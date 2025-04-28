package com.yunmo.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * springboot特殊的配置类，可以配置拦截器等等。
 *WebMvcConfigurer配置类其实是Spring内部的一种配置方式，
 * 采用JavaBean的形式来代替传统的xml配置文件形式进行针对框架个性化定制，
 * 可以自定义一些Handler，Interceptor
 *
 * @Configuration的作用是可以代替xml配置文件，
 * 通过代码定义一个配置类，被注解的类里面包含多个@Bean注解方法，
 * 这些方法会被AnnotationConfigApplicationContext或
 * AnnotationConfigWebApplicationContext类进行扫描，
 * 用于定义Bean，初始化spring ioc容器中的对象。
 *
 */
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {
    @Bean
    public HandlerInterceptorUtil handlerInterceptorUtil(){
        return  new HandlerInterceptorUtil();
    }
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptorUtil interceptor =
                handlerInterceptorUtil();
        InterceptorRegistration ig =
                registry.addInterceptor(interceptor);

        ig.excludePathPatterns("/users/login");
        ig.excludePathPatterns("/users/**");
        ig.excludePathPatterns("js/**");
    }

    /**
     * 解决前端跨域问题
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 所有的当前站点的请求地址，都支持跨域访问。
        registry.addMapping("/**")
                //是否发送Cookie
                .allowCredentials(true)
                //放行哪些原始域
                .allowedOriginPatterns("*")
                //当前站点支持的跨域请求类型是什么
                .allowedMethods("*")
                //	允许请求头中的header，默认都支持
                .allowedHeaders("*")
                //响应头中允许访问的header，默认为空
                .exposedHeaders("*")
                //预请求的结果的有效期，默认30分钟,这里为一天
                .maxAge(24 * 60 * 60);
    }
}

package com.yunmo.back.util;

//import com.nb.appapi.util.JwtUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
preHandle：在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理；
        postHandle：在业务处理器处理请求执行完成后，生成视图之前执行。后处理（调用了Service并返回ModelAndView，但未进行页面渲染），有机会修改ModelAndView；
        afterCompletion：在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。返回处理（已经渲染了页面）
*/
public class HandlerInterceptorUtil implements HandlerInterceptor {

    /**
     * 请求调用前执行
     * @param request
     * @param response
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (request.getMethod().equals("OPTIONS")) { // 如果是预检请求
            String headers = request.getHeader("Access-Control-Request-Headers"); // 获取请求头信息
            if (headers != null && headers.contains("token")) { // 检查请求头信息是否包含 Authorization
                response.setHeader("Access-Control-Allow-Headers", "token"); // 在响应中包含 Access-Control-Allow-Headers 头部信息，允许实际请求中包含 Authorization 请求头
                return true;
            }
        }
        System.out.println("请求URL：" + request.getRequestURI());
        String token = request.getHeader("token"); //得到前端的token
        System.out.println("请求token:" + token);
        //解析token
        try{
            //JwtUtils.parseJWT(token);
        }catch (RuntimeException e){
            e.printStackTrace();
            responseError(response);//响应-1
            return false;
        }
        return true; //返回true则继续执行后续的请求,false就拦截请求
    }
    private void responseError(HttpServletResponse response){
        response.setContentType("application/json");
        try {
            response.getWriter().write("-1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 请求完成前执行,还没有真正响应到client端执行
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //System.out.println("postHandle");
        //modelAndView.setViewName("redirect:/upload.jsp");
    }

    /**
     * 请求完成后执行
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //System.out.println("afterCompletion");
    }
}

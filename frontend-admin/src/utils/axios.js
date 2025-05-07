import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '@/routes/index'
import { localGet } from './index'


// 后端接口地址。

// 这边由于后端没有区分测试和正式，姑且都写成一个接口。
axios.defaults.baseURL = "http://127.0.0.1:5180/"
// 携带 cookie，对目前的项目没有什么作用，因为我们是 token 鉴权
axios.defaults.withCredentials = true
// 请求头，headers 信息
axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
//用localGet
axios.defaults.headers['token'] = localGet('token') || ''
// 默认 post 请求，使用 application/json 形式
axios.defaults.headers.post['Content-Type'] = 'application/json'


// 添加请求拦截器
/*axios.interceptors.request.use(function (config) {
  // 在发送请求之前做些什么
  // 根据条件修改请求地址 a就是后端java项目a.war  的文件名
  config.url = 'a' + config.url;
  return config;
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error);
});*/


// 请求拦截器，内部根据返回值，重新组装，统一管理。
axios.interceptors.response.use(res => {
  if (typeof res !== 'object') {
    ElMessage.error('服务端异常！')
    return Promise.reject(res)
  }
  /*if (res.resultCode != 200) {
    if (res.data.message) ElMessage.error(res.data.message)
    if (res.data.resultCode == 419) {
      router.push({ path: '/login' })
    }
    return Promise.reject(res.data)
  }*/

  return res.data
})

export default axios
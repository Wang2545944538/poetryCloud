import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '@/routes/index'
import { localGet } from './index'


// 后端接口地址。

// 这边由于后端没有区分测试和正式，姑且都写成一个接口。
axios.defaults.baseURL = "http://118.31.229.234:5180/"
// axios.defaults.baseURL = ""

// 携带 cookie，对目前的项目没有什么作用，因为我们是 token 鉴权
axios.defaults.withCredentials = true
// 请求头，headers 信息
axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
//用localGet
axios.defaults.headers['token'] = localGet('token') || ''
// 默认 post 请求，使用 application/json 形式
axios.defaults.headers.post['Content-Type'] = 'application/json'

//war
// 添加请求拦截器
// axios.interceptors.request.use(function (config) {
//   // 在发送请求之前做些什么
//   // 根据条件修改请求地址 a就是后端java项目a.war  的文件名
//     config.url = 'a' + config.url;
//   return config;
// }, function (error) {
//   // 对请求错误做些什么
//   return Promise.reject(error);
// });

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

// 响应拦截器
service.interceptors.response.use(
  response => {
    return response.data // 保持原逻辑
  },
  error => {
    if (error.response) {
      const status = error.response.status
      const data = error.response.data

      // 401: token 失效，跳转登录页
      if (status === 401) {
        ElMessage.error('登录状态已过期，请重新登录')
        localStorage.removeItem('token') // 清除 token
        router.push('/login') // 跳转登录页
      } else if (status === 403) {
        ElMessage.error('没有权限访问该资源')
      } else {
        ElMessage.error(data.message || '请求发生错误')
      }
    } else {
      ElMessage.error('服务器异常，请稍后再试')
    }

    return Promise.reject(error)
  }
)

const ttsAxios = axios.create({
  baseURL: "http://q7af8277.natappfree.cc/",
  withCredentials: false,
  timeout: 40000,
  headers: {
    //'X-Requested-With': 'XMLHttpRequest',
    'Content-Type': 'multipart/form-data'
  }
})

export { ttsAxios }
export default service

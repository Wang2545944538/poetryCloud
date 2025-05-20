import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '@/routes/index'
import { localGet } from './index'

// 创建实例
const service = axios.create({
  baseURL: 'http://118.31.229.234:5180/', // 统一接口前缀
  withCredentials: true,
  headers: {
    'X-Requested-With': 'XMLHttpRequest',
    'Content-Type': 'application/json'
  }
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 每次请求都动态从 localStorage 获取最新 token
    const token = localGet('token')
    if (token) {
      config.headers['token'] = token
    }

    // 如果后端部署为 a.war 形式，可以开启以下路径前缀（视项目需求）
    // config.url = 'a' + config.url

    return config
  },
  error => {
    return Promise.reject(error)
  }
)

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

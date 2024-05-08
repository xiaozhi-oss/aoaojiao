import axios from 'axios'
import { ElMessage } from 'element-plus'
// 引入用户相关的仓库
import useUserStore from '@/store/modules/user'
//创建axios实例
const request = axios.create({
  baseURL: import.meta.env.VITE_APP_BASE_API,
  timeout: 5000,
})
//请求拦截器
request.interceptors.request.use((config) => {
  // 获取用户相关的小仓库：获取仓库存储的token，登录成功之后的每次请求都携带给服务器
  const userStore = useUserStore()
  if (userStore.token) {
    config.headers['Authorization'] = userStore.token
  }
  return config
})
// 响应拦截器
request.interceptors.response.use(
  // 成功回调
  (response) => {
    const res = response.data
    const code = res.code

    if (code == '00000') {
      return Promise.resolve(res)
    } else if (code == 'A0601') {
      for (const index in res.data) {
        ElMessage({
          type: 'error',
          message: res.data[index],
        })
      }
      return Promise.reject(res)
    } else {
      ElMessage({
        type: 'error',
        message: res.message,
      })
      return Promise.reject('error')
    }
  },
  // 失败回调
  (error) => {
    const code = error.response.status
    let msg = ''
    if (code == 300) {
      msg = '页面重定向'
    } else if (code == 400) {
      msg = '页面不存在'
    } else {
      msg = '服务器异常'
    }
    ElMessage({
      type: 'error',
      message: msg,
    })
    return Promise.reject(error)
  },
)
export default request

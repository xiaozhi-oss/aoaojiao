// 创建用户相关的小仓库
import { defineStore } from 'pinia'
// 引入接口
import { reqLogin, reqUserInfo, reqLogout } from '@/api/login'
import {
  LoginFormData,
  LoginResponseData,
  UserInfoResponseData,
} from '@/api/login/type'
import { UserState } from './types/type'
import { GET_TOKEN, SET_TOKEN, REMOVE_TOKEN } from '@/utils/token'
// 引入路由（常量路由）
import { constantRoute } from '@/router/routes'
// 引入操作本地存储的工具方法
// 创建用户小仓库
const useUserStore = defineStore('User', {
  state: (): UserState => {
    return {
      token: GET_TOKEN(), // 用户唯一标识 token
      menuRoutes: constantRoute, // 仓库存储生成菜单需要的数组（路由）
      username: '',
      avatar: '',
    }
  },
  actions: {
    // 用户登录的方法
    async userLogin(data: LoginFormData) {
      return new Promise((resolve, reject) => {
        reqLogin(data)
          .then((res: LoginResponseData) => {
            if (res.data) {
              // pinia 仓库存储一下 token
              this.token = res.data.accessToken as string
              // 本地持久化存储
              SET_TOKEN(this.token)
            }
            resolve(res)
          })
          .catch((error: any) => {
            reject(error)
          })
      })
    },
    // 获取用户信息方法
    async userInfo() {
      // 获取用户信息存储到仓库中 [用户头像、名字]
      await reqUserInfo()
        .then((res) => {
          this.username = res.username
          this.avatar = res.avatar
        })
        .catch((error) => {
          return Promise.reject(new Error(error))
        })
    },
    // 退出登录
    async userLogout() {
      await reqLogout()
        .then(() => {
          this.clearUserData()
        })
        .catch((error) => {
          return Promise.reject(new Error(error))
        })
    },
    clearUserData() {
      // 成功清除本地用户数据
      this.token = ''
      this.username = ''
      this.avatar = ''
      REMOVE_TOKEN()
    },
  },
  getters: {},
})
export default useUserStore

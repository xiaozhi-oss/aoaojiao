import { ResponseData } from '@/api/commonType'

// 定义用户相关数据的 ts 类型
export interface LoginFormData {
  username: string
  password: string
}

// 定义登录接口返回数据类型
export interface LoginResponseData extends ResponseData {
  data: string
}

// 定义获取用户信息返回数据类型
export interface UserInfoResponseData extends ResponseData {
  data: {
    routes: string[]
    buttons: string[]
    roles: string[]
    name: string
    avatar: string
    accessToken: string
  }
}

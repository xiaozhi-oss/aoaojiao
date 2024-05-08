// 统一管理用户相关接口
import request from '@/utils/request'
import { LoginFormData, LoginResponseData, UserInfoResponseData } from './type'

// 真实接口
enum API {
  LOGIN_URL = '/admin/login',
  USERINFO_URL = '/admin/getUserInfo',
  LOGOUT_URL = '/admin/logout',
  CAPTCHA_IMG_URL = '/code/captchaImg',
  EMAIL_CODE_URL = '/code/emailCode',
}
// 图片验证码接口
export const reqCaptcheImg = () => request.get<any, any>(API.CAPTCHA_IMG_URL)
// 邮箱验证码接口
export const reqEmailCode = (email: string) =>
  request.get<any, any>(`${API.EMAIL_CODE_URL}/${email}`)
// 登录接口
export const reqLogin = (data: LoginFormData) =>
  request.post<any, LoginResponseData>(API.LOGIN_URL, data)
// 获取用户信息
export const reqUserInfo = () =>
  request.get<any, UserInfoResponseData>(API.USERINFO_URL)
// 退出登录
export const reqLogout = () => request.post<any, any>(API.LOGOUT_URL)

// mock 接口
// import { loginForm, loginResponseData, userResponseData } from './type'
// // 统一管理接口
// enum API {
//   LOGIN_URL = '/user/login',
//   USERINFO_URL = '/user/info',
// }
// // 暴露请求函数
// // 登录接口方法
// export const reqLogin = (data: loginForm) =>
//   request.post<any, loginResponseData>(API.LOGIN_URL, data)
// // 获取用户信息接口方法
// export const reqUserInfo = () =>
//   request.get<any, userResponseData>(API.USERINFO_URL)

import request from '@/utils/request'

import { UserResponseData, User } from '@/api/acl/user/type'

enum API {
  ALL_USER_URL = '/admin/acl/user/',
  ADD_USER_URL = '/admin/acl/user/save',
  UPDATE_USER_URL = '/admin/acl/user/update',
}

// 获取用户账号信息接口
export const reqUserInfo = (page: number, limit: number) =>
  request.get<any, UserResponseData>(API.ALL_USER_URL + `${page}/${limit}`)

// 获取用户账号信息接口
export const reqAddOrUpdateUser = (data: User) => {
  // 判断是添加还是更新
  if (data.id) {
    return request.put<any, any>(API.UPDATE_USER_URL, data)
  } else {
    return request.post<any, any>(API.ADD_USER_URL, data)
  }
}

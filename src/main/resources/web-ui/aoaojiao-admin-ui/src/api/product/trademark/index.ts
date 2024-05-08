// 品牌管理模块接口
import request from '@/utils/request'
import { TrademarkResponseData, Trademark } from './type'
// 平拍管理模块接口地址
enum API {
  // 获取已有品牌的接口
  TRADEMARK_URL = '/admin/product/baseTrademark/',
  ADD_TRADEMARK_URL = '/admin/product/baseTrademark/save',
  UPDATE_TRADEMARK_URL = '/admin/product/baseTrademark/update',
  DELETE_TRADEMARK_URL = '/admin/product/baseTrademark/remove/',
}
// 获取已有品牌的接口方法
export const reqHasTrademark = (page: number, limit: number) =>
  request.get<any, TrademarkResponseData>(
    API.TRADEMARK_URL + `${page}/${limit}`,
  )

// 添加和修改已有品牌接口方法
export const reqAddOrUpdateTrademark = (data: Trademark) => {
  // 修改已有品牌的数据
  if (data.id) {
    return request.put<any, any>(API.UPDATE_TRADEMARK_URL, data)
  } else {
    // 新增品牌
    return request.post<any, any>(API.ADD_TRADEMARK_URL, data)
  }
}

// 删除已有品牌
export const reqDeleteTrademark = (id: number) =>
  request.delete<any, any>(API.DELETE_TRADEMARK_URL + id)

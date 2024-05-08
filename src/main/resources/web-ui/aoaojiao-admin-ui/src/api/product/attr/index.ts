// 属性相关的 API 文件
import request from '@/utils/request'
// import {  } from './type'

enum API {
  // 获取一级、二级、三级分类的接口方法
  C1_URL = '/admin/product/getCategory1',
  C2_URL = '/admin/product/getCategory2/',
  C3_URL = '/admin/product/getCategory3/',
  // 获取分类下已有属性与属性值
  ATTR_URL = '/admin/product/attrInfoList/',
}

export const reqC1 = () => request.get<any, any>(API.C1_URL)
// 根据一级分类的id查询二级分类
export const reqC2 = (c1Id: number | string) =>
  request.get<any, any>(API.C2_URL + c1Id)
// 根据二级分类的id查询三级分类
export const reqC3 = (c2Id: number | string) =>
  request.get<any, any>(API.C3_URL + c2Id)

export const reqAttr = (
  category1Id: number | string,
  category2Id: number | string,
  category3Id: number | string,
) =>
  request.get<any, any>(
    API.ATTR_URL + `${category1Id}/${category2Id}/${category3Id}`,
  )

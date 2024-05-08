import request from '@/utils/request'

const moduleName = 'menu'
enum API {
  ALL_MENU_URL = `/${moduleName}/list`,
  ALL_TREE_MENU_URL = `/${moduleName}/treeList`,
  ADD_MENU_URL = `/${moduleName}/save`,
  UPDATE_MENU_URL = `/${moduleName}/update`,
}

// 获取部门信息
export const reqMenuInfo = (params: any) => {
  console.log(params)
  return request.get<any, any>(API.ALL_MENU_URL, { params })
}

// 获取树状部门信息
export const reqTreeMenuInfo = () =>
  request.get<any, any>(API.ALL_TREE_MENU_URL)

// 添加或更新部门
export const reqAddOrUpdateMenu = (data: any) => {
  // 判断是添加还是更新
  if (data.menuId) {
    return request.put<any, any>(API.UPDATE_MENU_URL, data)
  } else {
    return request.post<any, any>(API.ADD_MENU_URL, data)
  }
}

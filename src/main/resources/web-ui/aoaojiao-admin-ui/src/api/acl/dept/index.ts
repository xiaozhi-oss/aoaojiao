import request from '@/utils/request'

import { DeptResponseData, Dept } from '@/api/acl/dept/type'

enum API {
  ALL_DEPT_URL = '/dept/list',
  ALL_TREE_DEPT_URL = '/dept/treeList',
  ADD_DEPT_URL = '/dept/save',
  UPDATE_DEPT_URL = '/dept/update',
}

// 获取部门信息
export const reqDeptInfo = (params: any) => {
  console.log(params)
  return request.get<any, DeptResponseData>(API.ALL_DEPT_URL, { params })
}

// 获取树状部门信息
export const reqTreeDeptInfo = () =>
  request.get<any, DeptResponseData>(API.ALL_TREE_DEPT_URL)

// 添加或更新部门
export const reqAddOrUpdateDept = (data: Dept) => {
  // 判断是添加还是更新
  if (data.deptId) {
    return request.put<any, any>(API.UPDATE_DEPT_URL, data)
  } else {
    return request.post<any, any>(API.ADD_DEPT_URL, data)
  }
}

import { ArrayInfo } from '@/api/commonType'

export interface Dept {
  deptId?: number
  deptName: string
  deptSort: number
  parentId?: number
  parentName?: string
  status: number
  leader: string
  email?: string
  phone?: string
  remark?: string
}

export type DeptRecords = Dept[]
export type DeptResponseData = ArrayInfo<DeptRecords>

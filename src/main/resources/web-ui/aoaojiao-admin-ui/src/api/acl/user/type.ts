import { PaginationInfo } from '@/api/commonType'

export interface User {
  id?: number
  createTime?: string
  updateTime?: string
  username: string
  password: string
  name: string
  phone?: string
  roleName?: string
}

export type UserRecords = User[]
export type UserResponseData = PaginationInfo<UserRecords>

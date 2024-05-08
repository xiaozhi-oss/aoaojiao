import { ResponseData, PaginationInfo } from '@/api/commonType'
// 已有品牌的 ts 类型
export interface Trademark {
  id?: number
  tmName: string
  logoUrl: string
}

// 包含全部品牌数据的 ts 类型
export type Records = Trademark[]

// 获取已有品牌的全部数据的 ts 类型
// export interface TrademarkResponseData extends ResponseData {
//   data: {
//     records: Records
//     pagination: PaginationInfo
//   }
// }

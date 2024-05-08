// 定义全部接口返回数据都拥有的 ts 类型
export interface ResponseData {
  code: string | number
  message: string
  ok: boolean
}

// 定义接口响应分页的 ts 类型
export interface PaginationInfo<T> extends ResponseData {
  data: {
    records: T
    total: number
    size: number
    current: number
    searchCount: boolean
    pages: number
  }
}

export interface ArrayInfo<T> extends ResponseData {
  data: T
}

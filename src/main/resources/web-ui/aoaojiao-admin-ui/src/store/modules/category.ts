// 商品分类全局组件的小仓库
import { defineStore } from 'pinia'
import { reqC1, reqC2, reqC3 } from '@/api/product/attr'

const useCategoryStore = defineStore('Category', {
  state: () => {
    return {
      // 存储一级分类数据
      c1Arr: [],
      // 存储二级分类数据
      c2Arr: [],
      c3Arr: [],
      c1Id: '',
      c2Id: '',
      c3Id: '',
    }
  },
  actions: {
    async getC1() {
      // 发起请求获取一级分类的数据
      const result = await reqC1()
      if (result.code == 200) {
        this.c1Arr = result.data
      }
    },
    async getC2() {
      // 发起请求获取一级分类的数据
      const result = await reqC2(this.c1Id)
      if (result.code == 200) {
        this.c2Arr = result.data
      }
    },
    async getC3() {
      // 发起请求获取一级分类的数据
      const result = await reqC3(this.c2Id)
      if (result.code == 200) {
        this.c3Arr = result.data
      }
    },
  },
  getters: {},
})
export default useCategoryStore

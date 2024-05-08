// 关于layout 配置相关的仓库
import { defineStore } from 'pinia'

const useLayoutSettingStore = defineStore('SettingStore', {
  state: () => {
    return {
      fold: false, // 用户控制菜单是否折叠
      refsh: false, // 用户控制刷新效果
    }
  },
})
export default useLayoutSettingStore

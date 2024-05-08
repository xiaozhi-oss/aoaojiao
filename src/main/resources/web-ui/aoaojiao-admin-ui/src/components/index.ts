import SvgIcon from './SvgIcon/index.vue'
import Category from './Category/index.vue'
import type { App, Component } from 'vue'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
const components: { [name: string]: Component } = { SvgIcon, Category }
export default {
  install(app: App) {
    // 注册自定义组件为全局组件
    Object.keys(components).forEach((key: string) => {
      app.component(key, components[key])
    })
    // 注册element-plus 的所有图标组件为全局组件
    for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
      app.component(key, component)
    }
  },
}

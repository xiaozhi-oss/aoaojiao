import { createApp } from 'vue'
import App from '@/App.vue'
// 集成 ElementPlus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
// svg 插件需要的依赖
import 'virtual:svg-icons-register'
// 自定义插件，注册整个项目的全局组件
import GlobalComponent from '@/components'
// 引入模版的全局样式
import '@/styles/index.scss'

// 引入路由
import router from './router'
// 引入仓库
import pinia from './store'
// 引入路由鉴权文件
import './permission'
const app = createApp(App)
// 引入 ElementPlus
app.use(ElementPlus, {
  locale: zhCn, // ElementPlus 国际化
})
// 安装仓库
app.use(pinia)
// 安装自定义插件
app.use(GlobalComponent)
// 注册模版路由
app.use(router)
// 挂载
app.mount('#app')

// 测试代码：测试接口是否可用
// import axios from 'axios'
// axios({
//   url: '/api/user/login',
//   method: 'post',
//   data: {
//     username: 'admin',
//     password: '111111',
//   },
// })
// 测试环境变量
// console.log(import.meta.env.VITE_APP_BASE_API)

// 路由鉴权，项目中路由是否能被访问
import router from '@/router'
// 引入进度条插件
import nprogress from 'nprogress'
// 引入进度条样式
import 'nprogress/nprogress.css'
import { ElMessage } from 'element-plus'

// 进度条尾部加载样式去除
nprogress.configure({ showSpinner: false })

// 获取用户相关的小仓库内部token数据，去判断用户是否登录成功
import useUserStore from './store/modules/user'
import pinia from './store'
const userStore = useUserStore(pinia)

// 全局前置守卫：项目当中任意路由切换都会触发的钩子
// to：将要访问的路由
// from：从那个路由来
// next：路由的放行函数
router.beforeEach(async (to: any, from: any, next: any) => {
  // 页面标题
  document.title = to.meta.title
  // 访问某个路由之前会方法的守卫
  nprogress.start()
  // 获取 token 判断用户是否登录
  const token = userStore.token
  // 路由白名单
  const whiteList = ['/login']
  // 用户已登录
  if (token) {
    // 登录再次重新访问
    if (to.path == '/login') {
      // 返回首页
      next({ path: '/' })
    } else {
      // 获取用户信息
      console.log(userStore.username)
      if (userStore.username == '') {
        try {
          await userStore.userInfo()
          next()
        } catch (error) {
          // token 过期
          // 让用户退出登录，清理用户数据
          await userStore.clearUserData()
          next({ path: '/login', query: { redirect: to.path } })
        }
      }
      next()
    }
  } else {
    // 白名单路由直接放行
    if (whiteList.find((e) => e == to.path)) {
      next()
    }
    next({ path: '/login', query: { redirect: to.path } })
  }
})
// 全局后置守卫
router.afterEach((to: any, from: any) => {
  // 访问某个路由之后会方法的守卫
  nprogress.done()
})

// 1 任意路由切换实现触发进度条业务
// 2 路由鉴权业务
// 全部路由组件：登录 | 404 | 任意路由 | 首页 | 数据大屏
//              权限管理（三个子路由） | 商品管理（四个子路由）

// 用户未登录：可以访问 login，其他的路由不能访问 （指向 login）
// 用户登录成功：不可以访问 login(指向首页)，其他路由根据角色拥有的权限来访问

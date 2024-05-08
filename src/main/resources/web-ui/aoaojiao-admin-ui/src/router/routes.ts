// 对外暴露配置路由（常亮路由）
export const constantRoute = [
  {
    // 登录
    path: '/login',
    component: () => import('@/views/login/index.vue'),
    name: 'login', // 命名路由
    meta: {
      title: '登录', // 菜单标题
      hidden: true,
    },
  },
  {
    // 登录成功以后展示数据的路由
    path: '/',
    component: () => import('@/layout/index.vue'),
    name: 'layout',
    redirect: '/home', // 重定向到二级路由home，当访问 / 的时候跳转到 home
    meta: {
      title: '', // 菜单标题
      hidden: true, // 表示路由标题在菜单中是否隐藏，true隐藏，flase显示
      icon: '', // 菜单文字左侧的图标，支持 element-plus 全部图标
    },
    children: [
      {
        path: '/home',
        component: () => import('@/views/home/index.vue'),
        meta: {
          title: '首页', // 菜单标题
          hidden: false,
          icon: 'HomeFilled',
        },
      },
    ],
  },
  {
    // 权限管理
    path: '/acl',
    component: () => import('@/layout/index.vue'),
    name: 'Acl',
    redirect: '/acl/user',
    meta: {
      title: '权限管理', // 菜单标题
      hidden: false,
      icon: 'Lock',
    },
    children: [
      {
        path: '/acl/user',
        component: () => import('@/views/acl/user/index.vue'),
        name: 'User',
        meta: {
          title: '用户管理', // 菜单标题
          hidden: false,
          icon: 'User',
        },
      },
      {
        path: '/acl/role',
        component: () => import('@/views/acl/role/index.vue'),
        name: 'Role',
        meta: {
          title: '角色管理', // 菜单标题
          hidden: false,
          icon: 'UserFilled',
        },
      },
      {
        path: '/acl/permission',
        component: () => import('@/views/acl/permission/index.vue'),
        name: 'Permission',
        meta: {
          title: '菜单管理', // 菜单标题
          hidden: false,
          icon: 'Monitor',
        },
      },
      {
        path: '/acl/dept',
        component: () => import('@/views/acl/dept/index.vue'),
        name: 'Dept',
        meta: {
          title: '部门管理', // 菜单标题
          hidden: false,
          icon: 'Management',
        },
      },
    ],
  },
  {
    // 内容管理
    path: '/product',
    component: () => import('@/layout/index.vue'),
    name: 'Product',
    redirect: '/product/trademark',
    meta: {
      title: '商品管理',
      hidden: false,
      icon: 'Goods',
    },
    children: [
      {
        path: '/product/trademark',
        component: () => import('@/views/product/trademark/index.vue'),
        name: 'Trademark',
        meta: {
          title: '品牌管理', // 菜单标题
          icon: 'ShoppingCartFull',
        },
      },
      {
        path: '/product/spu',
        component: () => import('@/views/product/spu/index.vue'),
        name: 'Spu',
        meta: {
          title: 'SPU管理', // 菜单标题
          icon: 'ChromeFilled',
        },
      },
      {
        path: '/product/attr',
        component: () => import('@/views/product/attr/index.vue'),
        name: 'Attr',
        meta: {
          title: '属性管理', // 菜单标题
          icon: 'Calendar',
        },
      },
      {
        path: '/product/sku',
        component: () => import('@/views/product/sku/index.vue'),
        name: 'Sku',
        meta: {
          title: 'SKU管理', // 菜单标题
          icon: 'Orange',
        },
      },
    ],
  },
  {
    // 数据大屏
    path: '/screen',
    component: () => import('@/views/screen/index.vue'),
    name: 'Screen',
    meta: {
      title: '数据大屏', // 菜单标题
      icon: 'DataLine',
    },
  },
  {
    // 404 路由
    path: '/404',
    component: () => import('@/views/404/index.vue'),
    name: '404',
    meta: {
      title: '404', // 菜单标题
      hidden: true,
    },
  },
  {
    // 任意路由
    path: '/:pathMatch(.*)*',
    redirect: '404',
    name: 'Any',
    meta: {
      title: '任意路由', // 菜单标题
      hidden: true,
    },
  },
]

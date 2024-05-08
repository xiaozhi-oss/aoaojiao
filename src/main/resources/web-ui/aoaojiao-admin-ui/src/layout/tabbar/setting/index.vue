<template>
  <el-button
    size="default"
    icon="Refresh"
    circle
    @click="updateRefsh"
  ></el-button>
  <el-button
    size="default"
    icon="FullScreen"
    circle
    @click="fullScreen"
  ></el-button>
  <el-button size="default" icon="Setting" circle></el-button>
  <img
    :src="userStore.avatar"
    style="height: 30px; width: 30px; margin: 0 20px; border-radius: 50%"
  />
  <!-- 下拉菜单 -->
  <el-dropdown>
    <span class="el-dropdown-link">
      {{ userStore.username }}
      <el-icon class="el-icon--right">
        <arrow-down />
      </el-icon>
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>
<script lang="ts">
export default {
  name: 'Setting',
}
</script>
<script setup lang="ts">
// 获取配置的小仓库
import useLayoutSettingStore from '@/store/modules/setting'
import useUserStore from '@/store/modules/user'
import { useRouter, useRoute } from 'vue-router'
const userStore = useUserStore()
const layoutSettingStore = useLayoutSettingStore()
// 获取路由器对象
const $router = useRouter()
// 获取路由对象
const $route = useRoute()
const updateRefsh = () => {
  layoutSettingStore.refsh = !layoutSettingStore.refsh
}
const fullScreen = () => {
  // 注意：全屏模式当前只考虑 chrome 浏览器的兼容，其他不考虑，如需其他的支持可以使用插件实现
  // DOM 对象的一个属性：可以用来判断当前是否是全屏模式，true为全屏，false不为全屏
  let full = document.fullscreenElement
  // 切换为全屏模式
  if (!full) {
    // requestFullScreen 实现全屏模式
    document.documentElement.requestFullscreen()
  } else {
    // 退出全屏
    document.exitFullscreen()
  }
}
// 退出登录
const logout = async () => {
  // 1 向服务器发送请求
  // 2 仓库中关于用户相关的数据清空 [token, username, avatar]
  await userStore.userLogout()
  // 3 重定向到登录页面
  $router.push({ path: '/login', query: { redirect: $route.path } })
}
</script>

<style scoped></style>

<template>
  <div class="logo" v-if="setting.logoShow">
    <Icon icon="twemoji:cat-with-wry-smile" width="35" />
    <p v-if="!layoutSettingStore.fold">{{ setting.title }}</p>
  </div>
</template>

<script setup lang="ts">
import { Icon } from '@iconify/vue'
import useLayoutSettingStore from '@/store/modules/setting'
// 引入设置标题与logo的配置文件
import setting from '@/setting'
const layoutSettingStore = useLayoutSettingStore()
</script>
<script lang="ts">
export default {
  name: 'Logo',
}
</script>
<style scoped lang="scss">
.logo {
  width: 100%;
  height: $base-menu-logo-height;
  padding: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  p {
    font-size: $base-logo-title-fontSize;
    margin-left: 10px;
    color: $base-color;
    font-family: 'Open Sans', Arial, sans-serif;
    font-weight: bold;
    background: linear-gradient(0.25turn, #c21500, #ffc500);
    background-clip: text;
    -webkit-background-clip: text;
    color: transparent;
  }
}
</style>

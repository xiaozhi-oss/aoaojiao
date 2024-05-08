<template>
  <div class="tabbar">
    <div class="tabbar_left">
      <Breadcrumb></Breadcrumb>
    </div>
    <div class="tabbar_right">
      <Setting></Setting>
    </div>
  </div>
</template>

<script setup lang="ts">
import Breadcrumb from './breadcrumb/index.vue'
import Setting from './setting/index.vue'
</script>
<script lang="ts">
export default {
  name: 'Tabbar',
}
</script>
<style scoped lang="scss">
.tabbar {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: space-between;
  padding: 0 20px;
  // 添加渐变色
  // background-image: linear-gradient(
  //   to right,
  //   rgb(232, 223, 223),
  //   rgb(227, 200, 200),
  //   rgb(250, 249, 249)
  // );
  background-color: #ffffff;
  .tabbar_left,
  .tabbar_right {
    display: flex;
    align-items: center;
  }
}
</style>

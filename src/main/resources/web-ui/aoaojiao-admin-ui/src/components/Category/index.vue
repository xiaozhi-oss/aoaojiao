<template>
  <el-card>
    <el-form :inline="true">
      <el-form-item label="一级分类" class="form-item">
        <el-select v-model="categoruStore.c1Id" @change="handleC1">
          <!-- value属性为下拉菜单收集的数据 -->
          <el-option
            v-for="(c1, index) in categoruStore.c1Arr"
            :key="c1.id"
            :label="c1.name"
            :value="c1.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="二级分类" class="form-item">
        <el-select v-model="categoruStore.c2Id" @change="handleC2">
          <el-option
            v-for="(c2, index) in categoruStore.c2Arr"
            :key="c2.id"
            :label="c2.name"
            :value="c2.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="三级分类" class="form-item">
        <el-select v-model="categoruStore.c3Id" @change="handleC3">
          <el-option
            v-for="(c3, index) in categoruStore.c3Arr"
            :key="c3.id"
            :label="c3.name"
            :value="c3.id"
          ></el-option>
        </el-select>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import useCategoryStore from '@/store/modules/category'
let categoruStore = useCategoryStore()
// 存储一级分类的数据
const getC1 = async () => {
  categoruStore.getC1()
}
// 一级选择器选择触发的回调(选中值的时候会触发)
const handleC1 = () => {
  // 通知仓库获取二级分类的数据
  categoruStore.getC2()
  // 清空数据
  categoruStore.c2Id = ''
  categoruStore.c3Id = ''
  categoruStore.c3Arr = []
}
const handleC2 = () => {
  categoruStore.getC3()
  categoruStore.c3Id = ''
  categoruStore.c3Arr = []
}
// 挂载完毕后
onMounted(() => {
  // 获取一级分类的数据
  getC1()
})
</script>

<style scoped lang="scss">
.form-item {
  width: 300px;
}
</style>

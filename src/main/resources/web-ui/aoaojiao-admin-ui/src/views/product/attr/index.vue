<template>
  <div>
    <Category></Category>
    <el-card style="margin: 10px 0">
      <el-button
        type="primary"
        size="default"
        icon="Plus"
        :disabled="categoruStore.c3Id ? false : true"
        @click="addAttr"
      >
        添加属性
      </el-button>
      <el-table border style="margin: 10px 0" :data="attrList">
        <el-table-column
          label="序号"
          type="index"
          align="center"
          width="80px"
        ></el-table-column>
        <el-table-column
          label="属性名称"
          width="120px"
          prop="attrName"
        ></el-table-column>
        <el-table-column label="属性值名称">
          <template #="{ row, $index }">
            <el-tag
              style="margin: 5px"
              v-for="(item, index) in row.attrValueList"
              :key="item.id"
            >
              {{ item.valueName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120px">
          <template #="{ row, $index }">
            <el-button
              type="primary"
              size="small"
              icon="Edit"
              @click=""
            ></el-button>
            <el-button
              type="primary"
              size="small"
              icon="Delete"
              @click=""
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import useCategoryStore from '@/store/modules/category'
import { watch, ref } from 'vue'
// 引入获取已有属性与属性值的接口
import { reqAttr } from '@/api/product/attr'
let categoruStore = useCategoryStore()
let attrList = ref<any>([])
const addAttr = () => {}
// 监听仓库三级分类 ID 的变化，如果发生变化就发生请求获取数据
watch(
  () => categoruStore.c3Id,
  () => {
    // 清空上一次的数据
    attrList.value = []
    // 保证三级分类存在才能发请求
    if (!categoruStore.c3Id) return
    getAttr()
  },
)
const getAttr = async () => {
  // 获取分类的 ID
  const { c1Id, c2Id, c3Id } = categoruStore
  const result = await reqAttr(c1Id, c2Id, c3Id)
  if (result.code == 200) {
    attrList.value = result.data
  }
}
</script>

<style scoped lang="scss"></style>

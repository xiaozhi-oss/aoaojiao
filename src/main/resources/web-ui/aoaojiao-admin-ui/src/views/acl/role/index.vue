<template>
  <el-card class="br-10 mb-10">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="角色名称">
        <el-input
          v-model="formParams.roleName"
          placeholder="输入角色名称"
          clearable
        />
      </el-form-item>
      <el-form-item label="权限字符">
        <el-input
          v-model="formParams.roleStr"
          placeholder="输入权限字符"
          clearable
        />
      </el-form-item>
      <el-form-item label="状态">
        <el-select
          v-model="formParams.status"
          placeholder="角色状态"
          clearable
          style="width: 140px"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="default" @click="" icon="Search">
          搜索
        </el-button>
        <el-button type="default" size="default" @click="" icon="Refresh">
          重置
        </el-button>
      </el-form-item>
    </el-form>
  </el-card>
  <el-card class="br-10">
    <div class="mb-20">
      <el-button type="primary" icon="Plus">新增</el-button>
      <el-button type="danger" icon="Delete">删除</el-button>
    </div>
    <el-table
      :data="deptArr"
      :header-cell-style="{ background: '#F8F8F9' }"
      row-key="deptId"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="deptName" label="角色名称" />
      <el-table-column prop="deptSort" label="排序" width="80px" />
      <el-table-column prop="status" label="状态" width="80px" align="center">
        <template #="{ row, $index }">
          <el-tag type="success" v-if="row.status">启用</el-tag>
          <el-tag type="warning" v-if="!row.status">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="leader" label="权限字符" width="100px" />
      <el-table-column prop="email" label="备注" />
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column label="操作" width="300px">
        <template #="{ row, $index }">
          <el-button
            type="primary"
            size="small"
            icon="Edit"
            @click="editDept(row)"
          >
            编辑
          </el-button>
          <el-button
            type="danger"
            size="small"
            icon="Delete"
            @click="deleteDeptHandler"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页器 -->
    <div class="fr mtb-10">
      <el-pagination
        v-model:current-page="pageNo"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 30, 40]"
        :background="true"
        layout="prev, pager, next, jumper, sizes, total"
        :total="total"
        @size-change="getHasUser(1)"
        @current-change="getHasUser"
      />
    </div>
  </el-card>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
const options = [
  { value: 1, label: '启用' },
  { value: 0, label: '禁用' },
]

// 分页器
let pageNo = ref<number>(1)
let pageSize = ref<number>(10)
let total = ref<number>(0)

// 表单值
let formParams = reactive<any>({
  roleName: '',
  roleStr: '',
  status: '',
})
</script>

<style scoped></style>

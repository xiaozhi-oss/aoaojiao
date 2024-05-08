<template>
  <el-card class="br-10 mb-10">
    <el-form :inline="true" label-position="right" label-width="auto">
      <el-form-item label="部门名称">
        <el-input
          v-model="formParams.deptName"
          style="width: 230px"
          placeholder="输入部门名称搜索"
          prefix-icon="Search"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-select
          v-model="formParams.status"
          placeholder="部门状态"
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
        <el-button
          type="primary"
          size="default"
          @click="getDeptList()"
          icon="Search"
        >
          搜索
        </el-button>
        <el-button
          type="default"
          size="default"
          @click="resetBtnHandler()"
          icon="Refresh"
        >
          重置
        </el-button>
      </el-form-item>
    </el-form>
  </el-card>
  <!-- 主界面 -->
  <el-card class="br-10">
    <div class="opt-btn-con">
      <el-button
        type="primary"
        icon="Plus"
        @click="addOrUpdateDialogVisible = true"
      >
        新增
      </el-button>
      <el-button type="danger" icon="Delete" @click="">删除</el-button>
    </div>
    <el-table
      :data="deptArr"
      :header-cell-style="{ background: '#F8F8F9' }"
      :tree-props="{ children: 'childrenList' }"
      style="width: 100%; margin-bottom: 20px"
      row-key="deptId"
      default-expand-all
    >
      <el-table-column prop="deptName" label="部门名称" width="200px" />
      <el-table-column
        prop="deptSort"
        label="排序"
        width="80px"
        align="center"
      />
      <el-table-column prop="status" label="状态" width="80px" align="center">
        <template #="{ row, $index }">
          <el-tag type="success" v-if="row.status">启用</el-tag>
          <el-tag type="warning" v-if="!row.status">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="leader" label="负责人" width="100px" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="phone" label="手机号" />
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column label="操作" width="250px">
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
            type="success"
            size="small"
            icon="Plus"
            @click="updateDept(row)"
          >
            新增
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
  </el-card>
  <!-- 新增修改弹出框 -->
  <el-dialog
    v-model="addOrUpdateDialogVisible"
    width="590"
    :title="getTitle() + '部门'"
    @close="dialogClose"
    @open="dialogOpen"
  >
    <div class="form-main">
      <el-form
        :inline="true"
        size="large"
        label-position="right"
        label-width="80px"
      >
        <el-row>
          <el-col :span="24">
            <el-form-item label="上级部门" prop="parentId" style="width: 99%">
              <el-tree-select
                v-model="deptParams.parentId"
                :data="treeDeptArr"
                :props="{
                  value: 'deptId',
                  label: 'deptName',
                  children: 'childrenList',
                }"
                value-key="deptId"
                placeholder="选择上级部门"
                check-strictly
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="部门名称"
              :rules="{
                required: true,
                message: 'domain can not be null',
                trigger: 'blur',
              }"
            >
              <el-input
                placeholder="请输入部门名称"
                v-model="deptParams.deptName"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="显示排序"
              :rules="{
                required: true,
                message: 'domain can not be null',
                trigger: 'blur',
              }"
            >
              <el-input-number
                v-model="deptParams.deptSort"
                :min="0"
                style="width: 190px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="负责人">
              <el-input
                placeholder="请输入负责人"
                v-model="deptParams.leader"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话">
              <el-input
                placeholder="请输入负责人联系电话"
                v-model="deptParams.phone"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱">
              <el-input
                placeholder="请输入负责人邮箱"
                v-model="deptParams.email"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="部门状态">
              <el-switch
                v-model="deptParams.status"
                class="cu-switch-class"
                inline-prompt
                :active-value="1"
                :inactive-value="0"
                active-text="启用"
                inactive-text="禁用"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogClose" size="large">取消</el-button>
        <el-button type="primary" size="large" @click="addOrUpdateDept">
          确认
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import {
  reqDeptInfo,
  reqAddOrUpdateDept,
  reqTreeDeptInfo,
} from '@/api/acl/dept'
import { Dept } from '@/api/acl/dept/type'
const options = [
  { value: 1, label: '启用' },
  { value: 0, label: '禁用' },
]
/**
 * 表单筛选
 */
let formParams = reactive<any>({
  deptName: '',
  status: '',
})
const resetBtnHandler = () => {
  Object.assign(formParams, {
    status: '',
    deptName: '',
  })
  getDeptList()
}

/**
 * 首次加载
 */
let deptArr = ref<any>([])
const getDeptList = async () => {
  reqDeptInfo(formParams).then((res) => {
    deptArr.value = res.data
  })
}
onMounted(() => {
  getDeptList()
})

/**
 * 添加和更新弹窗
 */
let addOrUpdateDialogVisible = ref<boolean>(false)
let treeDeptArr = ref<any>([])
let deptParams = reactive<any>({
  parentId: '',
  parentName: '',
  status: 1,
  deptSort: 0,
  deptName: '',
  leader: '',
  phone: '',
  email: '',
  remark: '',
})
const editDept = (row: Dept) => {
  addOrUpdateDialogVisible.value = true
  // 将行的数据获取到放入到参数中
  Object.assign(deptParams, row)
  delete deptParams.childrenList
}
// 通过判断ID来动态获取标题和提示信息的内容
const getTitle = () => {
  return deptParams.deptId ? '修改' : '新增'
}
const addOrUpdateDept = async () => {
  const result: any = await reqAddOrUpdateDept(deptParams)
  const isOk = result.code == '00000'
  if (isOk) {
    // 获取最新数据
    getDeptList()
  }
  // 关闭弹窗
  addOrUpdateDialogVisible.value = false
  // 弹出消息
  const title = getTitle()
  ElMessage({
    message: isOk ? `${title}成功` : `${title}失败`,
    type: isOk ? 'success' : 'error',
  })
}

// 删除操作
const deleteDeptHandler = () => {}

// 弹窗数据
const getTreeDeptInfo = async () => {
  const result = await reqTreeDeptInfo()
  if (result.code == '00000') {
    treeDeptArr.value = result.data
  }
}
const dialogOpen = () => {
  getTreeDeptInfo()
}
// 弹窗关闭：清除数据状态
const dialogClose = () => {
  Object.assign(deptParams, {
    deptId: undefined,
    parentId: undefined,
    parentName: undefined,
    status: 1,
    deptSort: 0,
    deptName: '',
    leader: '',
    phone: '',
    email: '',
    remark: '',
  })
}
</script>

<style lang="scss" scoped>
.form-main {
  margin-top: 30px;
}
.el-form-item {
  margin-right: 10px !important;
}
.opt-btn-con {
  margin-bottom: 20px;
}
.custom-tree-class {
  max-height: 300px;
  overflow: scroll;
}
</style>

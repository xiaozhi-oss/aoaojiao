<template>
  <el-card class="br-10 mb-10">
    <el-form :inline="true" label-position="right" label-width="auto">
      <el-form-item label="菜单名称">
        <el-input
          v-model="formParams.deptName"
          style="width: 230px"
          placeholder="输入菜单名称搜索"
          prefix-icon="Search"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-select
          v-model="formParams.status"
          placeholder="菜单状态"
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
          @click="getMenuList()"
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
      :data="menuArr"
      :header-cell-style="{ background: '#F8F8F9' }"
      :tree-props="{ children: 'childrenList' }"
      style="width: 100%; margin-bottom: 20px"
      row-key="menuId"
      default-expand-all
    >
      <el-table-column prop="menuName" label="菜单名称" width="150px" />
      <el-table-column prop="icon" label="图标" width="60px" align="center">
        <template #="{ row, $index }">
          <el-icon size="1.2rem">
            <component :is="row.icon"></component>
          </el-icon>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="80px" align="center">
        <template #="{ row, $index }">
          <el-tag type="success" v-if="row.status">启用</el-tag>
          <el-tag type="warning" v-if="!row.status">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="path" label="路径" />
      <el-table-column prop="query" label="参数">
        <template #="{ row, $index }">
          <span>{{ row.query == '' ? '无' : row.query }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="isOuterChain"
        label="外链"
        width="60px"
        align="center"
      >
        <template #="{ row, $index }">
          <span v-if="row.isOuterChain">是</span>
          <span v-if="!row.isOuterChain">否</span>
        </template>
      </el-table-column>
      <el-table-column prop="menuType" label="类型" width="80px" align="center">
        <template #="{ row, $index }">
          <span v-if="row.menuType == 0">目录</span>
          <span v-if="row.menuType == 1">菜单</span>
          <span v-if="row.menuType == 2">按钮</span>
        </template>
      </el-table-column>
      <el-table-column prop="hidden" label="隐藏" width="60px" align="center">
        <template #="{ row, $index }">
          <span v-if="row.hidden">是</span>
          <span v-if="!row.hidden">否</span>
        </template>
      </el-table-column>
      <el-table-column prop="perms" label="权限字符" />
      <el-table-column prop="remark" label="备注" />
      <el-table-column
        prop="createTime"
        label="创建时间"
        show-overflow-tooltip
      />
      <el-table-column label="操作" width="250px">
        <template #="{ row, $index }">
          <el-button
            type="primary"
            size="small"
            icon="Edit"
            @click="editMenu(row)"
          >
            编辑
          </el-button>
          <el-button
            type="success"
            size="small"
            icon="Plus"
            @click="updateMenu(row)"
          >
            新增
          </el-button>
          <el-button
            type="danger"
            size="small"
            icon="Delete"
            @click="deleteMenuHandler"
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
    :title="getTitle() + '菜单'"
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
                v-model="menuParams.parentId"
                :data="treeMenuArr"
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
  reqMenuInfo,
  reqAddOrUpdateMenu,
  reqTreeMenuInfo,
} from '@/api/acl/menu'
const options = [
  { value: 1, label: '启用' },
  { value: 0, label: '禁用' },
]

/**
 * 表单筛选
 */
let formParams = reactive<any>({
  menuName: '',
  status: '',
})
const resetBtnHandler = () => {
  Object.assign(formParams, {
    status: '',
    menuName: '',
  })
  getMenuList()
}

/**
 * 首次加载
 */
let menuArr = ref<any>([])
const getMenuList = async () => {
  reqMenuInfo(formParams).then((res) => {
    menuArr.value = res.data
  })
}
onMounted(() => {
  getMenuList()
})

/**
 * 添加和更新弹窗
 */
let addOrUpdateDialogVisible = ref<boolean>(false)
let treeMenuArr = ref<any>([])
let menuParams = reactive<any>({
  menuName: '',
  parentId: '',
  path: '',
  query: '',
  isOuterChain: 0,
  menuType: 0,
  hidden: 0,
  status: 0,
  perms: '',
  icon: '',
  remark: '',
})
const editMenu = () => {
  addOrUpdateDialogVisible.value = true
  // 将行的数据获取到放入到参数中
  Object.assign(menuParams, row)
  delete menuParams.childrenList
}
// 通过判断ID来动态获取标题和提示信息的内容
const getTitle = () => {
  return menuParams.menuId ? '修改' : '新增'
}
const addOrUpdateMenu = async () => {
  const result: any = await reqAddOrUpdateMenu(menuParams)
  const isOk = result.code == '00000'
  if (isOk) {
    // 获取最新数据
    getMenuList()
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
const deleteMenuHandler = () => {}

// 弹窗数据
const getTreeMenuInfo = async () => {
  const result = await reqTreeMenuInfo()
  if (result.code == '00000') {
    treeMenuArr.value = result.data
  }
}
const dialogOpen = () => {
  getTreeMenuInfo()
}
// 弹窗关闭：清除数据状态
const dialogClose = () => {
  Object.assign(menuParams, {
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

<template>
  <!-- 主界面 -->
  <el-card body-style="display: flex;" class="br-10">
    <el-row style="width: 100%" justify="space-between">
      <el-col :span="4" style="padding-right: 10px">
        <!-- 树状数据关联的input -->
        <el-input
          v-model="treeTextSearch"
          style="margin-bottom: 20px"
          placeholder="请输入部门名称"
          prefix-icon="Search"
        />
        <el-tree
          class="custom-tree-class"
          ref="treeRef"
          :data="data"
          :props="treeProps"
          :default-expand-all="true"
          @node-click="handlerSelectDept"
          :filter-node-method="filterNode"
        />
      </el-col>
      <el-col :span="20">
        <el-button type="primary" size="default" @click="addUser">
          新增用户
        </el-button>
        <el-button type="danger" size="default" @click="">批量删除</el-button>
        <!-- table 展示用户信息 -->
        <el-table
          class="custom-el-table-class"
          :scrollbar-always-on="true"
          :data="userArr"
          :header-cell-style="{ background: '#F8F8F9' }"
        >
          <el-table-column type="selection" align="center"></el-table-column>
          <el-table-column
            type="index"
            label="#"
            align="center"
          ></el-table-column>
          <el-table-column
            label="ID"
            align="center"
            prop="id"
          ></el-table-column>
          <el-table-column
            label="用户名字"
            align="center"
            prop="username"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column
            label="用户名称"
            align="center"
            prop="name"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column
            label="用户角色"
            align="center"
            prop="roleName"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column
            label="创建时间"
            align="center"
            prop="createTime"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column
            label="更新时间"
            align="center"
            prop="updateTime"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column label="操作" width="300px" align="center">
            <template #="{ row, $index }">
              <el-button
                type="primary"
                size="small"
                icon="User"
                @click="setRole(row)"
              >
                分配角色
              </el-button>
              <el-button
                type="primary"
                size="small"
                icon="Edit"
                @click="updateUser(row)"
              >
                编辑
              </el-button>
              <el-button type="primary" size="small" icon="Delete" @click="">
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页器 -->
        <div class="fr">
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
      </el-col>
    </el-row>
  </el-card>
  <!-- 添加和修改用户 -->
  <!-- 分配权限 -->
</template>

<script setup lang="ts">
import { ref, onMounted, reactive, watch } from 'vue'
import { reqUserInfo, reqAddOrUpdateUser } from '@/api/acl/user'
import { UserResponseData, UserRecords, User } from '@/api/acl/user/type'
import { ElMessage, ElTree } from 'element-plus'
const data = [
  {
    label: '嗷嗷叫集团',
    children: [
      {
        label: '呜呜叫分公司',
        children: [
          {
            label: '开发组',
          },
          {
            label: '开发组',
          },
          {
            label: '开发组',
          },
          {
            label: '开发组',
          },
        ],
      },
      {
        label: '喳喳叫分公司',
        children: [
          {
            label: '开发组',
          },
          {
            label: '开发组',
          },
          {
            label: '开发组',
          },
          {
            label: '开发组',
          },
        ],
      },
    ],
  },
  {
    label: '嗷嗷叫集团',
    children: [
      {
        label: '呜呜叫分公司',
        children: [
          {
            label: '开发组',
          },
          {
            label: '开发组',
          },
          {
            label: '开发组',
          },
          {
            label: '开发组',
          },
        ],
      },
      {
        label: '喳喳叫分公司',
        children: [
          {
            label: '开发组',
          },
          {
            label: '开发组',
          },
          {
            label: '开发组',
          },
          {
            label: '开发组',
          },
        ],
      },
    ],
  },
]
/*
  树状数据部分
 */
interface Tree {
  [key: string]: any
}
const treeProps = { children: 'children', label: 'label' }
let treeTextSearch = ref<string>('')
const treeRef = ref<InstanceType<typeof ElTree>>()
const filterNode = (value: string, data: Tree) => {
  if (!value) return true
  return data.label.includes(value)
}
watch(treeTextSearch, (val) => {
  treeRef.value!.filter(val)
})
const handlerSelectDept = () => {
  console.log(defaultProps)
}

// 默认页码
let pageNo = ref<number>(1)
let pageSize = ref<number>(10)
let total = ref<number>(0)
let userArr = ref<UserRecords>([])
// 控制抽屉是否隐藏
let drawer = ref<boolean>(false)
// 控制分配角色业务抽屉是否隐藏
let drawerForRole = ref<boolean>(false)
// 收集用户信息数据
let userParams = reactive<User>({
  username: '',
  name: '',
  password: '',
})
// 是否全选
let checkAll = ref<boolean>(false)
// 设置不确定状态
let isIndeterminate = ref<boolean>(true)
// 所有角色数据
let allRole = ref(['销售', '前台', '财务', 'boss'])
let userRole = ref(['销售', '前台'])
// 获取已有用户数据
const getHasUser = async (pager = 1) => {
  pageNo.value = pager
  const result: UserResponseData = await reqUserInfo(
    pageNo.value,
    pageSize.value,
  )
  if (result.code == 200) {
    total.value = result.data.total
    userArr.value = result.data.records
  }
}
// 添加用户
const addUser = () => {
  drawer.value = true
}
// 修改用户
const updateUser = (row: User) => {
  drawer.value = true
  // 将行的数据获取到放入到参数中
  Object.assign(userParams, row)
}
// 打开分配角色框
const setRole = (row: User) => {
  drawerForRole.value = true
  Object.assign(userParams, row)
}
// 全选复选框的change
const handleCheckAllChange = (val: boolean) => {
  userRole.value = val ? allRole.value : []
  // 让不确定状态为false
  isIndeterminate.value = false
}
// 底部复选框 change 事件
const handleCheckedCitiesChange = (value: string[]) => {
  // 已经勾选的个数
  const checkedCound = value.length
  checkAll.value = checkedCound === allRole.value.length
  isIndeterminate.vlaue =
    checkedCound > 0 && checkedCound < allRole.value.length
}
const save = async () => {
  // 添加新用户 | 保存已有用户账号信息
  const result: any = await reqAddOrUpdateUser(userParams)
  if (result.code == 200) {
    drawer.value = false
    // 提示信息
    ElMessage({
      type: 'success',
      message: userParams.id ? '更新成功' : '添加成功',
    })
    // 更新留在当前页，添加留在第一页
    getHasUser(userParams.id ? pageNo.value : 1)
    // 让浏览器自动刷新一次
    // window.location.reload() // 这个操作感觉不合理
    /*
      TODO 判断一下是否是修改自己的账号，
      是的话就需要让浏览器自动刷新一下让它重新进行登录
      或者使用路由直接跳转到登录页面
    */
  } else {
    // 提示信息
    ElMessage({
      type: 'error',
      message: userParams.id ? '更新失败' : '添加失败',
    })
  }
}
// 抽屉关闭回调
const drawerClose = () => {
  Object.assign(userParams, {
    id: undefined,
    username: '',
    name: '',
    password: '',
  })
}
onMounted(() => {
  getHasUser()
})
</script>

<style scoped lang="scss">
.box {
  width: 100%;
  height: 400px;
  background: red;
}
.card-body-class {
  display: flex !important;
  background-color: red;
}
.main-left {
  width: 190px;
  margin-right: 10px;
}
.main-right {
  width: 100%;
}
.custom-tree-class {
  max-height: 300px;
  overflow: scroll;
}
.custom-el-table-class {
  margin: 10px 0;
  max-height: calc(100vh - $base-tabbar-height - 150px);
}
</style>

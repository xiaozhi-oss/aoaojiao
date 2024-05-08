<!-- eslint-disable vue/valid-attribute-name -->
<template>
  <el-card class="box-card">
    <!-- 卡片顶部添加品牌按钮 -->
    <el-button type="primary" size="default" icon="Plus" @click="addTrademark">
      添加品牌
    </el-button>
    <!-- 表格组件：展示数据 -->
    <el-table style="margin: 10px 0px" border :data="trademarkArr">
      <el-table-column
        label="序号"
        width="80px"
        align="center"
        type="index"
      ></el-table-column>
      <el-table-column label="品牌名称" prop="tmName"></el-table-column>
      <!-- 
          table-column：默认展示数据使用div，
          我们可以使用它提供的插槽来自定义我们自己的内容
          使用 #="{ row, $index }" 接收回传的数据，
          row表示的是每一行的数据，$index 就是每一个元素的下角标
        -->
      <el-table-column label="品牌LOGO">
        <template #="{ row, $index }">
          <img
            :src="row.logoUrl"
            style="height: 100px; width: 100px"
            alt="图片加载失败"
          />
        </template>
      </el-table-column>
      <el-table-column label="品牌操作">
        <template #="{ row, $index }">
          <el-button
            type="primary"
            size="small"
            icon="Edit"
            @click="updateTrademark(row)"
          ></el-button>
          <el-popconfirm
            :title="`您确定要删除${row.tmName}吗？`"
            width="250px"
            icon="Delete"
            @confirm="removeTrademark(row.id)"
          >
            <template #reference>
              <el-button type="primary" size="small" icon="Delete"></el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页器组件
          pagination
            current-page：设置分页器当前页码
            page-size="limit"：设置每页的条数
            page-sizes：选择每页多少条数据的配置
            small：设置分页器的类型
            background：激活按钮的背景色
            layout：设置分页器六个子组件的布局，
            可以显示或隐藏某个组件并且可以根据编写的顺序调整它们位置,
            还可以使用 ‘->’ 将组件放到最右边

      -->
    <el-pagination
      v-model:current-page="pageNo"
      v-model:page-size="limit"
      :page-sizes="[3, 5, 7, 9]"
      :background="true"
      layout="prev,  pager, next, jumper, ->,sizes, total"
      :total="total"
      @size-change="getHasTrademark(1)"
      @current-change="getHasTrademark"
    />
  </el-card>
  <!-- 
    对话框组件
      v-model 属性用于控制对话框组件显示或隐藏
      title：设置对话框左上角标题
  -->
  <el-dialog
    v-model="dialogFormVisible"
    :title="trademarkParams.id ? '修改品牌' : '添加品牌'"
  >
    <el-form
      style="width: 80%"
      :model="trademarkParams"
      :rules="rules"
      ref="fromRef"
    >
      <el-form-item label="品牌名称" label-width="80px" prop="tmName">
        <el-input
          placeholder="请你输入品牌名称"
          v-model="trademarkParams.tmName"
        ></el-input>
      </el-form-item>
      <el-form-item label="品牌LOGO" label-width="80px" prop="logoUrl">
        <!-- 
          upload 组件
            action：请求url，上传文件的接口
         -->
        <el-upload
          class="avatar-uploader"
          action="/api/admin/product/fileUpload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
        >
          <img
            v-if="trademarkParams.logoUrl"
            :src="trademarkParams.logoUrl"
            class="avatar"
          />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </el-form-item>
    </el-form>
    <!-- 具名插槽 -->
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="cancel">取消</el-button>
        <el-button type="primary" @click="confrim">确定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import {
  reqHasTrademark,
  reqAddOrUpdateTrademark,
  reqDeleteTrademark,
} from '@/api/product/trademark'
import {
  Records,
  Trademark,
  TrademarkResponseData,
} from '@/api/product/trademark/type'
import { onMounted, reactive, ref, nextTick } from 'vue'
import { ElMessage, type UploadProps } from 'element-plus'
// 当前页码
let pageNo = ref<number>(1)
// 每一页展示多少条数据
let limit = ref<number>(3)
// 存储已有品牌数量的总数
let total = ref<number>(0)
// 存储已有品牌的数据
let trademarkArr = ref<Records>([])
// 控制对话框显示与隐藏
let dialogFormVisible = ref<boolean>(false)
// 定义收集新增品牌数据
let trademarkParams = reactive<Trademark>({
  tmName: '',
  logoUrl: '',
})
// 获取 el-form 组件实例
let fromRef = ref()
// 发送请求获取已有品牌数据
// (pager=1) -> 如果没有传值，那么默认为 1
const getHasTrademark = async (page = 1) => {
  pageNo.value = page
  const result: TrademarkResponseData = await reqHasTrademark(
    pageNo.value,
    limit.value,
  )
  if (result.code == 200) {
    total.value = result.data.total
    trademarkArr.value = result.data.records
  }
}
// 对于当前页面发生变化自定义事件，组件 paginatin 父组件回传了数据 -> 当前页码
// const changePageNo = (pageNo) => {
//   // 测试输出回传的数据
//   console.log(pageNo)
//   // 再次发送请求获取数据
//   getHasTrademark()
// }

// 当下拉菜单发生变化时触发此方法
// 分页器会将分页条数返回
// const sizeChange = (pageSize: number) => {
//   // console.log(pageSize)
//   // 当每一页的数据量发生变化时，页码归1
//   // pageNo.value = 1
//   // 再次发送请求获取数据
//   getHasTrademark()
// }

// 添加品牌按钮回调
const addTrademark = () => {
  // 清空收集的数据
  trademarkParams.tmName = ''
  trademarkParams.logoUrl = ''
  // 这里是因为用户可能是先点击修改然后再点击的添加就会导致title没有发生变化
  trademarkParams.id = 0
  // 显示对话框
  dialogFormVisible.value = true
  // 清除校验信息 -> 这是因为打开了又关闭就会导致信息的残留，所以我们要清除
  // 第一种做法：es6 的问号语法 -> 对象存在才会进行调用，不存在就不调用
  // fromRef.value?.clearValidate('tmName')
  // fromRef.value?.clearValidate('logoUrl')
  // 第二种：使用 nextTick() 获取到更新后的 dom
  nextTick(() => {
    fromRef.value.clearValidate('tmName')
    fromRef.value.clearValidate('logoUrl')
  })
  /*
   可以在对话框关闭时清空校验信息：
      在 dailog 的 close 函数中进行清除(推荐)
      监听控制 dailog 隐藏和显式的变量来进行清除
  */
}
// 修改品牌按钮回调
// row 参数是单行的数据对象，点击后进行传递
// eslint-disable-next-line @typescript-eslint/no-unused-vars
const updateTrademark = (row: Trademark) => {
  // 清除错误信息
  nextTick(() => {
    fromRef.value.clearValidate('tmName')
    fromRef.value.clearValidate('logoUrl')
  })
  // 展示要修改品牌的数据
  // trademarkParams.tmName = row.tmName
  // trademarkParams.logoUrl = row.logoUrl
  // trademarkParams.id = row.id
  // ES6语法
  // 将 row 的值赋值给 trademarkParams
  Object.assign(trademarkParams, row)
  // 显示对话框
  dialogFormVisible.value = true
}
// 品牌自定义校验规则方法
const validatorTmName = (rule: any, value: any, callBack: any) => {
  // 自定义校验规则
  // 清除空格之后的长度至少两位
  if (value.trim().length >= 2) {
    callBack()
  } else {
    // 校验未通过，显示错误信息
    callBack(new Error('品牌名称位数至少两位'))
  }
}
const validatorLogoUrl = (rule: any, value: any, callBack: any) => {
  console.log(123)
  // 自定义校验规则
  if (value) {
    callBack()
  } else {
    callBack(new Error('LOGO图片务必上传'))
  }
}
// 表单校验规则对象
const rules = {
  tmName: [{ required: true, traigger: 'blur', validator: validatorTmName }],
  logoUrl: [{ required: true, validator: validatorLogoUrl }],
}
// 对话框取消按钮
const cancel = () => {
  // 显示对话框
  dialogFormVisible.value = false
}
// 对话框确认按钮
const confrim = async () => {
  // 发起请求之前对整个表单进行校验
  // await 一个返回 error 的 Promise就不会往下继续执行
  await fromRef.value.validate()
  const result: any = await reqAddOrUpdateTrademark(trademarkParams)
  if (result.code == 200) {
    // 再次发送请求获取最新数据
    getHasTrademark(trademarkParams.id ? pageNo.value : 1)
    // 关闭对话框
    dialogFormVisible.value = false
    ElMessage({
      type: 'success',
      message: trademarkParams.id ? '修改品牌成功' : '添加品牌成功',
    })
  } else {
    ElMessage({
      type: 'error',
      message: trademarkParams.id ? '修改品牌失败' : '添加品牌失败',
    })
  }
}
// 上传图片之前调用的钩子，上传文件之前可以约束文件类型和大小
// return ture表示可以上传， false 不可上传
const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
  // rawFile -> 文件对象
  // 限制文件类型：png | jpg | gif    文件大小：4M
  if (
    (rawFile.type == 'image/png' ||
      rawFile.type == 'image/jpeg' ||
      rawFile.type == 'image/gif') &&
    rawFile.size / 1024 / 1024 < 4
  ) {
    // 可以上传
    return true
  } else {
    ElMessage({
      type: 'error',
      message: '上传文件格式必须是 png | jpg | gif',
    })
    return false
  }
}
// 文件上传成功触发的钩子
const handleAvatarSuccess: UploadProps['onSuccess'] = (
  response,
  uploadFile,
) => {
  /*
    接收参数 (response, uploadFile)
      response 就是服务器返回的数据
      uploadFile 文件信息
  */
  trademarkParams.logoUrl = response.data
  // 图片上传成功，清除对应图片的校验结果
  fromRef.value.clearValidate('logoUrl')
  ElMessage({
    type: response.code == 200 ? 'success' : 'error',
    message:
      response.code == 200 ? '图片上传成功' : '图片上传失败，请联系管理员解决',
  })
}
// 气泡确认框确认按钮的回调
const removeTrademark = async (id: number) => {
  const result = await reqDeleteTrademark(id)
  if (result.code == 200) {
    ElMessage({
      type: 'success',
      message: '品牌删除成功',
    })
    // 再次获取已有的品牌数据并且删除之后停留在当前页，如果当前页已经没有数据了就跳到上一页
    getHasTrademark(
      trademarkArr.value.length > 1 ? pageNo.value : pageNo.value - 1,
    )
  } else {
    ElMessage({
      type: 'error',
      message: '品牌删除失败',
    })
  }
}

// 组件挂载完成发送一次请求，获取第一页的数据
onMounted(() => {
  getHasTrademark()
})
</script>

<style scoped lang="scss">
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>

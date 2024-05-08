<template>
  <div class="login_container">
    <el-row class="row-con">
      <el-col :span="6" :offset="15" :xs="24">
        <div class="main-con">
          <Logo></Logo>
          <el-tabs
            v-model="activeName"
            class="login-type-tabs"
            @tab-click="handleClick"
            @tab-change="handleTabChange"
            stretch
          >
            <el-tab-pane label="账号密码登录" name="username_pwd">
              <el-form
                :model="loginForm"
                :rules="rules"
                ref="formRef"
                v-if="activeName == 'username_pwd'"
              >
                <el-form-item prop="username">
                  <el-input
                    v-model="loginForm.username"
                    size="large"
                    placeholder="请输入用户名"
                  ></el-input>
                </el-form-item>
                <el-form-item prop="password">
                  <el-input
                    v-model="loginForm.password"
                    type="password"
                    show-password
                    size="large"
                    placeholder="请输入密码"
                  ></el-input>
                </el-form-item>
                <el-form-item prop="verificationCode">
                  <div class="login-input-img-con">
                    <el-input
                      v-model="loginForm.verificationCode"
                      size="large"
                      placeholder="请输入验证码"
                      :input-style="{ width: '100%' }"
                    ></el-input>
                    <img
                      @click="flushVerificationCode"
                      style="width: 130px; height: 40px; margin-left: 20px"
                      :src="captcheImg"
                    />
                  </div>
                </el-form-item>
              </el-form>
            </el-tab-pane>
            <el-tab-pane label="短信登录" name="phone_number">
              <el-form
                :model="loginForm"
                :rules="rules"
                ref="formRef"
                v-if="activeName == 'phone_number'"
              >
                <el-form-item prop="phoneNumber">
                  <el-input
                    v-model="loginForm.phoneNumber"
                    size="large"
                    placeholder="请输入用户名"
                  ></el-input>
                </el-form-item>
                <el-form-item prop="code">
                  <div class="login-input-img-con">
                    <el-input
                      v-model="loginForm.code"
                      :disabled="isCodeDisabled"
                      size="large"
                      placeholder="请输入验证码"
                      :input-style="{ width: '100%' }"
                    ></el-input>
                    <el-button type="primary" size="large" @click="getCode">
                      {{ buttonText }}
                    </el-button>
                  </div>
                </el-form-item>
              </el-form>
            </el-tab-pane>
            <el-tab-pane label="邮箱登录" name="email">
              <el-form
                :model="loginForm"
                :rules="rules"
                ref="formRef"
                v-if="activeName == 'email'"
              >
                <el-form-item prop="email">
                  <el-input
                    v-model="loginForm.email"
                    size="large"
                    placeholder="请输入用户名"
                  ></el-input>
                </el-form-item>
                <el-form-item prop="code">
                  <div class="login-input-img-con">
                    <el-input
                      v-model="loginForm.code"
                      size="large"
                      placeholder="请输入验证码"
                      :input-style="{ width: '100%' }"
                    ></el-input>
                    <el-button
                      type="primary"
                      size="large"
                      :disabled="isCodeDisabled"
                      @click="getCode"
                    >
                      {{ buttonText }}
                    </el-button>
                  </div>
                </el-form-item>
              </el-form>
            </el-tab-pane>
          </el-tabs>
          <el-button
            class="login_btn"
            type="primary"
            size="large"
            @click="login"
            :loading="loading"
            round
          >
            登录
          </el-button>
          <div class="main-footer">
            <el-divider>其他登录方式</el-divider>
            <div class="other-login-type">
              <el-link
                :href="otherLoginBasicUrl + name"
                :underline="false"
                v-for="(name, index) in otherLoginType"
                :key="index"
              >
                <template #icon>
                  <SvgIcon :name="name" :width="iconSize" :height="iconSize" />
                </template>
              </el-link>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import Logo from '@/layout/Logo/index.vue'
import SvgIcon from '@/components/SvgIcon/index.vue'
import { reactive, ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import useUserStore from '@/store/modules/user'
import { ElNotification, ElMessage } from 'element-plus'
import { getTime } from '@/utils/time'
import { reqCaptcheImg, reqEmailCode } from '@/api/login'

onMounted(() => {
  getCaptcheImg()
})

// 常量
const iconSize = '32px'
const otherLoginBasicUrl = '/api/admin/login/'
const otherLoginType = ['gitee', 'github', 'qq']
const loginType = {
  usernamePwd: 'username_pwd',
  email: 'email',
  phoneNumber: 'phone_number',
}

// tabs 激活tab的名字
let activeName = ref<string>('username_pwd')
// tabs 切换事件 -> 清除记录状态
const handleTabChange = (type: any) => {
  loginForm.loginType = type
  loginForm.code = ''
  buttonText.value = '发送验证码'
  time.value = 0
}

// 验证码图片地址
let captcheImg = ref<string>('')
// 刷新图片验证码
const flushVerificationCode = () => {
  getCaptcheImg()
}
// 获取验证码
const getCaptcheImg = async () => {
  await reqCaptcheImg().then((res) => {
    const result = res.data
    loginForm.uuid = result.uuid
    captcheImg.value = result.base64Img
  })
}

// 验证码按钮是否禁用
let isCodeDisabled = ref<boolean>(false)
let buttonText = ref<string>('发送验证码')
// 按钮禁用倒计时
let time = ref<number>(60)
// 获取手机验证码
const getCode = () => {
  // 判断是手机验证码还是邮箱验证码
  const type = loginForm.loginType
  if (type == loginType.email) {
    getEmailCode()
  } else if (type == loginType.phoneNumber) {
    ElMessage({
      message: '暂时不支持该登录方式',
      type: 'error',
    })
  } else {
    ElMessage({
      type: 'error',
      message: '程序异常，请联系管理员解决',
    })
  }
}
const getEmailCode = async () => {
  reqEmailCode(loginForm.email).then(() => {
    ElMessage.success('验证码发送成功')
    disableBtn()
  })
}
const disableBtn = () => {
  if (isCodeDisabled.value) return
  console.log('进来')
  time.value = 60
  // 禁用时间
  isCodeDisabled.value = true
  buttonText.value = '已发送'
  const timer = setInterval(() => {
    buttonText.value = `${time.value}秒后重试`
    time.value -= 1
    // 为 0 则关闭定时器
    if (time.value <= 0) {
      clearInterval(timer)
      buttonText.value = '发送验证码'
      isCodeDisabled.value = false
      time.value = 60
    }
  }, 1000)
}

// 使用用户仓库
let useStore = useUserStore()
// 获取路由器对象
const $router = useRouter()
// 获取路由对象
const $route = useRoute()
// 定义变量控制按钮加载效果
let loading = ref(false)
// 表单实例对象
let formRef = ref()
// 收集账号与密码的数据
let loginForm = reactive({
  username: 'sys_test',
  password: '11111111',
  verificationCode: '',
  email: '',
  phoneNumber: '',
  code: '',
  uuid: '',
  loginType: 'username_pwd', // 默认账号密码登录
})
// 自定义校验规则
const validatorEamil = (rule: any, value: any, callback: any) => {
  // rule：即为校验规则对象
  // value：即为表单元素文本内容
  // callback：符合条件就使用它放行通过，不符合条件还是调用它并注入错误提示信息
  const EmailReg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/
  if (EmailReg.test(value)) {
    callback()
  } else {
    callback(new Error('邮箱格式不对'))
  }
}
const validatorPhoneNumber = (rule: any, value: any, callback: any) => {
  // rule：即为校验规则对象
  // value：即为表单元素文本内容
  // callback：符合条件就使用它放行通过，不符合条件还是调用它并注入错误提示信息
  const PhoneReg =
    /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
  if (PhoneReg.test(value)) {
    callback()
  } else {
    callback(new Error('手机号格式不对'))
  }
}

// 定义表单校验需要配置的对象
const rules = {
  username: [
    {
      required: true,
      min: 5,
      max: 10,
      message: '账号长度五到十位',
      trigger: 'blur',
    },
    // 自定义校验规则
    // { trigger: 'change', validator: validatorUserName },
  ],
  password: [
    {
      required: true,
      min: 6,
      max: 32,
      message: '密码长度至少8位',
      trigger: 'blur',
    },
  ],
  email: [{ required: true, trigger: 'blur', validator: validatorEamil }],
  phoneNumber: [
    { required: true, trigger: 'blur', validator: validatorPhoneNumber },
  ],
  verificationCode: [
    { required: true, trigger: 'blur', message: '验证码不能为空' },
  ],
}
const login = () => {
  // 校验表单
  formRef.value.validate(async (valid) => {
    // 校验通过才发送请求
    if (valid) {
      // 开始加载
      loading.value = true
      // 点击登录按钮携带数据发起请求
      reqLogin()
    }
  })
}
const reqLogin = async () => {
  await useStore
    .userLogin(loginForm)
    .then((res) => {
      // 判断登录时是否携带query参数，如果有就忘query参数中指定的地址跳，没有就跳转到首页
      const redirect: any = $route.query.redirect
      $router.push({ path: redirect || '/' })
      // 登录成功提示信息
      ElNotification({
        type: 'success',
        message: '登录成功',
        title: `HI, ${getTime()}好`,
      })
      loading.value = false
      if (res.code == '00000') {
        return true
      } else {
        return false
      }
    })
    .catch(() => {
      // 登录失败停止加载
      loading.value = false
      // 登录成功提示信息
      ElNotification({
        type: 'error',
        message: '登录失败',
      })
    })
}
</script>

<style scoped lang="scss">
.login_container {
  width: 100%;
  height: 100vh;
  background: url('@/assets/images/background.jpg') no-repeat;
  background-size: cover;
  .row-con {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100vh;
    .main-con {
      background-color: #fff;
      border-radius: 20px;
      padding: 15px 25px;
      box-shadow: var(--el-box-shadow);
      .login_btn {
        width: 100%;
      }
      // 验证码块的容器样式
      .login-input-img-con {
        width: 100%;
        display: flex;
      }
    }
  }
  // 登录框底部 - 其他登录方式
  .main-footer {
    padding: 20px 0;
    .other-login-type {
      display: flex;
      justify-content: space-evenly;
    }
  }
}
</style>

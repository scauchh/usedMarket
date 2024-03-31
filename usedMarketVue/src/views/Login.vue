<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { useTokenStore } from '@/store/token.js'
import { useUserInfoStore } from '@/store/userinfo.js'
import { userLoginService, userRegisterService, getUserInfoService } from '@/api/user.js'

// 控制登录/注册界面
const isRegister = ref(false)
// 控制重置密码弹窗
const dialogVisible = ref(false)
// 路由配置
const router = useRouter()
// 使用token存储服务
const tokenStore = useTokenStore()
// 使用用户信息存储服务
const userInfoStore = useUserInfoStore()

// 用户登录/注册数据模型
const userData = ref({
  userName: '',
  password: '',
  rePassword: ''
})

// 重置密码数据模型
const rePasswordData = ref({
  userName: '',
  newPassword: ''
})

// 确认密码验证
const rePasswordValid = (rule, value, callback) => {
  if (value == null || value === '')
    return callback(new Error('请再次确认密码'))
  else if (userData.value.password !== value)
    return callback(new Error('两次输入密码不一致'))
  else
    return callback();
}

// 登录/注册数据验证
const userDataRules = ref({
  userName: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 5, max: 16, message: '用户名的长度必须为5~16位', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 5, max: 16, message: '密码长度必须为5~16位', trigger: 'blur' }
  ],
  rePassword: [
    { validator: rePasswordValid, trigger: 'blur' }
  ]
})

// 清除数据缓存
const clearData = () => {
  userData.value.password = ''
  userData.value.rePassword = ''
}

// 用户注册
const register = async () => {
  await userRegisterService(userData.value)
  ElMessage.success("注册成功")
  isRegister.value = false
  clearData()
}

// 用户登录
const login = async () => {
  let result = await userLoginService(userData.value)
  tokenStore.setToken(result.data)
  await getUserInfo()
  await router.push('/')
}

// 获取用户数据
const getUserInfo = async () => {
  let result = await getUserInfoService()
  userInfoStore.setInfo(result.data)
}
</script>

<template>
  <el-row class="login-page">
    <el-col :span="7" class="form">
      <!-- 注册表单 -->
      <el-form ref="form" size="large" autocomplete="off" v-if="isRegister" :model="userData" :rules="userDataRules">
        <el-form-item>
          <h1>注册</h1>
        </el-form-item>
        <el-form-item prop="userName">
          <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="userData.userName"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="userData.password"></el-input>
        </el-form-item>
        <el-form-item prop="rePassword">
          <el-input :prefix-icon="Lock" type="password" placeholder="请输入再次密码" v-model="userData.rePassword"></el-input>
        </el-form-item>
        <!-- 注册按钮 -->
        <el-form-item>
          <el-button class="button" type="primary" auto-insert-space @click="register">注册</el-button>
        </el-form-item>
        <el-form-item class="flex">
          <el-link type="info" :underline="false" @click="isRegister = false; clearData()">← 返回</el-link>
        </el-form-item>
      </el-form>
      <!-- 登录表单 -->
      <el-form ref="form" size="large" autocomplete="off" v-else :model="userData" :rules="userDataRules">
        <el-form-item>
          <h1>登录</h1>
        </el-form-item>
        <el-form-item prop="userName">
          <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="userData.userName"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input name="password" :prefix-icon="Lock" type="password" placeholder="请输入密码"
            v-model="userData.password"></el-input>
        </el-form-item>
        <!-- 登录按钮 -->
        <el-form-item>
          <el-button class="button" type="primary" auto-insert-space @click="login">登录</el-button>
        </el-form-item>
        <div class="flex">
          <el-link type="info" :underline="false" @click="isRegister = true; clearData()">注册 →</el-link>
          <el-link type="primary" :underline="false" @click="dialogVisible = true">忘记密码？</el-link>
        </div>
      </el-form>
    </el-col>
  </el-row>
  <!-- 重置密码弹窗 -->
  <el-dialog v-model="dialogVisible" title="重置密码" width="30%">
    <el-form :model="rePasswordData" :rules="rules" label-width="100px" style="padding-right: 30px">
      <el-form-item label="用户名" prop="userName">
        <el-input v-model="rePasswordData.userName" minlength="1" maxlength="10"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword">
        <el-input v-model="rePasswordData.newPassword" minlength="1" maxlength="15"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary"> 确认 </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style lang="scss" scoped>
.login-page {
  height: 100vh;
  background: url('@/assets/background.jpg') no-repeat center / cover;
  display: flex;
  justify-content: center;
  align-items: center;

  .form {
    padding: 20px 30px;
    height: 65vh;
    display: flex;
    flex-direction: column;
    justify-content: center;
    user-select: none;
    border-radius: 20px;
    background-color: #fff;

    .title {
      margin: 0 auto;
    }

    .button {
      width: 100%;
    }

    .flex {
      width: 100%;
      display: flex;
      justify-content: space-between;
    }
  }
}
</style>
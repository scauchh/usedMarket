<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useTokenStore } from '@/store/token.js'
import { useUserInfoStore } from '@/store/userinfo.js'
import { updateUserInfoService, getUserInfoService } from '@/api/user.js'
import avatar from '@/assets/default.png'

// 使用token存储服务
const tokenStore = useTokenStore()
// 使用用户信息存储服务
const userInfoStore = useUserInfoStore()
// 控制是否处于编辑状态
const isEdit = ref(false)
// 控制预览图是否显示
const visibleDialog = ref(false)
// 控制预览图的图片
const prePicture = ref("")

// 用户数据模型
const userData = ref({
  userID: '',
  userName: '',
  nickName: '',
  avatar: '',
  gender: '',
  email: '',
  phone: ''
})

// 刷新数据
const refresh = async () => {
  let result = await getUserInfoService()
  userData.value = result.data
  isEdit.value = false
}

// 初始化界面
onMounted(async () => {
  refresh()
})

// 用户数据验证
const userDataRules = {
  userName: [
    { required: true, message: '', trigger: 'blur' }
  ],
  nickName: [
    { required: true, message: '请输入用户昵称', trigger: 'blur' },
    { min: 2, max: 16, message: '昵称的长度必须为2~16位', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '', trigger: 'blur' }
  ],
  email: [
    { type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
  ],
  phone: [
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
  ],
  wechat: [
    { min: 0, max: 20, message: '微信号的长度不能超过20位', trigger: 'blur' }
  ],
  address: [
    { min: 0, max: 20, message: '地址的长度不能超过20位', trigger: 'blur' }
  ]
}

// 头像上传成功的回调函数
const uploadSuccess = (result) => {
  if (result.code === 0) {
    userData.value.avatar = result.data
  } else {
    ElMessage.error(result.message)
  }
}

// 头像上传失败的回调函数
const uploadError = (result) => {
  ElMessage.error("图片大小不能超过5MB")
}

// 编辑信息
const editInfo = () => {
  isEdit.value = true
}

// 更新用户数据
const updateUserInfo = async () => {
  await updateUserInfoService(userData.value)
  let result = await getUserInfoService()
  userInfoStore.setInfo(result.data)
  isEdit.value = false
  refresh()
  ElMessage.success("修改成功")
}

// 打开预览图
const showPreview = (picture) => {
  visibleDialog.value = true
  prePicture.value = picture
}
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>基本资料</span>
      </div>
    </template>
    <el-row>
      <el-col :span="12">
        <!-- 编辑信息 -->
        <el-form v-if="isEdit" :model="userData" :rules="userDataRules" label-width="100px" size="large">
          <el-form-item label="用户名" prop="userName">
            <el-input v-model="userData.userName" disabled style="width: 300px"></el-input>
          </el-form-item>
          <el-form-item label="昵称" prop="nickName">
            <el-input v-model="userData.nickName" style="width: 300px"></el-input>
          </el-form-item>
          <el-form-item label="头像" prop="avatar">
            <el-upload class="avatar-uploader" action="/api/uploadImg" :auto-upload="true" :show-file-list="false"
              :headers="{ 'Authorization': tokenStore.token }" :on-success="uploadSuccess" :on-error="uploadError">
              <img v-if="userData.avatar" :src="userData.avatar" class="avatar" />
              <img v-else :src="avatar" width="200" />
            </el-upload>
          </el-form-item>
          <el-form-item label="性别" prop="gender">
            <el-radio-group v-model="userData.gender" @change="$forceUpdate()" class="ml-4">
              <el-radio value='1' size="large">男</el-radio>
              <el-radio value='2' size="large">女</el-radio>
              <el-radio value='0' size="large">不愿透露</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="userData.email" style="width: 300px"></el-input>
          </el-form-item>
          <el-form-item label="手机" prop="phone">
            <el-input v-model="userData.phone" style="width: 300px"></el-input>
          </el-form-item>
          <el-form-item label="微信号" prop="wechat">
            <el-input v-model="userData.wechat" style="width: 300px"></el-input>
          </el-form-item>
          <el-form-item label="地址" prop="address">
            <el-input v-model="userData.address" style="width: 300px"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updateUserInfo">确认修改</el-button>
            <el-button type="info" @click="refresh">取消编辑</el-button>
          </el-form-item>
        </el-form>
        <!-- 展示信息 -->
        <el-form v-else label-width="100px" size="large">
          <el-form-item label="用户名" prop="userName">{{userData.userName}}</el-form-item>
          <el-form-item label="昵称" prop="nickName">{{ userData.nickName }}</el-form-item>
          <el-form-item label="头像" prop="avatar">
            <img v-if="userData.avatar" :src="userData.avatar" class="avatar" @click="showPreview(userData.avatar)"/>
            <img v-else :src="avatar" width="200" @click="showPreview(avatar)"/>
          </el-form-item>
          <el-form-item label="性别" prop="gender">
            <el-text v-if="userData.gender==1">男</el-text>
            <el-text v-else-if="userData.gender==2">女</el-text>
            <el-text v-else>不愿透露</el-text>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">{{ userData.email }}</el-form-item>
          <el-form-item label="手机" prop="phone">{{ userData.phone }}</el-form-item>
          <el-form-item label="微信号" prop="wechat">{{ userData.wechat }}</el-form-item>
          <el-form-item label="地址" prop="address">{{ userData.address }}</el-form-item>
          <el-form-item>
            <el-button type="primary" @click="editInfo">编辑信息</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
  <!-- 预览图 -->
  <el-dialog v-model="visibleDialog" draggable width="35%">
    <img :src="prePicture" style="width: 100%; height: auto;" />
  </el-dialog>
</template>

<style lang="scss" scoped>
.avatar-uploader {
  :deep() {
    .avatar {
      width: 200px;
      height: 200px;
      display: block;
    }

    .el-upload {
      border: 1px dashed var(--el-border-color);
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      transition: var(--el-transition-duration-fast);
    }

    .el-upload:hover {
      border-color: var(--el-color-primary);
    }

    .el-icon.avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 200px;
      height: 200px;
      text-align: center;
    }
  }
}

.avatar {
  width: 200px;
  height: 200px;
  display: block;
}
</style>
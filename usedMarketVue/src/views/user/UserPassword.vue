<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getAllQuestionService } from '@/api/question.js'
import { saveRelationService, getCurrentRelationService } from '@/api/relation.js'
import { updatePasswordService } from '@/api/user.js'

// 密保问题数据模型
const questionData = ref([])
// 用户的密保问题及答案
const userQuestion = ref({})
// 新密码
const newPassword = ref({
  password:'',
  rePassword:''
})

// 密保问题验证
const userQuestionRules = {
  question: [
    { required: true, message: '请输入密保问题', trigger: 'blur' }
  ],
  answer: [
    { required: true, message: '回答不能为空', trigger: 'blur' },
    { min: 1, max: 15, message: '回答的长度不能超过15位', trigger: 'blur' }
  ]
}

// 确认密码验证
const rePasswordValid = (rule, value, callback) => {
  if (value == null || value === '')
    return callback(new Error('请再次确认密码'))
  else if (newPassword.value.password !== value)
    return callback(new Error('两次输入密码不一致'))
  else
    return callback();
}

// 新密码数据验证
const newPasswordRules = {
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 5, max: 16, message: '密码长度必须为5~16位', trigger: 'blur' }
  ],
  rePassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    { validator: rePasswordValid, trigger: 'blur' }
  ]
}

// 刷新数据
const refresh = async () => {
  let result = await getAllQuestionService()
  questionData.value = result.data
  result = await getCurrentRelationService()
  userQuestion.value = result.data
}

// 初始化页面
onMounted(async () => {
  refresh()
})

// 保存新的密保问题
const saveQuestion = async()=>{
  await saveRelationService(userQuestion.value)
  ElMessage.success("保存成功")
}

// 更新用户密码
const updatePassword = async() => {
  await updatePasswordService(newPassword.value);
  ElMessage.success("更新成功")
}
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>密保问题</span>
      </div>
    </template>
    <el-row>
      <el-col :span="12">
        <el-form label-width="100px" size="large" :model="userQuestion" :rules="userQuestionRules">
          <el-form-item label="密保问题：" prop="question">
            <el-select placeholder="请选择密保问题" v-model="userQuestion.questionID">
              <el-option v-for="item in questionData" :label="item.item" :value="item.questionID" />
            </el-select>
          </el-form-item>
          <el-form-item label="您的答案：" prop="answer">
            <el-input v-model="userQuestion.answer"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="saveQuestion">确认修改</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
  <br>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>修改密码</span>
      </div>
    </template>
    <el-row>
      <el-col :span="12">
        <el-form label-width="100px" size="large" :model="newPassword" :rules="newPasswordRules">
          <el-form-item label="新密码：" prop="password">
            <el-input v-model="newPassword.password" type="password" placeholder="请输入新密码" show-password></el-input>
          </el-form-item>
          <el-form-item label="确认密码：" prop="rePassword">
            <el-input v-model="newPassword.rePassword" type="password" placeholder="请再次输入密码" show-password></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updatePassword">确认修改</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
</template>

<style lang="scss" scoped>

</style>
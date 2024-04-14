<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getAllQuestionService } from '@/api/question.js'
import { saveRelationService, getCurrentRelationService } from '@/api/relation.js'

// 密保问题数据模型
const questionData = ref([])
// 用户的密保问题及答案
const userQuestion = ref({})

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
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>密码中心</span>
      </div>
    </template>
    <el-row>
      <el-col :span="12">
        <el-form label-width="100px" size="large">
          <el-form-item label="密保问题：">
            <el-select placeholder="请选择密保问题" v-model="userQuestion.questionID">
              <el-option v-for="item in questionData" :label="item.item" :value="item.questionID" />
            </el-select>
          </el-form-item>
          <el-form-item label="您的答案：" >
            <el-input v-model="userQuestion.answer"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="saveQuestion">确认修改</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
</template>

<style lang="scss" scoped>

</style>
<script setup>
import { ElMessage, ElMessageBox } from 'element-plus'
import { Edit, Delete } from '@element-plus/icons-vue'
import { ref, onMounted } from 'vue'
import { addQuestionService, 
  deleteQuestionService, 
  changeQuestionService,
  getAllQuestionService 
} from '@/api/question.js'
import { useUserInfoStore } from '@/store/userinfo.js'

// 使用用户信息存储服务
const userInfoStore = useUserInfoStore()
// 判断是否为管理员
const isManager = ref(true)
// 控制弹窗是否显示
const dialogVisible = ref(false)
// 控制弹窗标题
const title = ref("添加密保问题")

// 密保问题数据模型
const questions = ref([])
// 添加密保问题模型
const questionModel = ref({
  questionID: '',
  item: ''
})
// 保存旧密保问题的信息
const oldQuestionModel = ref({
  oldQuestionID: '',
  oldItem: ''
})

// 密保问题验证
const questionModelRules = ref({
  questionID: [
    { required: true, message: '请输入密保问题编号', trigger: 'blur' }
  ],
  item: [
    { required: true, message: '请输入密保问题', trigger: 'blur' },
    { min: 1, max: 15, message: '密保问题的长度不能超过15位', trigger: 'blur' }
  ]
})

// 刷新数据
const refresh = async () => {
  let result = await getAllQuestionService()
  questions.value = result.data
  if(userInfoStore.info.role === 0) isManager.value = false;
}

// 初始化页面
onMounted(async () => {
  refresh()
})

// 弹出添加类别弹窗
const preAddQuestion = () => {
  title.value = "添加密保问题"
  questionModel.value = {
    questionID: '',
    item: ''
  }
  dialogVisible.value = true;
}

// 添加/修改密保问题
const addQuestion = async () => {
  if(title.value==="添加密保问题"){
    await addQuestionService(questionModel.value)
    ElMessage.success("添加成功")
  }else if(title.value==="修改密保问题") {
    await changeQuestionService(oldQuestionModel.value, questionModel.value)
    ElMessage.success("修改成功")
  }
  dialogVisible.value = false;
  refresh()
}

// 删除密保问题
const deleteQuestion = async (row) => {
  ElMessageBox.confirm("您确定要删除该密保问题吗?<br>( 注:无法删除正在被使用的密保问题! )", "温馨提示", {
      confirmButtonClass: "确定",
      cancelButtonClass: "取消",
      type: "warning",
      dangerouslyUseHTMLString: true
    }).then(async() => {
      await deleteQuestionService(row.questionID)
      ElMessage.success("删除成功")
      refresh()
    })
}

// 修改密保问题
const changeQuestion = async (row) => {
  title.value = "修改密保问题"

  oldQuestionModel.value.oldQuestionID = row.questionID
  oldQuestionModel.value.oldItem = row.item

  questionModel.value.questionID = row.questionID
  questionModel.value.item = row.item
  dialogVisible.value = true;
}
</script>

<template>
  <el-card v-if="isManager" class="page-container">
    <template #header>
      <div class="header">
        <h1 class="title">密保问题</h1>
        <el-button type="primary" @click="preAddQuestion">添加密保问题</el-button>
      </div>
    </template>
    <el-table :data="questions" style="width: 100%">
      <el-table-column label="问题编号" prop="questionID"></el-table-column>
      <el-table-column label="密保问题" prop="item"></el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-tooltip effect="light" placement="left" content="编辑">
            <el-button :icon="Edit" circle plain type="primary" @click="changeQuestion(row)" style="margin: 20px auto"></el-button>
          </el-tooltip>
          <el-tooltip effect="light" placement="right" content="删除">
            <el-button :icon="Delete" circle plain type="danger" @click="deleteQuestion(row)"></el-button>
          </el-tooltip>
        </template>
      </el-table-column>
      <template #empty>
          <el-empty description="没有数据" />
      </template>
    </el-table>
  </el-card>
  <!-- 添加/修改密保问题弹窗 -->
  <el-dialog v-model="dialogVisible" draggable style="width: 30%; border-radius: 10px; padding: 1% 3% 3% 3%;">
    <el-form style="color: black; font-size: 18px;">
      <h1>{{ title }}</h1>
    </el-form>
    <el-form :model="questionModel" :rules="questionModelRules" label-width="90px" style="padding: 20px 40px 30px 0">
      <el-form-item label="问题编号" prop="questionID">
        <el-input-number v-model="questionModel.questionID" :precision="0" :step="1" :max="1000" :min="0"/>
      </el-form-item>
      <br>
      <el-form-item label="密保问题" prop="item">
        <el-input v-model="questionModel.item" minlength="1" maxlength="15"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="addQuestion"> 确认 </el-button>
    </template>
  </el-dialog>
</template>

<style lang="scss" scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;

    .title {
      margin: auto 10px;
    }
  }
}
</style>

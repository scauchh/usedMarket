<script setup>
import { ElMessage } from 'element-plus'
import { Edit, Delete } from '@element-plus/icons-vue'
import { ref, onMounted } from 'vue'
import { addTypeService, 
  deleteTypeService, 
  getAllTypeService 
} from '@/api/type.js'

// 控制分类弹出是否显示
const dialogVisible = ref(false)

// 书籍类别数据模型
const bookType = ref([])
// 添加类别数据模型
const typeModel = ref({
  typeID: '',
  typeName: ''
})

// 刷新数据
const refresh = async () => {
  let result = await getAllTypeService()
  bookType.value = result.data
}

// 初始化页面
onMounted(async () => {
  refresh()
})

// 弹出添加类别弹窗
const preAddType = () => {
  typeModel.value = {
    typeID: '',
    typeName: ''
  }
  dialogVisible.value = true;
}

// 添加类别
const addType = async () => {
  await addTypeService(typeModel.value)
  ElMessage.success("添加成功")
  dialogVisible.value = false;
  refresh()
}

// 删除类别
const deleteType = async (row) => {
  let result = await deleteTypeService(row.typeName)
  if(result.code === 0) {
    ElMessage.success("删除成功")
  } else{
    ElMessage.error(result.message?result.message:"服务异常")
  }
  refresh()
}
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>书籍类别</span>
        <el-button type="primary" @click="preAddType">添加类别</el-button>
      </div>
    </template>
    <el-table :data="bookType" style="width: 100%">
      <el-table-column label="类别编号" prop="typeID"> </el-table-column>
      <el-table-column label="类别名称" prop="typeName"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button :icon="Edit" circle plain type="primary" ></el-button>
          <el-button :icon="Delete" circle plain type="danger" @click="deleteType(row)"></el-button>
        </template>
      </el-table-column>
      <template #empty>
          <el-empty description="没有数据" />
      </template>
    </el-table>
  </el-card>
  <!-- 添加书籍类别弹窗 -->
  <el-dialog v-model="dialogVisible" title="添加书籍类别" width="30%">
    <el-form :model="typeModel" :rules="rules" label-width="100px" style="padding-right: 30px">
      <br/>
      <br/>
      <el-form-item label="类别编号" prop="categoryName">
        <el-input-number v-model="typeModel.typeID" :precision="0" :step="1" :max="1000" :min="0"/>
        <!-- <el-input v-model="typeModel.typeID" minlength="1" maxlength="10"></el-input> -->
      </el-form-item>
      <br/>
      <el-form-item label="类别名称" prop="categoryAlias">
        <el-input v-model="typeModel.typeName" minlength="1" maxlength="15"></el-input>
      </el-form-item>
    </el-form>
    <br/>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="addType"> 确认 </el-button>
      </span>
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
  }
}
</style>

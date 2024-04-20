<script setup>
import { ElMessage, ElMessageBox } from 'element-plus'
import { Edit, Delete } from '@element-plus/icons-vue'
import { ref, onMounted } from 'vue'
import { addTypeService, 
  deleteTypeService, 
  changeTypeService,
  getAllTypeService 
} from '@/api/type.js'
import { useUserInfoStore } from '@/store/userinfo.js'

// 使用用户信息存储服务
const userInfoStore = useUserInfoStore()
// 判断是否为管理员
const isManager = ref(true)
// 控制类别弹窗是否显示
const dialogVisible = ref(false)
// 控制弹窗标题
const title = ref("添加物品类别")

// 物品类别数据模型
const goodsType = ref([])
// 添加类别数据模型
const typeModel = ref({
  typeID: '',
  typeName: ''
})
// 保存旧类别的信息
const oldTypeModel = ref({
  oldTypeID: '',
  oldTypeName: ''
})

// 类别数据验证
const typeModelRules = ref({
  typeID: [
    { required: true, message: '请输入类别编号', trigger: 'blur' }
  ],
  typeName: [
    { required: true, message: '请输入类别名称', trigger: 'blur' },
    { min: 1, max: 10, message: '类别名称的长度不能超过10位', trigger: 'blur' }
  ]
})

// 刷新数据
const refresh = async () => {
  let result = await getAllTypeService()
  goodsType.value = result.data
  if(userInfoStore.info.roleID === 0) isManager.value = false;
}

// 初始化页面
onMounted(async () => {
  refresh()
})

// 弹出添加类别弹窗
const preAddType = () => {
  title.value = "添加物品类别"
  typeModel.value = {
    typeID: '',
    typeName: ''
  }
  dialogVisible.value = true;
}

// 添加/修改类别
const addType = async () => {
  if(title.value==="添加物品类别"){
    await addTypeService(typeModel.value)
    ElMessage.success("添加成功")
  }else if(title.value==="修改物品类别") {
    await changeTypeService(oldTypeModel.value, typeModel.value)
    ElMessage.success("修改成功")
  }
  dialogVisible.value = false;
  refresh()
}

// 删除类别
const deleteType = async (row) => {
  ElMessageBox.confirm("您确定要删除该类别吗?<br>( 注:该类别的物品将会失去分类! )", "温馨提示", {
      confirmButtonClass: "确定",
      cancelButtonClass: "取消",
      type: "warning",
      dangerouslyUseHTMLString: true
    }).then(async() => {
      await deleteTypeService(row.typeName)
      ElMessage.success("删除成功")
      refresh()
    })
}

// 修改类别
const changeType = async (row) => {
  title.value = "修改物品类别"

  oldTypeModel.value.oldTypeID = row.typeID
  oldTypeModel.value.oldTypeName = row.typeName

  typeModel.value.typeID = row.typeID
  typeModel.value.typeName = row.typeName
  dialogVisible.value = true;
}
</script>

<template>
  <el-card v-if="isManager" class="page-container">
    <template #header>
      <div class="header">
        <h1 class="title">物品类别</h1>
        <el-button type="primary" @click="preAddType">添加类别</el-button>
      </div>
    </template>
    <el-table :data="goodsType" style="width: 100%">
      <el-table-column label="类别编号" prop="typeID"></el-table-column>
      <el-table-column label="类别名称" prop="typeName"></el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-tooltip effect="light" placement="left" content="编辑">
            <el-button :icon="Edit" circle plain type="primary" @click="changeType(row)" style="margin: 20px auto"></el-button>
          </el-tooltip>
          <el-tooltip effect="light" placement="right" content="删除">
            <el-button :icon="Delete" circle plain type="danger" @click="deleteType(row)"></el-button>
          </el-tooltip>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
  </el-card>
  <!-- 添加/修改物品类别弹窗 -->
  <el-dialog v-model="dialogVisible" draggable style="width: 30%; border-radius: 10px; padding: 1% 3% 3% 3%;">
    <el-form style="color: black; font-size: 18px;">
      <h1>{{ title }}</h1>
    </el-form>
    <el-form :model="typeModel" :rules="typeModelRules" label-width="100px" style="padding: 20px 80px 40px 0">
      <el-form-item label="类别编号" prop="typeID">
        <el-input-number v-model="typeModel.typeID" :precision="0" :step="1" :max="1000" :min="0"/>
      </el-form-item>
      <br>
      <el-form-item label="类别名称" prop="typeName">
        <el-input v-model="typeModel.typeName" minlength="1" maxlength="10"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="addType"> 确认 </el-button>
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

.dialog-title{
  display: flex; 
  justify-content: center; 
  padding-left: 40px; 
  font-size: 20px;
}
</style>

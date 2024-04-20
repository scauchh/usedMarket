<script setup>
import { Edit, Delete } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { ref, onMounted } from 'vue'
import {
  addGoodsService,
  deleteGoodsService,
  updateGoodsService,
  getCurrentPageGoodsService
} from '@/api/goods.js'
import { getAllTypeService } from '@/api/type.js'
import { Plus } from '@element-plus/icons-vue'
import { useTokenStore } from '@/store/token'

// 使用token存储服务
const tokenStore = useTokenStore()
// 控制抽屉是否显示
const visibleDrawer = ref(false)
// 控制抽屉的标题
const drawerTitle = ref("添加物品")
// 控制预览图是否显示
const visibleDialog = ref(false)
// 控制预览图的图片
const prePicture = ref("")

// 数据模型
const goodsData = ref([])
const goodsModel = ref({})
// 物品类型数据模型
const goodsType = ref([])

//分页条数据模型
const pageNum = ref(1)
const total = ref(10)
const pageSize = ref(5)

// 物品数据验证
const goodsDataRules = ref({
  goodsName: [
    { required: true, message: '请输入物品名称', trigger: 'blur' },
    { min: 2, max: 25, message: '物品名称的长度必须为2~25位', trigger: 'blur' }
  ],
  picture: [
    { required: true, message: '图片必须上传', trigger: 'blur' },
  ],
  price: [
    { required: true, message: '请输入物品价格', trigger: 'blur' }
  ],
  type: [
    { required: true, message: '请选择物品类型', trigger: 'blur' }
  ],
  notes: [
    { min: 0, max: 100, message: '备注的长度不能超过100位', trigger: 'blur' }
  ]
})

// 刷新数据
const refresh = async () => {
  let result = await getCurrentPageGoodsService(pageNum.value, pageSize.value)
  total.value = result.data.total
  goodsData.value = result.data.items
  result = await getAllTypeService()
  goodsType.value = result.data
}

// 初始化页面
onMounted(async () => {
  refresh()
})

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  refresh()
}

//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  refresh()
}

// 打开添加物品抽屉
const openDrawer = () => {
  drawerTitle.value = "添加物品"
  visibleDrawer.value = true
  goodsModel.value = ({})
}

// 更新物品
const updateGoodsByState = async (state) => {
  if (state) {
    if (drawerTitle.value === "添加物品") {
      await addGoodsService(goodsModel.value)
      ElMessage.success("添加成功")
    }
    else if (drawerTitle.value === "编辑物品") {
      await updateGoodsService(goodsModel.value)
      ElMessage.success("修改成功")
    }
    refresh()
  }
  visibleDrawer.value = false
}

// 删除物品
const deleteGoods = async(row) =>{
  ElMessageBox.confirm("您确定要删除该物品吗?", "温馨提示", {
      confirmButtonClass: "确定",
      cancelButtonClass: "取消",
      type: "warning"
    }).then(async() => {
      await deleteGoodsService(row.goodsID)
      ElMessage.success("删除成功")
      refresh()
    })
}

// 编辑物品
const editGoods = (row) => {
  drawerTitle.value = "编辑物品"
  goodsModel.value.goodsID = row.goodsID
  goodsModel.value.goodsName = row.goodsName
  goodsModel.value.picture = row.picture
  goodsModel.value.price = row.price
  goodsModel.value.type = row.type
  goodsModel.value.notes = row.notes
  visibleDrawer.value = true
}

// 图片上传成功的回调函数
const uploadSuccess = (result) => {
  if (result.code === 0) {
    goodsModel.value.picture = result.data
  } else {
    ElMessage.error(result.message)
  }
}

// 图片上传失败的回调函数
const uploadError = (result) => {
  ElMessage.error("图片大小不能超过5MB")
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
        <h1 class="title">我的发布</h1>
        <el-button type="primary" @click="openDrawer">添加物品</el-button>
      </div>
    </template>
    <!-- 物品列表 -->
    <el-table :data="goodsData" style="width: 100%">
      <el-table-column label="物品名称" prop="goodsName"></el-table-column>
      <el-table-column label="物品图片" prop="picture">
        <template #default="{ row }">
          <el-image class="avatar" :src="row.picture" @click="showPreview(row.picture)" />
        </template>
      </el-table-column>
      <el-table-column label="价格" prop="price"> </el-table-column>
      <el-table-column label="类型" prop="type"> </el-table-column>
      <el-table-column label="更新时间" prop="datetime"> </el-table-column>
      <el-table-column label="备注" prop="notes"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-tooltip effect="light" placement="top" content="编辑">
            <el-button :icon="Edit" circle plain type="primary" @click="editGoods(row)"></el-button>
          </el-tooltip>
          <el-tooltip effect="light" placement="top" content="删除">
            <el-button :icon="Delete" circle plain type="danger" @click="deleteGoods(row)"></el-button>
          </el-tooltip>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <!-- 抽屉组件 -->
    <el-drawer v-model="visibleDrawer" :title="drawerTitle" direction="rtl" size="50%" class="title">
      <!-- 添加/编辑物品表单 -->
      <el-form :model="goodsModel" label-width="100px" :rules="goodsDataRules">
        <el-form-item label="物品名称" prop="goodsName">
          <el-input v-model="goodsModel.goodsName" placeholder="请输入标题"></el-input>
        </el-form-item>
        <el-form-item label="图片" prop="picture">
          <el-upload class="avatar-uploader" action="/api/uploadImg" :auto-upload="true" :show-file-list="false"
            :headers="{ 'Authorization': tokenStore.token }" :on-success="uploadSuccess" :on-error="uploadError">
            <img v-if="goodsModel.picture" :src="goodsModel.picture" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon">
              <Plus />
            </el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input-number v-model="goodsModel.price" :precision="2" :step="1" :max="10000" :min="1"/>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select placeholder="请选择物品类型" v-model="goodsModel.type" style="width: 200px">
            <el-option v-for="item in goodsType" :key="item.typeID" :label="item.typeName" :value="item.typeName" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="notes">
          <el-input v-model="goodsModel.notes" placeholder="请输入备注"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateGoodsByState(true)">确定</el-button>
          <el-button type="info" @click="updateGoodsByState(false)">取消</el-button>
        </el-form-item>
      </el-form>
    </el-drawer>
    <!-- 预览图 -->
    <el-dialog v-model="visibleDialog" draggable width="35%">
      <img :src="prePicture" style="width: 100%; height: auto;" />
    </el-dialog>
    <!-- 分页条 -->
    <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
      layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
      @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />
  </el-card>
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

.avatar-uploader {
  :deep() {
    .avatar {
      width: 178px;
      height: 178px;
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
      width: 178px;
      height: 178px;
      text-align: center;
    }
  }
}

.avatar {
  width: 100px;
  height: 100px;
}
</style>
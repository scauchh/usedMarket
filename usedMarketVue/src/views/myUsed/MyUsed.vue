<script setup>
import { Edit, Delete } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { ref, onMounted } from 'vue'
import {
  addBookService,
  deleteBookService,
  updateBookService,
  getCurrentPageBookService
} from '@/api/book.js'
import { getAllTypeService } from '@/api/type.js'
import { Plus } from '@element-plus/icons-vue'
import { useTokenStore } from '@/store/token'

const tokenStore = useTokenStore()

// 控制抽屉是否显示
const visibleDrawer = ref(false)
// 控制抽屉的标题
const drawerTitle = ref("添加书籍")
// 控制预览图是否显示
const visibleDialog = ref(false)
// 控制预览图的图片
const prePicture = ref("")

// 数据模型
const bookData = ref([])
const bookModel = ref({})
// 书籍类型数据模型
const bookType = ref([])

//分页条数据模型
const pageNum = ref(1)
const total = ref(10)
const pageSize = ref(3)

// 书籍数据验证
const bookDataRules = ref({
  title: [
    { required: true, message: '请输入书籍标题', trigger: 'blur' },
    { min: 3, max: 25, message: '书籍标题的长度必须为3~25位', trigger: 'blur' }
  ],
  picture: [
    { required: true, message: '图片必须上传', trigger: 'blur' },
  ],
  price: [
    { required: true, message: '请输入书籍价格', trigger: 'blur' }
  ],
  type: [
    { required: true, message: '请选择书籍类型', trigger: 'blur' }
  ],
  notes: [
    { min: 0, max: 100, message: '备注的长度不能超过100位', trigger: 'blur' }
  ]
})

// 刷新数据
const refresh = async () => {
  let result = await getCurrentPageBookService(pageNum.value, pageSize.value)
  total.value = result.data.total
  bookData.value = result.data.items
  result = await getAllTypeService()
  bookType.value = result.data
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

// 打开添加书籍抽屉
const openDrawer = () => {
  drawerTitle.value = "添加书籍"
  visibleDrawer.value = true
  bookModel.value = ({})
}

// 更新书籍
const updateBookByState = async (state) => {
  if (state) {
    if (drawerTitle.value === "添加书籍") {
      await addBookService(bookModel.value)
      ElMessage.success("添加成功")
    }
    else if (drawerTitle.value === "编辑书籍") {
      await updateBookService(bookModel.value)
      ElMessage.success("修改成功")
    }
    refresh()
  }
  visibleDrawer.value = false
}

// 删除书籍
const deleteBook = async(row) =>{
  ElMessageBox.confirm("您确定要删除该书籍吗?", "温馨提示", {
      confirmButtonClass: "确定",
      cancelButtonClass: "取消",
      type: "warning"
    }).then(async() => {
      await deleteBookService(row.bookID)
      ElMessage.success("删除成功")
      refresh()
    })
}

// 编辑书籍
const editBook = (row) => {
  drawerTitle.value = "编辑书籍"
  bookModel.value.bookID = row.bookID
  bookModel.value.title = row.title
  bookModel.value.picture = row.picture
  bookModel.value.price = row.price
  bookModel.value.type = row.type
  bookModel.value.notes = row.notes
  visibleDrawer.value = true
}

// 图片上传成功的回调函数
const uploadSuccess = (result) => {
  if (result.code === 0) {
    bookModel.value.picture = result.data
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
  console.log(picture)
}
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>我的发布</span>
        <el-button type="primary" @click="openDrawer">添加书籍</el-button>
      </div>
    </template>
    <!-- 书籍列表 -->
    <el-table :data="bookData" style="width: 100%">
      <el-table-column v-if="false" label="编号" prop="bookID"></el-table-column>
      <el-table-column label="书籍名称" prop="title"></el-table-column>
      <el-table-column label="书籍图片" prop="picture">
        <template #default="{ row }">
          <el-image style="width: 100px; height: 100px" :src="row.picture" @click="showPreview(row.picture)" />
        </template>
      </el-table-column>
      <el-table-column label="价格" prop="price"> </el-table-column>
      <el-table-column label="类型" prop="type"> </el-table-column>
      <el-table-column label="更新时间" prop="datetime"> </el-table-column>
      <el-table-column label="备注" prop="notes"></el-table-column>
      <el-table-column label="" width="100">
        <template #default="{ row }">
          <el-button :icon="Edit" circle plain type="primary" @click="editBook(row)"></el-button>
          <el-button :icon="Delete" circle plain type="danger" @click="deleteBook(row)"></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <!-- 抽屉组件 -->
    <el-drawer v-model="visibleDrawer" :title="drawerTitle" direction="rtl" size="50%">
      <!-- 添加/编辑书籍表单 -->
      <el-form :model="bookModel" label-width="100px" :rules="bookDataRules">
        <el-form-item label="书籍名称" prop="title">
          <el-input v-model="bookModel.title" placeholder="请输入标题"></el-input>
        </el-form-item>
        <el-form-item label="图片" prop="picture">
          <el-upload class="avatar-uploader" action="/api/uploadImg" :auto-upload="true" :show-file-list="false"
            :headers="{ 'Authorization': tokenStore.token }" :on-success="uploadSuccess" :on-error="uploadError">
            <img v-if="bookModel.picture" :src="bookModel.picture" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon">
              <Plus />
            </el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input-number v-model="bookModel.price" :precision="2" :step="1" :max="1000" :min="0"/>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select placeholder="请选择书籍类型" v-model="bookModel.type" style="width: 200px">
            <el-option v-for="item in bookType" :key="item.typeID" :label="item.typeName" :value="item.typeName" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="notes">
          <el-input v-model="bookModel.notes" placeholder="请输入备注"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateBookByState(true)">确定</el-button>
          <el-button type="info" @click="updateBookByState(false)">取消</el-button>
        </el-form-item>
      </el-form>
    </el-drawer>
    <!-- 预览图 -->
    <el-dialog v-model="visibleDialog">
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
</style>
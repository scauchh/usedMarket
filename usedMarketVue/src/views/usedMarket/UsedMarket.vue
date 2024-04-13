<script setup>
import { ref, onMounted } from 'vue'
import { Avatar, Promotion } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh } from '@element-plus/icons-vue'
import { getAllPageBookService } from '@/api/book.js'
import { getUserInfoByIDService } from '@/api/user.js'
import { getAllTypeService } from '@/api/type.js'
import { addTradeService } from '@/api/trade.js'
import avatar from '@/assets/default.png'

// 控制预览图是否显示
const visibleImg = ref(false)
// 控制卖家信息弹窗是否显示
const visibleSeller = ref(false)
// 控制预览图的图片
const prePicture = ref("")

// 数据信息数据模型
const bookData = ref([])
// 卖家信息数据模型
const sellerData = ref({})
// 书籍类型数据模型
const bookType = ref([])
// 搜索书籍数据模型
const searchData = ref({
  title: '',
  type: ''
})

// 分页条数据模型
const pageNum = ref(1)
const total = ref(10)
const pageSize = ref(3)

// 刷新数据
const refresh = async () => {
  let result = await getAllPageBookService(pageNum.value, pageSize.value, searchData.value)
  total.value = result.data.total
  bookData.value = result.data.items
  result = await getAllTypeService()
  bookType.value = result.data
}

// 初始化页面
onMounted(async () => {
  refresh()
})

// 展示卖家信息
const showSeller = async (row) => {
  visibleSeller.value = true
  let result = await getUserInfoByIDService(row.userID)
  sellerData.value = result.data
  if (sellerData.value.gender === '0') sellerData.value.gender = '未知'
  else if (sellerData.value.gender === '1') sellerData.value.gender = '男'
  else if (sellerData.value.gender === '2') sellerData.value.gender = '女';
}

// 发起交易
const trade = async (row) => {
  ElMessageBox.confirm("您确定要发起交易吗?", "交易提示", {
      confirmButtonClass: "确定",
      cancelButtonClass: "取消"
    }).then(async() => {
      await addTradeService(row.userID, row.bookID)
      ElMessage.success("交易发起成功")
    }) 
}

// 重置搜索条件
const reset = () => {
  searchData.value = {
    title: '',
    type: ''
  }
}

// 当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  refresh()
}

// 当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  refresh()
}

// 打开预览图
const showPreview = (picture) => {
  visibleImg.value = true
  prePicture.value = picture
}
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <!-- 搜索表单 -->
      <el-form inline>
        <el-form-item label="书籍名称：">
          <el-input v-model="searchData.title" placeholder="请输入书籍名称" />
        </el-form-item>
        <el-form-item label="书籍类型：">
          <el-select placeholder="请选择书籍类型" v-model="searchData.type" style="width: 200px">
            <el-option v-for="item in bookType" :key="item.typeID" :label="item.typeName" :value="item.typeName" />
            <el-option label="所有类型" value="" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :icon="Search" @click="refresh">搜索</el-button>
          <el-button type="default" :icon="Refresh" @click="reset">重置</el-button>
        </el-form-item>
      </el-form>
    </template>
    <!-- 书籍列表 -->
    <el-table :data="bookData" style="width: 100%">
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
      <el-table-column label="卖家信息" width="80">
        <template #default="{ row }">
          <div class="button-container">
            <el-button :icon="Avatar" circle plain type="primary" @click="showSeller(row)"></el-button>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="发起交易" width="80">
        <template #default="{ row }">
          <div class="button-container">
            <el-button :icon="Promotion" circle plain type="success" @click="trade(row)"></el-button>
          </div>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <!-- 卖家信息弹窗 -->
    <el-dialog v-model="visibleSeller" title="卖家信息" width="30%">
      <el-form :model="sellerData" label-width="100px" style="padding-right: 30px">
        <el-form-item label="用户名">
          <el-text size="large">{{ sellerData.userName }}</el-text>
        </el-form-item>
        <el-form-item label="昵称">
          <el-text size="large">{{ sellerData.nickName }}</el-text>
        </el-form-item>
        <el-form-item label="头像">
          <img v-if="sellerData.avatar" :src="sellerData.avatar" class="avatar"
            @click="showPreview(sellerData.avatar)" />
          <img v-else :src="avatar" class="avatar" @click="showPreview(avatar)" />
        </el-form-item>
        <el-form-item label="性别">
          <el-text size="large">{{ sellerData.gender }}</el-text>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-text size="large">{{ sellerData.email ? sellerData.email : '暂无' }}</el-text>
        </el-form-item>
        <el-form-item label="手机">
          <el-text size="large">{{ sellerData.phone ? sellerData.phone : '暂无' }}</el-text>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!-- 预览图 -->
    <el-dialog v-model="visibleImg" width="50%">
      <img :src="prePicture" style="width: 100%; height: auto;" class="prePicture" />
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
}

.button-container {
  display: flex;
  justify-content: center;
}

.avatar {
  width: 150px;
  height: 150px;
  display: block;
}

.prePicture {
  max-width: 100%;
  height: auto;
}
</style>
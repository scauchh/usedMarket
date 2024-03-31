<script setup>
import { Avatar } from '@element-plus/icons-vue'
import { ref, onMounted } from 'vue'
import { getAllPageBookService } from '@/api/book.js'
import { getUserInfoByIDService } from '@/api/user.js'
import avatar from '@/assets/default.png'

// 控制预览图是否显示
const visibleImg = ref(false)
// 控制卖家信息弹窗是否显示
const visibleSeller = ref(false)
// 控制预览图的图片
const prePicture = ref("")
//用户搜索时选中的上架状态
const state = ref('')

// 数据模型
const bookData = ref([])
const sellerData = ref({
  userID: '',
  userName: '',
  nickName: '',
  avatar: '',
  gender: '',
  email: '',
  phone: ''
})

//分页条数据模型
const pageNum = ref(1)
const total = ref(10)
const pageSize = ref(3)

// 刷新数据
const refresh = async () => {
  let result = await getAllPageBookService(pageNum.value, pageSize.value)
  total.value = result.data.total
  bookData.value = result.data.items
}

// 初始化页面
onMounted(async () => {
  refresh()
})

// 联系卖家
const chat = async(row) => {
  visibleSeller.value = true
  let result = await getUserInfoByIDService(row.userID)
  sellerData.value = result.data
  if(sellerData.value.gender === '0') sellerData.value.gender = '未知'
  else if(sellerData.value.gender === '1') sellerData.value.gender = '男'
  else if(sellerData.value.gender === '2') sellerData.value.gender = '女';
}

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

// 打开预览图
const showPreview = (picture) => {
  visibleImg.value = true
  prePicture.value = picture
}
</script>

<template>
  <el-card class="page-container">
    <!-- 搜索表单 -->
    <el-form inline>
      <el-form-item label="上架状态：">
        <el-select placeholder="请选择" v-model="state">
          <el-option label="已上架" value="已发布"></el-option>
          <el-option label="未上架" value="未上架"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary">搜索</el-button>
        <el-button>重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 书籍列表 -->
    <el-table :data="bookData" style="width: 100%">
      <el-table-column v-if="false" prop="bookID"></el-table-column>
      <el-table-column v-if="false" prop="userID"></el-table-column>
      <el-table-column label="书籍名称" prop="title"></el-table-column>
      <el-table-column label="书籍图片" prop="picture">
        <template #default="{ row }">
          <el-image style="width: 100px; height: 100px" :src="row.picture" @click="showPreview(row.picture)" />
        </template>
      </el-table-column>
      <el-table-column label="价格" prop="price"> </el-table-column>
      <el-table-column label="备注" prop="notes"></el-table-column>
      <el-table-column label="卖家信息" width="80">
        <template #default="{ row }">
          <div class="button-container">
            <el-button :icon="Avatar" circle plain type="primary" @click="chat(row)"></el-button>
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
        <el-form-item label="昵称">
          <td class="text">{{sellerData.nickName}}</td>
        </el-form-item>
        <el-form-item label="头像">
          <img v-if="sellerData.avatar" :src="sellerData.avatar" class="avatar" @click="showPreview(sellerData.avatar)"/>
          <img v-else :src="avatar" class="avatar" @click="showPreview(avatar)"/>
        </el-form-item>
        <el-form-item label="性别">
          <td class="text">{{sellerData.gender}}</td>
        </el-form-item>
        <el-form-item label="邮箱">
          <td class="text">{{sellerData.email?sellerData.email:'暂无'}}</td>
        </el-form-item>
        <el-form-item label="手机">
          <td class="text">{{sellerData.phone?sellerData.phone:'暂无'}}</td>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!-- 预览图 -->
    <el-dialog v-model="visibleImg" width="50%">
      <img :src="prePicture" style="width: 100%; height: auto;" class="prePicture"/>
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
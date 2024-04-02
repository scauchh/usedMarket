<script setup>
import { ElMessage, ElMessageBox } from 'element-plus'
import { Edit, Delete } from '@element-plus/icons-vue'
import { ref, onMounted } from 'vue'
import { getAllUserInfoService } from '@/api/user.js'
import { useUserInfoStore } from '@/store/userinfo.js'
import avatar from '@/assets/default.png'

// 使用用户信息存储服务
const userInfoStore = useUserInfoStore()
// 判断是否为管理员
const isManager = ref(true)
// 控制预览图是否显示
const visibleImg = ref(false)
// 控制预览图的图片
const prePicture = ref("")

// 用户信息数据模型
const userInfo = ref([])
//分页条数据模型
const pageNum = ref(1)
const total = ref(10)
const pageSize = ref(3)

// 刷新数据
const refresh = async () => {
  let result = await getAllUserInfoService(pageNum.value, pageSize.value)
  userInfo.value = result.data.items
  total.value = result.data.total
  if(userInfoStore.info.roleID === 0) isManager.value = false;
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

// 打开预览图
const showPreview = (picture) => {
  visibleImg.value = true
  prePicture.value = picture
}
</script>

<template>
  <el-card v-if="isManager" class="page-container">
    <template #header>
      <div class="header">
        <span>用户列表</span>
      </div>
    </template>
    <el-table :data="userInfo" style="width: 100%">
      <el-table-column label="用户名" prop="userName"></el-table-column>
      <el-table-column label="昵称" prop="nickName"></el-table-column>
      <el-table-column label="头像" prop="avatar">
        <template #default="{ row }">
          <el-image v-if="row.avatar" style="width: 100px; height: 100px" :src="row.avatar" @click="showPreview(row.avatar)" />
          <el-image v-else style="width: 100px; height: 100px" :src="avatar" @click="showPreview(avatar)" />
        </template>
      </el-table-column>
      <el-table-column label="性别" prop="gender">
        <template #default="{ row }">
          <el-text v-if="row.gender==0">未知</el-text>
          <el-text v-else-if="row.gender==1">男</el-text>
          <el-text v-else-if="row.gender==2">女</el-text>
        </template>
      </el-table-column>
      <el-table-column label="邮箱" prop="email"></el-table-column>
      <el-table-column label="手机号" prop="phone"></el-table-column>
      <el-table-column label="角色" prop="roleID"> 
        <template #default="{ row }">
          <el-text v-if="row.roleID==0">普通用户</el-text>
          <el-text v-else-if="row.roleID==1">管理员</el-text>
        </template>
      </el-table-column>
      <el-table-column label="注册时间" prop="registerTime"></el-table-column>
      <el-table-column label="上次登录" prop="loginTime"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button :icon="Edit" circle plain type="primary" ></el-button>
          <el-button :icon="Delete" circle plain type="danger" ></el-button>
        </template>
      </el-table-column>
      <template #empty>
          <el-empty description="没有数据" />
      </template>
    </el-table>
    <!-- 分页条 -->
    <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
        layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
        @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />
    <!-- 预览图 -->
    <el-dialog v-model="visibleImg" width="50%">
      <img :src="prePicture" style="width: 100%; height: auto;" class="prePicture" />
    </el-dialog>
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
</style>

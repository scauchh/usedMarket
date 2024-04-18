<script setup>
import { ElMessage, ElMessageBox } from 'element-plus'
import { Edit, Delete, Search, Refresh, RefreshLeft } from '@element-plus/icons-vue'
import { ref, onMounted } from 'vue'
import { 
  deleteUserService, 
  updateUserRoleService, 
  getAllUserInfoService, 
  resetPasswordByManageService } from '@/api/user.js'
import { useUserInfoStore } from '@/store/userinfo.js'
import avatar from '@/assets/default.png'

// 使用用户信息存储服务
const userInfoStore = useUserInfoStore()
// 判断是否为管理员
const isManager = ref(true)
// 控制弹窗是否显示
const dialogVisible = ref(false)
// 控制预览图是否显示
const visibleImg = ref(false)
// 控制预览图的图片
const prePicture = ref("")

// 用户信息数据模型
const userInfo = ref([])
// 修改用户数据模型
const changeModel = ref({
  userName: '',
  nickName: '',
  userRole: ''
})
// 搜索用户数据模型
const searchData = ref({
  userName: '',
  userRole: ''
})
// 分页条数据模型
const pageNum = ref(1)
const total = ref(10)
const pageSize = ref(5)

// 刷新数据
const refresh = async () => {
  let result = await getAllUserInfoService(pageNum.value, pageSize.value, searchData.value)
  userInfo.value = result.data.items
  total.value = result.data.total
  if(userInfoStore.info.roleID === 0) isManager.value = false;
}

// 初始化页面
onMounted(async () => {
  refresh()
})

// 打开修改用户权限的界面
const preChange = (row) => {
  changeModel.value.userName = row.userName
  changeModel.value.nickName = row.nickName
  changeModel.value.userRole = row.roleID==0?"普通用户":"管理员"
  dialogVisible.value = true
}

// 修改用户权限
const change = async () => {
  await updateUserRoleService(changeModel.value)
  dialogVisible.value = false
  refresh()
  ElMessage.success("修改成功")
}

// 重置用户密码
const resetPassword = (row) => {
  ElMessageBox.confirm("您确定要重置该用户的密码吗?密码将被重置为123456！", "温馨提示", {
      confirmButtonClass: "确定",
      cancelButtonClass: "取消",
      type: "warning"
    }).then(async() => {
      await resetPasswordByManageService(row.userName)
      ElMessage.success("重置成功")
      refresh()
    })
}

// 删除用户
const deleteUser = (row) => {
  ElMessageBox.confirm("您确定要删除该用户吗?用户的所有信息会被一并删除！", "温馨提示", {
      confirmButtonClass: "确定",
      cancelButtonClass: "取消",
      type: "warning"
    }).then(async() => {
      await deleteUserService(row.userID)
      ElMessage.success("删除成功")
      refresh()
    })
}

// 重置搜索条件
const reset = () => {
  searchData.value = {
    userName: '',
    userRole: ''
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
  <el-card v-if="isManager" class="page-container">
    <template #header>
      <!-- 搜索表单 -->
      <el-form inline class="header">
        <el-form-item label="用户名：" class="form-row">
          <el-input v-model="searchData.userName" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="用户角色：" class="form-row">
          <el-select placeholder="请选择用户角色" v-model="searchData.userRole" @change="refresh" style="width: 200px">
            <el-option label="普通用户" value="普通用户" />
            <el-option label="管理员" value="管理员" />
            <el-option label="所有用户" value="" />
          </el-select>
        </el-form-item>
        <el-form-item class="form-row">
          <el-button type="primary" :icon="Search" @click="refresh">搜索</el-button>
          <el-button type="default" :icon="Refresh" @click="reset">重置</el-button>
        </el-form-item>
      </el-form>
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
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-tooltip effect="light" placement="top" content="编辑">
            <el-button :icon="Edit" circle plain type="primary" @click="preChange(row)"></el-button>
          </el-tooltip>
          <el-tooltip effect="light" placement="top" content="重置密码">
            <el-button :icon="RefreshLeft" circle plain type="primary" @click="resetPassword(row)"></el-button>
          </el-tooltip>
          <el-tooltip effect="light" placement="top" content="删除">
            <el-button :icon="Delete" circle plain type="danger" @click="deleteUser(row)"></el-button>
          </el-tooltip>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <!-- 修改用户权限弹窗 -->
    <el-dialog v-model="dialogVisible" title="修改用户权限" width="30%">
      <el-form label-width="100px" style="padding-right: 30px">
        <br/>
        <el-form-item label="用户名" >{{ changeModel.userName }}</el-form-item>
        <el-form-item label="昵称" >{{ changeModel.nickName }}</el-form-item>
        <el-form-item label="角色" >
          <el-select v-model="changeModel.userRole" style="width: 200px">
            <el-option label="普通用户" value="普通用户" />
            <el-option label="管理员" value="管理员" />
          </el-select>
        </el-form-item>
      </el-form>
      <br/>
      <template #footer>
        <el-button @click="dialogVisible = false" >取消</el-button>
        <el-button type="primary" @click="change"> 确认 </el-button>
      </template>
    </el-dialog>
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
  }

  .form-row {
    margin: auto 10px;
  }
}
</style>

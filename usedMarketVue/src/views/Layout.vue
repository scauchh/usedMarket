<script setup>
import {
  Management,
  UserFilled,
  GoodsFilled,
  User,
  EditPen,
  SwitchButton,
  CaretBottom
} from '@element-plus/icons-vue'
import { ref, onMounted } from 'vue'
import avatar from '@/assets/default.png'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useUserInfoStore } from '@/store/userinfo.js'
import { useTokenStore } from '@/store/token.js'
import router from '@/router/index.js'
import { removeImgService } from '@/api/utils.js'

// 使用用户信息存储服务
const userInfoStore = useUserInfoStore()
// 使用token存储服务
const tokenStore = useTokenStore()
// 控制预览图是否显示
const visibleDialog = ref(false)

// 用户数据模型
const userData = ref({})

const prePicture = ref()

// 刷新数据
const refresh = ()=>{
  userData.value = userInfoStore.info
}

// 初始化界面
onMounted(() => {
  refresh()
})

// 退出登录
const dropdownEvent = (command) => {
  if (command === "logout") {
    ElMessageBox.confirm("您确定要退出登录吗?", "温馨提示", {
      confirmButtonClass: "确定",
      cancelButtonClass: "取消",
      type: "warning"
    }).then(() => {
      userInfoStore.removeInfo()
      tokenStore.removeToken()
      ElMessage.success("退出登录成功")
      removeImgService();
      router.push('/login')
    })
  } else {
    router.push('/user/' + command)
  }
}

// 打开预览图
const showPreview = (picture) => {
  visibleDialog.value = true
  prePicture.value = picture
  console.log(picture)
}
</script>

<template>
  <el-container class="layout-container">
    <!-- 左侧菜单 -->
    <el-aside width="200px">
      <div class="el-aside__logo"></div>
      <el-menu active-text-color="#99ccff" background-color="#00354d" text-color="#fff" router>
        <el-menu-item index="/usedMarket">
          <el-icon>
            <GoodsFilled />
          </el-icon>
          <span>二手闲置</span>
        </el-menu-item>
        <el-menu-item index="/myUsed">
          <el-icon>
            <Management />
          </el-icon>
          <span>我的物品</span>
        </el-menu-item>
        <el-sub-menu index="">
          <template #title>
            <el-icon>
              <UserFilled />
            </el-icon>
            <span>个人中心</span>
          </template>
          <el-menu-item index="/user/info">
            <el-icon>
              <User />
            </el-icon>
            <span>基本资料</span>
          </el-menu-item>
          <el-menu-item index="/user/resetPassword">
            <el-icon>
              <EditPen />
            </el-icon>
            <span>重置密码</span>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <!-- 右侧主区域 -->
    <el-container>
      <!-- 头部区域 -->
      <el-header>
        <div>当前用户：<strong>{{ userData.nickName }}</strong></div>
        <el-dropdown placement="bottom-end" @command="dropdownEvent">
          <span class="el-dropdown__box">
            <el-avatar :src="userData.avatar ? userData.avatar : avatar" @click="showPreview(userData.avatar ? userData.avatar : avatar)"/>
            <el-icon>
              <CaretBottom />
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="info" :icon="User">基本资料</el-dropdown-item>
              <el-dropdown-item command="resetPassword" :icon="EditPen">重置密码</el-dropdown-item>
              <el-dropdown-item command="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <!-- 中间区域 -->
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
  <!-- 预览图 -->
  <el-dialog v-model="visibleDialog">
      <img :src="prePicture" style="width: 100%; height: auto;" />
    </el-dialog>
</template>

<style lang="scss" scoped>
.layout-container {
  height: 100vh;

  .el-aside {
    background-color: #00354d;

    &__logo {
      height: 120px;
      background: url('@/assets/logo.png') no-repeat center / 120px auto;
    }

    .el-menu {
      border-right: none;
    }
  }

  .el-header {
    background-color: rgb(236, 245, 255);
    display: flex;
    align-items: center;
    justify-content: space-between;

    .el-dropdown__box {
      display: flex;
      align-items: center;

      .el-icon {
        color: #999;
        margin-left: 10px;
      }

      &:active,
      &:focus {
        outline: none;
      }
    }
  }

  .el-container {
    background-color: rgb(236, 245, 255, 0.7);
    border-radius: 10px;
  }
}
</style>
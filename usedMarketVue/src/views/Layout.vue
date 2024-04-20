<script setup>
import {
  Management,
  UserFilled,
  Shop,
  List,
  User,
  Notebook,
  SwitchButton,
  Document,
  Tickets,
  HomeFilled,
  CoffeeCup,
  HotWater,
  Memo,
  RefreshRight,
  Files
} from '@element-plus/icons-vue'
import { ref, onMounted } from 'vue'
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
// 判断是否是管理员
const isManager = ref(true)

// 用户数据模型
const userData = ref({})
// 预览图的url
const prePicture = ref()

// 刷新数据
const refresh = ()=>{
  userData.value = userInfoStore.info
  if(userInfoStore.info.roleID === 0) isManager.value = false;
}

// 初始化界面
onMounted(() => {
  refresh()
})

// 清除图片缓存
const removeImg = () => {
  ElMessageBox.confirm("您确定要清除图片缓存吗?<br>( 注:该操作将会删除所有未被使用的图片 )", "温馨提示", {
      confirmButtonClass: "确定",
      cancelButtonClass: "取消",
      type: "warning",
      dangerouslyUseHTMLString: true
    }).then(async () => {
      await removeImgService()
      ElMessage.success("清除缓存成功")
    })
}

// 退出登录
const logout = () => {
  ElMessageBox.confirm("您确定要退出登录吗?", "温馨提示", {
      confirmButtonClass: "确定",
      cancelButtonClass: "取消",
      type: "warning"
    }).then(() => {
      userInfoStore.removeInfo()
      tokenStore.removeToken()
      router.push('/login')
      ElMessage.success("退出登录成功")
    })
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
            <Shop />
          </el-icon>
          <span>交易市场</span>
        </el-menu-item>
        <el-menu-item index="/myUsed">
          <el-icon>
            <List />
          </el-icon>
          <span>我的物品</span>
        </el-menu-item>
        <el-sub-menu index="/trade">
          <template #title>
            <el-icon>
              <HomeFilled />
            </el-icon>
            <span>交易信息</span>
          </template>
          <el-menu-item index="/trade/TradeFromMe">
            <el-icon>
              <CoffeeCup />
            </el-icon>
            <span>我的发起</span>
          </el-menu-item>
          <el-menu-item index="/trade/TradeToMe">
            <el-icon>
              <HotWater />
            </el-icon>
            <span>我的接收</span>
          </el-menu-item>
        </el-sub-menu>
        <el-sub-menu v-if="isManager" index="/manage">
          <template #title>
            <el-icon>
              <Management />
            </el-icon>
            <span>管理中心</span>
          </template>
          <el-menu-item index="/manage/category">
            <el-icon>
              <Document />
            </el-icon>
            <span>类别管理</span>
          </el-menu-item>
          <el-menu-item index="/manage/UserManage">
            <el-icon>
              <Tickets />
            </el-icon>
            <span>用户管理</span>
          </el-menu-item>
          <el-menu-item index="/manage/Question">
            <el-icon>
              <Memo />
            </el-icon>
            <span>密保问题</span>
          </el-menu-item>
          <el-menu-item index="/manage/Trade">
            <el-icon>
              <Files />
            </el-icon>
            <span>历史交易</span>
          </el-menu-item>
          <el-menu-item @click="removeImg">
            <el-icon>
              <RefreshRight />
            </el-icon>
            <span>清除图片缓存</span>
          </el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="/user">
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
          <el-menu-item index="/user/password">
            <el-icon>
              <Notebook />
            </el-icon>
            <span>密码中心</span>
          </el-menu-item>
          <el-menu-item @click="logout">
            <el-icon>
              <SwitchButton />
            </el-icon>
            <span>退出登录</span>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <!-- 右侧主区域 -->
    <el-container>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
  <!-- 预览图 -->
  <el-dialog v-model="visibleDialog" draggable width="35%">
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

  .el-container {
    background-color: rgb(236, 245, 255, 0.7);
    border-radius: 10px;
  }
}
</style>
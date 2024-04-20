<script setup>
import { ElMessage, ElMessageBox } from 'element-plus'
import { Avatar } from '@element-plus/icons-vue'
import { Search, Refresh } from '@element-plus/icons-vue'
import { ref, onMounted } from 'vue'
import { getUserInfoByNameService } from '@/api/user.js'
import { getGoodsByIDService } from '@/api/goods.js'
import { updateTrade, getTradeToMeService } from '@/api/trade.js'
import avatar from '@/assets/default.png'

// 控制用户信息弹窗是否显示
const visibleUser = ref(false)
// 控制物品信息弹窗是否显示
const visibleGoods = ref(false)
// 控制预览图是否显示
const visibleImg = ref(false)
// 控制预览图的图片
const prePicture = ref("")

// 用户信息数据模型
const tradeInfo = ref([])
// 用户信息数据模型
const userData = ref({})
// 信息数据模型
const goodsData = ref({})
// 搜索交易数据模型
const searchData = ref({
  state: '',
})
// 分页条数据模型
const pageNum = ref(1)
const total = ref(10)
const pageSize = ref(10)

// 刷新数据
const refresh = async () => {
  let result = await getTradeToMeService(pageNum.value, pageSize.value, searchData.value)
  tradeInfo.value = result.data.items
  total.value = result.data.total
}

// 初始化页面
onMounted(async () => {
  refresh()
})

// 展示用户信息
const showUserInfo = async (row) => {
  visibleUser.value = true
  let result = await getUserInfoByNameService(row.buyerName)
  userData.value = result.data
  if (userData.value.gender === '0') userData.value.gender = '不愿透露'
  else if (userData.value.gender === '1') userData.value.gender = '男'
  else if (userData.value.gender === '2') userData.value.gender = '女';
}

// 展示物品信息
const showGoodsInfo = async (row) => {
  let result = await getGoodsByIDService(row.goodsID)
  goodsData.value = result.data
  visibleGoods.value = true
}

// 重置搜索条件
const reset = () => {
  searchData.value = {
    state: '',
  }
}

// 接受交易
const accept = async (row) => {
  ElMessageBox.confirm("您确定要接受该交易吗?", "交易提示", {
      confirmButtonClass: "确定",
      cancelButtonClass: "取消",
    }).then(async() => {
      await updateTrade(row.tradeID, 2, '')
      ElMessage.success("接受交易成功")
      refresh()
    })
}

// 拒绝交易
const refuse = async (row) => {
  ElMessageBox.confirm("您确定要拒绝该交易吗?", "交易提示", {
      confirmButtonClass: "确定",
      cancelButtonClass: "取消",
    }).then(async() => {
      await updateTrade(row.tradeID, 4, '卖家拒绝交易')
      ElMessage.success("拒绝交易成功")
      refresh()
    })
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
      <el-form inline class="header">
        <el-form-item label="交易状态：" class="form-row">
          <el-select placeholder="请选择交易状态" v-model="searchData.state" @change="refresh" style="width: 200px">
            <el-option label="发起中" value="1" />
            <el-option label="进行中" value="2" />
            <el-option label="交易成功" value="3" />
            <el-option label="交易失败" value="4" />
            <el-option label="所有交易" value="" />
          </el-select>
        </el-form-item>
        <el-form-item class="form-row">
          <el-button type="primary" :icon="Search" @click="refresh">搜索</el-button>
          <el-button type="default" :icon="Refresh" @click="reset">重置</el-button>
        </el-form-item>
      </el-form>
    </template>
    <el-table :data="tradeInfo" style="width: 100%">
      <el-table-column label="买家用户名" prop="buyerName"></el-table-column>
      <el-table-column label="买家信息">
        <template #default="{ row }">
          <el-button :icon="Avatar" circle plain type="primary" @click="showUserInfo(row)"></el-button>
        </template>
      </el-table-column>
      <el-table-column label="物品名称" prop="goodsName">
        <template #default="{ row }">
          <el-button type="primary" link @click="showGoodsInfo(row)">{{ row.goodsName }}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="物品图片">
        <template #default="{ row }">
          <el-image class="avatar" :src="row.picture" @click="showPreview(row.picture)" />
        </template>
      </el-table-column>
      <el-table-column label="状态" prop="state">
        <template #default="{ row }">
          <el-text v-if="row.state==1">发起中</el-text>
          <el-text v-else-if="row.state==2">进行中</el-text>
          <el-text v-else-if="row.state==3">交易成功</el-text>
          <el-text v-else-if="row.state==4">交易失败</el-text>
        </template>
      </el-table-column>
      <el-table-column label="" width="300">
        <template #default="{ row }">
          <el-text v-if="row.state==1">
            <el-button type="success" text bg @click="accept(row)">接受交易</el-button>
            <el-divider direction="vertical" />
            <el-button type="danger" text bg @click="refuse(row)">拒绝交易</el-button>
          </el-text>
          <el-text v-if="row.state==2">
            <el-text>请及时完成交易</el-text>
            <el-divider direction="vertical" />
            <el-button type="danger" text bg @click="refuse(row)">拒绝交易</el-button>
          </el-text>
          <el-text v-if="row.state==3">交易成功，您无需操作</el-text>
          <el-text v-if="row.state==4">{{ row.notes }}</el-text>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <!-- 买家信息弹窗 -->
    <el-dialog v-model="visibleUser" draggable style="width: 30%; border-radius: 10px; padding: 0 0 2% 2%;">
      <el-form :model="userData" label-width="100px" style="padding-right: 30px">
        <h1 class="title">买家信息</h1>
        <el-form-item label="用户名">
          <el-text size="large">{{ userData.userName }}</el-text>
        </el-form-item>
        <el-form-item label="昵称">
          <el-text size="large">{{ userData.nickName }}</el-text>
        </el-form-item>
        <el-form-item label="头像">
          <img v-if="userData.avatar" :src="userData.avatar" class="avatar" @click="showPreview(userData.avatar)" />
          <img v-else :src="avatar" class="avatar" @click="showPreview(avatar)" />
        </el-form-item>
        <el-form-item label="性别">
          <el-text size="large">{{ userData.gender }}</el-text>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-text size="large">{{ userData.email ? userData.email : '暂无' }}</el-text>
        </el-form-item>
        <el-form-item label="手机">
          <el-text size="large">{{ userData.phone ? userData.phone : '暂无' }}</el-text>
        </el-form-item>
        <el-form-item label="微信号">
          <el-text size="large">{{ userData.wechat ? userData.wechat : '暂无' }}</el-text>
        </el-form-item>
        <el-form-item label="地址">
          <el-text size="large">{{ userData.address ? userData.address : '暂无' }}</el-text>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!-- 物品信息弹窗 -->
    <el-dialog v-model="visibleGoods" draggable style="width: 30%; border-radius: 10px; padding: 0 0 2% 2%;">
      <el-form :model="goodsData" label-width="100px" style="padding-right: 30px">
        <h1 class="title">物品信息</h1>
        <el-form-item label="物品名称">
          <el-text size="large">{{ goodsData.goodsName }}</el-text>
        </el-form-item>
        <el-form-item label="物品图片">
          <img :src="goodsData.picture" class="avatar" @click="showPreview(goodsData.picture)" />
        </el-form-item>
        <el-form-item label="价格">
          <el-text size="large">{{ goodsData.price }}</el-text>
        </el-form-item>
        <el-form-item label="类型">
          <el-text size="large">{{ goodsData.type }}</el-text>
        </el-form-item>
        <el-form-item label="更新时间">
          <el-text size="large">{{ goodsData.datetime }}</el-text>
        </el-form-item>
        <el-form-item label="备注">
          <el-text size="large">{{ goodsData.notes }}</el-text>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!-- 分页条 -->
    <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[5, 10, 15, 20]"
      layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
      @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />
    <!-- 预览图 -->
    <el-dialog v-model="visibleImg" draggable width="35%">
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

  .title {
    display: flex; 
    justify-content: center; 
    font-size: 20px;
  }
}

.avatar {
  width: 100px;
  height: 100px;
}
</style>

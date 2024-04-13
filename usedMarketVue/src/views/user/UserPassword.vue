<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useTokenStore } from '@/store/token.js'
import { useUserInfoStore } from '@/store/userinfo.js'
import { updateUserInfoService, getUserInfoService } from '@/api/user.js'
import avatar from '@/assets/default.png'

</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>密码中心</span>
      </div>
    </template>
    <el-row>
      <el-col :span="12">
        <el-form label-width="100px" size="large">
          <el-form-item label="用户名" prop="userName">
            <el-input ></el-input>
          </el-form-item>
          <el-form-item label="昵称" prop="nickName">
            <el-input></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary">确认修改</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
</template>

<style lang="scss" scoped>

</style>
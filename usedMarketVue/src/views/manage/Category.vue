<script setup>
import { Edit, Delete } from '@element-plus/icons-vue'
import { ref, onMounted } from 'vue'
import { getAllTypeService } from '@/api/type.js'

// 书籍类型数据模型
const bookType = ref([])

// 刷新数据
const refresh = async () => {
  let result = await getAllTypeService()
  bookType.value = result.data
}

// 初始化页面
onMounted(async () => {
  refresh()
})
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>书籍类别</span>
        <el-button type="primary">添加类别</el-button>
      </div>
    </template>
    <el-table :data="bookType" style="width: 100%">
      <el-table-column label="类别编号" prop="typeID"> </el-table-column>
      <el-table-column label="类别名称" prop="typeName"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button :icon="Edit" circle plain type="primary" ></el-button>
          <el-button :icon="Delete" circle plain type="danger"></el-button>
        </template>
      </el-table-column>
      <template #empty>
          <el-empty description="没有数据" />
      </template>
    </el-table>
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

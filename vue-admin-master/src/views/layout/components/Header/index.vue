<template>
  <el-card class="header">
    <el-row justify="space-between">
      <el-col :span="16">
        <breadCrumbs />
      </el-col>
      <el-col :span="8">
        <setting />
      </el-col>
    </el-row>
  </el-card>
</template>

<script setup lang="ts"></script>

<style scoped lang="scss">
.el-card {
  border-left: 0;
}
.header {
  width: 100%;
  height: 100%;
  border-radius: 0;
  :deep(.el-card__body) {
    padding: 0 !important;
  }
  .el-row {
    width: 100%;
    height: $layout-header_height;
  }
}
</style>

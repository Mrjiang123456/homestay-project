<template>
  <el-button size="small" type="success" @click="open()">
    点击打开数据大屏
  </el-button>
</template>
<script setup lang="ts">
const open = () => {
  window.open("http://192.168.85.198:8100/#/de-link/YD6RsYqY")
}
</script>

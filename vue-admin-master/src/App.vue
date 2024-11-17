<template>
  <!-- 中文 -->
  <el-config-provider :locale="zhCn">
    <router-view></router-view>
  </el-config-provider>
</template>
<script lang="ts" setup>
import { publicFun } from "@/config/function"
import { ElConfigProvider } from "element-plus"
import zhCn from "element-plus/es/locale/lang/zh-cn"
import { onMounted, ref } from "vue"
import { GETDARK } from "./utils/local"
publicFun()
onMounted(() => {
  if (GETDARK()) {
    document.documentElement.className = "dark"
  } else {
    document.documentElement.className = ""
  }
})
</script>

<style lang="less">
@import "@/assets/font/font.css";

</style>

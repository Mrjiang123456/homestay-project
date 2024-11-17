<template>
  <template v-for="routes in menuRouteList as any" :key="routes.path">
    <!-- 所有一级路由 -->
    <el-menu-item
      :index="routes.path"
      v-if="routes.meta.isShow && routes.children.length === 0"
    >
      <svg-icon :icon="routes.meta.icon"></svg-icon>
      <template #title>
        <span style="font-size: 16px">{{ routes.meta.title }}</span>
      </template>
    </el-menu-item>
    <!-- 判断是否有子路由 -->
    <el-sub-menu
      :index="routes.path"
      v-if="routes.meta.isShow && routes.children.length > 0"
    >
      <template #title>
        <svg-icon :icon="routes.meta.icon"></svg-icon>
        <span style="font-size: 16px">{{ routes.meta.title }}</span>
      </template>
      <!-- 递归循环 -->
      <MenuItem :menuRouteList="routes.children" />
    </el-sub-menu>
  </template>
</template>

<script setup lang="ts">
defineProps<{
  menuRouteList: any
}>()
</script>

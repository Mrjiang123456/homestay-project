<template>
  <div class="layout">
    <div
      class="layout-menu"
      :class="{
        'layout-min-menu': flag,
        bgMenuClassDark: dark,
      }"
    >
      <!-- 标题与logo -->
      <MenuLogo />
      <el-menu
        router
        :default-active="$route.path"
        :collapse="flag"
        :background-color="
          dark
            ? variables['menu-background-dark']
            : variables['menu-background']
        "
        text-color="#fff"
        :active-text-color="variables['menu-active-text']"
      >
        <MenuItem :menuRouteList="toRaw(userStore.routerMenuList)" />
      </el-menu>
    </div>
    <!-- 主体 -->
    <view
      class="layout-main"
      :class="{
        'layout-max-main': flag,
      }"
    >
      <!-- 头部 -->
      <div class="layout-header">
        <Header />
      </div>
      <!-- 导航栏 -->
      <TagViews />
      <!-- 主体部分 -->
      <div
        class="main-content"
        :style="{
          backgroundColor: dark ? '#1D1E1F' : '#fff',
          transition: 'all 0.2s',
        }"
      >
        <router-view v-slot="{ Component, route }" v-if="isflag">
          <transition
            :enter-active-class="`animate__animated ${defaultSettings.routerAnimateInType} animate__faster`"
            mode="out-in"
          >
            <keep-alive>
              <component :is="Component" :key="route.path" v-if="isflag" />
            </keep-alive>
          </transition>
        </router-view>
      </div>
    </view>
  </div>
</template>

<script setup lang="ts">
import variables from "@/styles/variables.module.scss"
import { defaultSettings } from "@/config/setting"
import { useSettingStore } from "@/stores/modules/setting"
import { useUserStore } from "@/stores/modules/user"
import { computed, nextTick, ref, watch, toRaw } from "vue"

import { storeToRefs } from "pinia"
const settingStore = useSettingStore()
const userStore = useUserStore()
const { flag, dark, page_setting } = storeToRefs(settingStore)
let isflag = ref(true)
// 刷新
watch(
  () => settingStore.flush,
  () => {
    //让组件销毁
    isflag.value = false
    nextTick(() => {
      isflag.value = true
    })
  },
)
</script>

<style scoped lang="scss">
@import "./index.scss";
</style>

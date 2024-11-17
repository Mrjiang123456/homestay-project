<template>
  <div
    v-show="settingStore.page_setting.isShow_Tagview"
    class="tagView"
    :style="{
      backgroundColor: isDark ? '#1D1E1F' : 'white',
      transition: 'all 0.2s',
    }"
  >
    <el-scrollbar>
      <div class="tags animate__animated animate__fadeInDown animate__faster">
        <template v-for="(item, i) in tagViewArr" :key="item.title">
          <el-tag
            :closable="tagViewArr.length !== 1 && i !== 0"
            @close="closeTag(i, item.path)"
            @click="changeTag(item.path)"
            :class="{ tagItem: true, activeTag: route.path === item.path }"
          >
            <div class="tagContent">
              <div class="dot" v-show="route.path === item.path"></div>
              <text>{{ item.title }}</text>
            </div>
          </el-tag>
        </template>
      </div>
    </el-scrollbar>
  </div>
</template>

<script setup lang="ts">
import { nextTick, ref, watch, watchEffect } from "vue"
import { useRoute, useRouter } from "vue-router"
import { useSettingStore } from "@/stores/modules/setting"
import { computed } from "vue"
const settingStore = useSettingStore()
const router = useRouter()
const route = useRoute()
// 黑暗模式
const isDark = computed(() => {
  let dark = false
  if (settingStore.dark) {
    dark = true
  } else {
    dark = false
  }
  return dark
})
interface routeObj {
  path: string
  title: string
}
const tagViewArr = ref(<routeObj[]>[
  {
    path: "/home",
    title: "首页",
  },
])

watch(
  () => route.path,
  (newVal) => {
    let rouiteObj = <routeObj>{
      path: route.path,
      title: route.meta.title as string,
    }

    let isInclude = tagViewArr.value.find((item: routeObj) => {
      return item.path === rouiteObj.path
    })
    if (isInclude) return
    tagViewArr.value.push(rouiteObj)
  },
  { immediate: true },
)

// 切换tag
const changeTag = (path: string) => {
  router.push({ path })
}
// 关闭tag
const closeTag = (i: number, path: string) => {
  if (path === route.path) {
    tagViewArr.value.splice(i, 1)
    router.push(tagViewArr.value[tagViewArr.value.length - 1].path)
  } else {
    tagViewArr.value.splice(i, 1)
  }
}
</script>

<style scoped lang="scss">
.tagView {
  border-bottom: 1px var(--el-border-color-dark) solid;
  .tags {
    display: flex;
  }
  .tagItem {
    flex-shrink: 0;
    display: flex;
    margin: 5px;
    align-items: center;
    justify-content: space-between;
    padding: 5px 10px 5px 10px;
    border: 1px var(--el-color-primary) solid;
    color: var(--el-color-primary);
    font-size: 10px;
    border-radius: 3px;
    .tagContent {
      display: flex;
      align-items: center;
      justify-content: space-around;
      .dot {
        width: 6px;
        height: 6px;
        background-color: #fff;
        border-radius: 50%;
        margin-right: 5px;
      }
    }

    &:hover {
      cursor: pointer;
    }
    &.activeTag {
      flex-shrink: 0;
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 5px 10px 5px 10px;
      font-size: 10px;
      margin: 5px;
      text-align: center;
      color: #fff !important;
      background: var(--el-color-primary);
      :deep(.el-tag__close) {
        color: #fff;
      }
      :deep(.el-tag__close):hover {
        background-color: #fff;
        color: var(--el-color-primary);
      }
    }
  }
}
</style>

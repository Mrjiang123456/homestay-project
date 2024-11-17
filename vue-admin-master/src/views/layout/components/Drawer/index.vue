<template>
  <!-- 设置 -->
  <el-drawer :show-close="false" size="300">
    <template #header>
      <div
        :class="{ blackTitle: settingStore.dark, title: !settingStore.dark }"
      >
        项目配置
      </div>
    </template>
    <div class="config">
      <!-- 主题设置 -->
      <div class="topic">
        <div :class="{ blackTip: settingStore.dark, tip: !settingStore.dark }">
          <text>主题设置</text>
        </div>
        <!-- 切换主题 -->
        <div class="changeTopic">
          <div class="topicChange" :class="{ darkBg: settingStore.dark }">
            <div
              class="topicItemLight"
              :class="{ avtiveTopicItemLight: !settingStore.dark }"
              @click="toggleDark"
            >
              <img src="../../../../assets/config/dayTime.svg" alt="" />
            </div>
            <div
              class="topicItemDark"
              :class="{ avtiveTopicItemDark: settingStore.dark }"
              @click="toggleDark"
            >
              <img src="../../../../assets/config/blackSky.svg" alt="" />
            </div>
          </div>
        </div>
        <!-- 界面设置 -->
        <div :class="{ blackTip: settingStore.dark, tip: !settingStore.dark }">
          <text>页面设置</text>
        </div>
        <div class="set">
          <div class="set_item">
            <text>开启 Tags-View</text>
            <el-switch v-model="settingStore.page_setting.isShow_Tagview" />
          </div>
          <div class="set_item">
            <text>侧边栏 Logo</text>
            <el-switch v-model="settingStore.page_setting.isShow_Logo" />
          </div>
          <div class="set_item">
            <text>展示面包屑</text>
            <el-switch v-model="settingStore.page_setting.isShow_Bread" />
          </div>
        </div>
      </div>
    </div>
  </el-drawer>
</template>

<script setup lang="ts">
import { computed, ref, watch, watchEffect } from "vue"
import { useSettingStore } from "@/stores/modules/setting"
import { GETDARK } from "@/utils/local"
import { toggleDark } from "@/utils/changeDark"
const settingStore = useSettingStore()
// const props = defineProps<{
//   drawerShow: boolean
// }>()
// const emit = defineEmits<{
//   changeDrawer: [val: boolean]
// }>()
// const drawerShowa = computed({
//   get() {
//     return props.drawerShow
//   },
//   set(newVal) {
//     emit("changeDrawer", newVal)
//   },
// })
// const model = defineModel("drawerShow", { default: false })
</script>

<style scoped lang="scss">
.title {
  color: black;
  font-size: 15px;
}
.blackTitle {
  color: #fff;
  font-size: 15px;
}
.config {
  .tip {
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
    &::before {
      content: "";
      position: absolute;
      left: 20px;
      height: 1px;
      background-color: #e0e0e0;
      width: 80px;
    }
    &::after {
      content: "";
      position: absolute;
      right: 20px;
      height: 1px;
      background-color: #e0e0e0;
      width: 80px;
    }
  }
  .blackTip {
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
    &::before {
      content: "";
      position: absolute;
      left: 20px;
      height: 1px;
      background-color: #5f5f5f;
      width: 80px;
    }
    &::after {
      content: "";
      position: absolute;
      right: 20px;
      height: 1px;
      background-color: #5f5f5f;
      width: 80px;
    }
  }
  .topic {
    width: 100%;

    .changeTopic {
      width: 100%;
      display: flex;

      .topicChange {
        margin: 20px auto;
        width: 60%;
        position: relative;
        height: 40px;
        border-radius: 5px;
        background-color: #efefef;
        &.darkBg {
          background-color: #424247;
        }
        .topicItemLight {
          display: flex;
          align-items: center;
          justify-content: center;
          border-radius: 5px;
          width: 47%;
          height: 82%;
          position: absolute;
          left: 3px;
          top: 4px;
          img {
            width: 20px;
            height: 20px;
          }
          &.avtiveTopicItemLight {
            background-color: #fff;
            box-shadow: 0px 0px 1px 1px rgba(0, 0, 0, 0.1);
          }
        }
        .topicItemDark {
          display: flex;
          align-items: center;
          justify-content: center;
          border-radius: 5px;
          width: 47%;
          height: 82%;
          position: absolute;
          right: 3px;
          top: 4px;
          img {
            width: 20px;
            height: 20px;
          }
          &.avtiveTopicItemDark {
            background-color: #55555a;
            box-shadow: 0px 0px 5px 1px rgba(0, 0, 0, 0.2);
          }
        }
      }
    }
  }
  .set {
    width: 90%;
    margin: 15px auto;
    display: flex;
    flex-direction: column;
    .set_item {
      width: 100%;
      display: flex;
      align-items: center;
      justify-content: space-between;
      text {
        font-size: 10px;
      }
    }
  }
}
</style>

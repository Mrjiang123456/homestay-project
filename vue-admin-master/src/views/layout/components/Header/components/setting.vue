<template>
  <view class="setting">
    <!-- 刷新 -->
    <div class="refresh" @click="refresh" v-if="defaultSettings.isShowMenu">
      <div ref="refreshRef" class="refreshContent">
        <el-icon size="16" color="gray"><Refresh /></el-icon>
      </div>
    </div>

    <!-- 全屏 -->
    <div
      class="full_Screen"
      @click="toggle"
      v-show="defaultSettings.isFullScreen"
    >
      <img
        src="../../../../../assets/config/fullScreen.svg"
        v-show="!isFullscreen"
      />
      <img
        src="../../../../../assets/config/nofullScreen.svg"
        v-show="isFullscreen"
      />
    </div>

    <!-- 主题切换（黑白切换） -->
    <div class="topic" @click="toggleDark">
      <img
        src="../../../../../assets/config/blackSky.svg"
        class="animate__animated animate__fadeInDown animate__faster"
        v-show="settingStore.dark"
      />
      <img
        src="../../../../../assets/config/dayTime.svg"
        class="animate__animated animate__fadeInDown animate__faster"
        v-show="!settingStore.dark"
      />
    </div>
    <!-- 用户 -->
    <div class="setting-user">
      <el-dropdown trigger="click">
        <div class="user">
          <el-avatar
            :size="35"
            :src="userStore.userinfo.avatar"
            @error="() => true"
          >
            <img
              src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png"
            />
          </el-avatar>
          <div class="nickname">{{ userStore.userinfo.username }}</div>
        </div>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="loginOut">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
    <!-- 设置 -->
    <el-tooltip
      class="box-item"
      effect="dark"
      content="设置"
      placement="bottom"
    >
      <div class="set" @click="set" v-show="defaultSettings.isSet">
        <el-icon size="16" color="gray"><Setting /></el-icon>
      </div>
    </el-tooltip>
    <!-- 设置 -->
    <Drawer v-model="drawerShow" />
  </view>
</template>

<script setup lang="ts">
import { ref } from "vue"
import { useRouter, useRoute } from "vue-router"
import { defaultSettings } from "@/config/setting"
import { useFullscreen } from "@vueuse/core"
import { GETDARK } from "@/utils/local"
import { useSettingStore } from "@/stores/modules/setting"
import { useUserStore } from "@/stores/modules/user"
import { toggleDark } from "@/utils/changeDark"
import { messageBox, notification } from "@/hooks/Element-plus"
const { isFullscreen, toggle } = useFullscreen()
const settingStore = useSettingStore()
const userStore = useUserStore()
const router = useRouter()
const route = useRoute()

// 刷新
const refreshRef = ref()
const refresh = () => {
  settingStore.changeFlush()
  // 添加类
  refreshRef.value!.classList.add("animate__animated", "animate__rotateIn")
  // 等动画结束，移除类
  refreshRef.value.addEventListener("animationend", () => {
    refreshRef.value!.classList.remove("animate__animated", "animate__rotateIn")
  })
}
// 退出登录
const loginOut = async () => {
  await messageBox("提示", "您确定要退出登录吗？", "primary")
  userStore.loginOut()
  router.push({
    path: "/login",
    query: {
      url: route.path,
    },
  })
}

// 设置
const drawerShow = ref(false)
const set = () => {
  drawerShow.value = true
}
</script>

<style scoped lang="scss">
.setting {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: flex-end;
  height: $layout-header_height;
  .refresh {
    height: 100%;
    padding: 0px 15px 0px 15px;
    display: flex;
    align-items: center;
    justify-content: center;
    .refreshContent {
      display: flex;
      align-items: center;
      justify-content: center;
    }
    &:hover {
      background-color: $layout-header_hoverbgColor;
    }
  }
  .full_Screen {
    height: 100%;
    padding: 0px 15px 0px 15px;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: all 0.4s;
    img {
      width: 15px;
      height: 15px;
    }
    &:hover {
      background-color: $layout-header_hoverbgColor;
    }
  }
  .topic {
    height: 100%;
    padding: 0px 15px 0px 15px;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: all 0.4s;
    img {
      width: 20px;
      height: 20px;
    }
    &:hover {
      background-color: $layout-header_hoverbgColor;
    }
  }
  .setting-user {
    height: 100%;
    padding: 0px 10px 0px 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: all 0.4s;
    .user {
      display: flex;
      align-items: center;
      .nickname {
        margin-left: 10px;
        font-size: 14px;
      }
    }
    &:hover {
      background-color: $layout-header_hoverbgColor;
      .nickname {
        color: black;
      }
    }
  }
  .set {
    height: 100%;
    padding: 0px 20px 0px 20px;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: all 0.4s;
    position: relative;
    &:hover {
      background-color: $layout-header_hoverbgColor;
    }
  }
}
</style>

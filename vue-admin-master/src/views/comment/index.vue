<template>
  <div class="msg-all-contain">
    <div class="msg-board">
      <div class="msg-board-contain">
        <div class="msg-head" :style="{backgroundColor: dark ? '#404040' : '#f2f2f2'}">
          <img :src="userinfo.avatar" alt="用户头像" />
          <textarea
            placeholder="请输入内容..."
            v-model="inputComment"
            style="min-height: 100px; width: calc(100% - 300px)"
          ></textarea>
          <button class="submit-button" @click="submit">发表</button>
        </div>
        <div class="msg-content">
          <CommentsChild
            @refreshComments="load"
            :comments="comments"
            :count="layerCount"
          ></CommentsChild>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, provide, ref } from "vue"
import CommentsChild from "@/views/comment/content/index.vue"
import { ElMessage } from "element-plus"
import { addCommentInfo, getCommentList } from "@/api/index"
import { GETUSERINFO } from "@/utils/local"

// 主题颜色配置
import { useSettingStore } from "@/stores/modules/setting"
import { storeToRefs } from "pinia"
const settingStore = useSettingStore()
const { dark } = storeToRefs(settingStore)

const userinfo = GETUSERINFO()
const address = JSON.parse(userinfo.address)

const comments = ref([])
const inputComment = ref("")
const layerCount = ref(0)

const submit = () => {
  if (!inputComment.value.trim()) {
    ElMessage.warning("发表内容不能为空")
    return
  }

  const form = {
    text: inputComment.value.trim(),
    senderId: userinfo.id,
    senderAvatar: userinfo.avatar,
    senderName: userinfo.nickname,
    senderProvince: address.city,
  }

  addCommentInfo(form).then((res) => {
    if (res.code === "200") {
      ElMessage.success("发表成功")
      inputComment.value = ""
      load()
    }
  })
}

const load = async () => {
  await getCommentList().then((res) => {
    if (res.code === "200") {
      comments.value = res.data
    }
  })
}
provide("refreshComments", load)

onMounted(() => {
  load()
})
</script>

<style scoped>
.msg-all-contain {
  width: 100%;
  height: 100%;
  overflow-y: auto;
}

.msg-board-title {
  text-align: center;
  font-size: 28px;
  font-weight: 300;
  margin: 0 0 1.8rem 0;
  color: #000;
  font-family: "Lato", Verdana, sans-serif;
}

.msg-board-contain {
  padding: 0 10px;
}

.msg-head {
  position: relative;
  height: 130px;
  border-radius: 5px;
}

/* 评论头像 */
.msg-head img {
  width: 55px;
  height: 55px;
  border-radius: 50%;
  position: absolute;
  top: 22px;
  left: 13px;
}

/* 评论输入 */
.msg-head textarea {
  position: absolute;
  top: 13px;
  left: 85px;
  border-radius: 5px;
  font-size: 16px;
  padding: 20px;
  border: 2px solid #999999;
  resize: none;
  outline: none;
}

/* 发布评论按钮 */
.submit-button {
  position: absolute;
  top: 13px;
  right: 35px;
  width: 100px;
  height: 100px;
  border: 0;
  border-radius: 5px;
  font-size: 18px;
  font-weight: 500;
  color: #bfbfbf;
  background-color: #0f5197;
  cursor: pointer;
  transition:
    box-shadow 0.2s ease,
    transform 0.2s ease;
}

/* 悬停时的样式 */
.submit-button:hover {
  background-color: #0f5197;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

/* 按下时的样式 */
.submit-button:active {
  background-color: #0f5197;
  transform: translateY(2px);
}

@media (max-width: 900px) {
  .msg-head img {
    width: 40px;
    height: 40px;
    top: 22px;
    left: 13px;
  }

  .msg-head textarea {
    top: 13px;
    left: 70px;
    height: 55px;
    font-size: 15px;
    padding: 10px;
  }

  .submit-button {
    top: 13px;
    right: 16px;
    width: 80px;
    height: 77px;
    font-size: 14px;
  }
}
</style>

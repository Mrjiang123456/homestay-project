<template>
  <div>
    <div
      :class="count > 0 ? '' : 'comments-child-contain'"
      v-for="(item, index) in comments"
      :key="index"
    >
      <!--style 根据层级缩进-->
      <div class="comments-child" :style="{ paddingLeft: 30 * count + 'px' }">
        <div
          :class="count > 0 ? 'comments-child-img-sm' : 'comments-child-img'"
        >
          <img v-if="item.senderAvatar" :src="item.senderAvatar" alt="" />
        </div>
        <div class="comments-child-content">
          <!-- 用户信息 -->
          <div class="comments-child-username-contain">
            <h3 class="comments-child-username"  :style="{color: dark ? '#fff' : '#000'}">{{ item.senderName }}</h3>
            <div
              v-if="item.receiverId && item.receiverId !== ''"
              class="comments-child-replay"
            >
              <span class="reply-text"  :style="{color: dark ? '#fff' : '#000'}">回复</span>
              <h4 class="comments-child-at-username">
                @{{ item.receiverName }}
              </h4>
            </div>
          </div>
          <!-- 评论内容 -->
          <p class="comments-comments-child" :style="{color: dark ? '#fff' : '#000'}">
            {{ item.text }}
          </p>
          <div class="comments-child-bottom-contain">
            <!-- 发布时间 -->
            <span class="comments-child-time">{{ item.createTime + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + address.city }}</span>
            <!--删除和回复-->
            <div
              class="comments-child-right"
              style="display: inline-flex; align-items: center"
            >
              <span
                class="fa fa-thumbs-up delete"
                @click="commentLike(item)"
                style="display: inline-flex; align-items: center"
              >
                <svg-icon style="margin-right: 5px" icon="dz"></svg-icon>
                <span style="margin: 0">{{ item.likeNum }}</span>
              </span>
              <span
                class="fa fa-trash-o delete"
                @click="commentDelete(item, $event)"
                style="display: inline-flex; align-items: center"
                v-show="true"
              >
                <svg-icon style="margin-right: 5px" icon="del"></svg-icon>
              </span>
              <span
                v-if="layerCount < 3"
                class="fa fa-comment-o comments"
                @click="commentReply(item, $event)"
                style="display: inline-flex; align-items: center"
              >
                <svg-icon style="margin-right: 5px" icon="reply"></svg-icon>
              </span>
            </div>
          </div>
          <div class="reply-comment">
            <img :src="userinfo.avatar" alt="" />
            <input
              type="text"
              v-model="replyComment"
              @keyup.enter="replySubmit(item, $event)"
            />
            <button class="reply-button" @click="replySubmit(item, $event)">
              回复
            </button>
          </div>
        </div>
      </div>
      <!--递归调用-->
      <div v-if="item.children">
        <CommentsChild
          :comments="item.children"
          :count="layerCount"
        ></CommentsChild>
      </div>
    </div>
  </div>
</template>

<script setup>
import { inject, onMounted, ref } from "vue"
import { ElMessage } from "element-plus"
import { GETUSERINFO } from "@/utils/local"
import CommentsChild from "./index.vue"
import { addCommentInfo, deleteCommentInfo } from "@/api/index"

// 主题颜色配置
import { useSettingStore } from "@/stores/modules/setting"
import { storeToRefs } from "pinia"
const settingStore = useSettingStore()
const { dark } = storeToRefs(settingStore)

const refreshComments = inject("refreshComments")
const userinfo = GETUSERINFO()
const address = JSON.parse(userinfo.address)

const props = defineProps(["comments", "count"])
const replyComment = ref("")
const layerCount = ref(props.count + 1)

const commentDelete = (item) => {
  deleteCommentInfo({ id: item.id }).then((res) => {
    if (res.code === "200") {
      ElMessage.success("删除成功")
      refreshComments()
    }
  })
}

const commentLike = (item) => {
  item.likeNum++
  addCommentInfo(item).then((res) => {
    if (res.code === "200") {
      refreshComments()
    }
  })
}

const commentReply = (item, event) => {
  replyComment.value = ""
  const replyDom = event.currentTarget.parentNode.parentNode.nextElementSibling
  if (replyDom.style.display === "" || replyDom.style.display === "none") {
    replyDom.style.display = "flex"
    const replyInput = replyDom.getElementsByTagName("input")[0]
    replyInput.setAttribute("placeholder", "回复 @ " + item.senderName)
  } else {
    replyDom.style.display = "none"
  }
}

const replySubmit = async (item, event) => {
  if (!replyComment.value || replyComment.value === "") {
    ElMessage.warning("回复内容不能为空")
    return
  }

  const form = {
    pid: item.id,
    text: replyComment.value,
    senderId: userinfo.id,
    senderName: userinfo.nickname,
    senderAvatar: userinfo.avatar,
    senderProvince: address.city,
    receiverId: item.senderId,
    receiverName: item.senderName,
    receiverAvatar: item.senderAvatar,
    receiverProvince: item.senderProvince,
  }
  // push 追加到数组的末尾
  addCommentInfo(form).then((res) => {
    if (res.code === "200") {
      ElMessage.success("回复成功")
      // 通知父组件更新评论列表
      refreshComments()
    }
  })
  // item.children.push(form)
  replyComment.value = ""
  event.currentTarget.parentNode.style.display = "none"
}

onMounted(() => {
  layerCount.value++
})
</script>
<style scoped>
/* 评论内容区域 */
.msg-content .comments-child {
  display: flex;
  position: relative;
  padding: 18px 10px 18px 10px;
}
.comments-child-contain {
  border-bottom: 1px solid #d3d9e1;
  padding: 0 25px;
}

/* 子评论头像 */
.comments-child .comments-child-img {
  /*flex: 1;*/
  text-align: center;
  padding: 0 20px 0 0;
}
/* 子评论头像 */
.comments-child-img > img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}

/* 子评论小头像 */
.comments-child .comments-child-img-sm {
  /*flex: 1;*/
  text-align: center;
  padding: 0 20px 0 0;
}
/* 子评论小头像 */
.comments-child-img-sm > img {
  width: 35px;
  height: 35px;
  border-radius: 50%;
}

/* 子评论用户名 */
.comments-child-username {
  margin: 0;
  font-size: 15px;
  width: auto;
  text-align: left;
}

/* 子评论回复用户名 */
.comments-child-at-username {
  margin: 0;
  color: #0f5197;
}

.comments-child-username-contain {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  flex-wrap: nowrap;
  /*margin-bottom: 15px;*/
}

/* 回复内容 */
.reply-text {
  margin: 0 10px;
  font-size: 16px;
  font-weight: 400;
  //color: #000 !important;
  font-family: "Lato", Verdana, sans-serif !important;
}

.comments-child-replay {
  display: flex;
  align-items: center;
  font-size: 15px;
  margin: 0;
}

.comments-child-content {
  flex: 9;
}
/* 回复时间 */
.comments-child-time {
  color: #767575;
  font-size: 12px;
  white-space: nowrap;
}
.comments-comments-child {
  font-size: 16px;
  margin-top: 10px;
  margin-bottom: 10px;
  font-weight: 400;
  font-family: "Lato", Verdana, sans-serif !important;
  text-align: left;
  display: block; /* 确保块级显示 */
  word-wrap: break-word; /* 确保长单词换行 */
  white-space: normal; /* 允许文本换行 */
  overflow-wrap: break-word; /* 保证长单词换行 */
  word-break: break-all; /* 强制在任何地方换行 */
}

.comments-child-bottom-contain {
  display: flex;
  align-items: center;
}
/* 右边点赞和评论 */
.comments-child-right {
  position: absolute;
  right: 1.5%;
  top: 10px;
  white-space: nowrap;
}
.comments-child-right span {
  font-weight: 400;
  font-size: 15px;
  margin: 0 20px;
  cursor: pointer;
  color: #333 !important;
}
/* 删除评论 */
.delete:hover {
  color: red;
}
.delete::before {
  /* 想使用的icon的十六制编码，去掉&#x之后的 */
  margin-right: 4px;
  font-size: 16px;
}
/* 评论字体图标 */
.comments::before {
  /* 想使用的icon的十六制编码，去掉&#x之后的 */
  margin-right: 4px;
  font-size: 16px;
}
/* 点赞字体图标 */
.praise::before {
  /* 想使用的icon的十六制编码，去掉&#x之后的 */
  content: "\ec7f";
  /* 必须加 */
  font-family: "iconfont";
  margin-right: 4px;
  font-size: 19px;
}

.to_reply {
  color: rgb(106, 106, 106);
}

/* 评论 */
.reply-comment {
  margin: 10px 0 0 0;
  align-items: center;
  justify-content: space-around;
  display: none;
}
/* 评论输入框头像 */
.reply-comment > img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}
/* 评论输入框 */
.reply-comment input {
  height: 40px;
  border-radius: 5px;
  outline: none;
  width: 70%;
  font-size: 16px;
  padding: 0 10px;
  /* border: 2px solid #f8f8f8; */
  border: 2px solid #0f5197;
}
/* 发布评论按钮 */
.reply-comment button {
  width: 100px;
  height: 43px;
  border: 0;
  border-radius: 5px;
  font-size: 16px;
  font-weight: 500;
  letter-spacing: 2px;
  color: #fff !important;
  background-color: #0f5197;
  cursor: pointer;
}
/* 鼠标经过字体加粗 */
.reply-comment button:hover {
}
/* 评论点赞颜色 */
.comment-like {
  color: red;
}

.no-content-warn {
  border: 1px solid red !important;
}

@media (max-width: 900px) {
  .comments-child-right {
    position: inherit;
    margin-left: 10px;
  }

  .comments-child > img {
    width: 40px;
    height: 40px;
    border-radius: 50%;
  }

  .reply-comment button {
    width: 50px;
    height: 43px;
    border: 0;
    border-radius: 5px;
    font-size: 14px;
    font-weight: 500;
    color: #fff !important;
    background-color: #00a1d6;
    cursor: pointer;
  }

  .reply-comment input {
    height: 40px;
    border-radius: 5px;
    outline: none;
    width: 50%;
    font-size: 16px;
    padding: 0 10px;
    margin: 0 10px;
    /* border: 2px solid #f8f8f8; */
    border: 2px solid skyblue;
  }

  .comments-child-right span {
    font-weight: 400;
    font-size: 12px;
    margin: 0 5px;
    cursor: pointer;
    color: #333 !important;
  }

  .reply-comment {
    justify-content: flex-start;
  }
  .container-fluid {
    position: relative;
  }

  .comments-child-username-contain {
    flex-wrap: wrap;
  }
  .comments-child-username {
    width: 100%;
  }
  .comments-child-replay {
    margin-top: 10px;
  }
  .reply-text {
    margin: 0 10px 0 0;
  }
  .msg-class {
    font-size: 25px;
    line-height: 26px;
  }
}

.reply-button {
  width: 80px; /* 按钮的宽度 */
  height: 40px; /* 按钮的高度 */
  border: 2px solid #0f5197; /* 边框颜色 */
  border-radius: 5px; /* 圆角边框 */
  font-size: 16px; /* 字体大小 */
  font-weight: 600; /* 字体加粗 */
  color: #ffffff; /* 字体颜色 */
  background-color: #0f5197; /* 背景颜色 */
  cursor: pointer; /* 鼠标指针样式 */
  transition:
    background-color 0.3s,
    box-shadow 0.3s,
    transform 0.2s;
}

/* 悬停时的样式 */
.reply-button:hover {
  background-color: #0f5197; /* 改变背景颜色 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 添加阴影效果 */
}

/* 按下时的样式 */
.reply-button:active {
  background-color: #0f5197; /* 按下时的背景颜色 */
  transform: translateY(2px); /* 按下时的位移效果 */
}
</style>

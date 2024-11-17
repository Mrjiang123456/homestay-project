<template>
  <div class="main-content">
    <el-card style="width: 50%; margin: 30px auto">
      <div style="text-align: right; margin-bottom: 20px">
        <el-button
          type="primary"
          @click="dialogVisible = true"
          v-if="dialogVisible !== true"
        >
          修改密码
        </el-button>
        <el-button
          type="primary"
          @click="dialogVisible = false"
          v-if="dialogVisible === true"
        >
          取消修改
        </el-button>
      </div>
      <el-form
        ref="ruleFormRef"
        :rules="rules"
        :model="user"
        label-position="left"
        label-width="80px"
        style="padding-right: 20px"
      >
        <div style="margin: 15px; text-align: center">
          <el-upload
            class="avatar-uploader"
            :action="uploadUrl"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
          >
            <img v-if="user.avatar" :src="user.avatar" class="avatar" alt="" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </div>
        <el-form-item label="用户名" prop="username">
          <el-input
            v-model="user.username"
            placeholder="用户名"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item
          label="昵称"
          prop="nickname"
          v-if="dialogVisible === false"
        >
          <el-input v-model="user.nickname" placeholder="昵称"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone" v-if="dialogVisible === false">
          <el-input v-model="user.phone" placeholder="电话"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email" v-if="dialogVisible === false">
          <el-input v-model="user.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item label="原密码" prop="password" v-if="dialogVisible">
          <el-input
            v-model="user.password"
            placeholder="请输入原始密码"
          ></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword" v-if="dialogVisible">
          <el-input
            v-model="user.newPassword"
            placeholder="请输入新密码"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="确认密码"
          prop="confirmPassword"
          v-if="dialogVisible"
        >
          <el-input
            v-model="user.confirmPassword"
            placeholder="请确认新密码"
          ></el-input>
        </el-form-item>
        <div style="text-align: center; margin-bottom: 20px">
          <el-button
            type="primary"
            @click="saveUser(ruleFormRef)"
            v-if="dialogVisible !== true"
          >
            保存用户
          </el-button>
          <el-button
            type="primary"
            @click="savePassword(ruleFormRef)"
            v-if="dialogVisible === true"
          >
            保存密码
          </el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { updatePassword, updateUserInfo } from "@/api/index";
import { reactive, ref } from "vue";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";
import { piniaFunction } from "@/stores";
import { useUserStore } from "@/stores/modules/user";
import { Plus } from "@element-plus/icons-vue";
//在其他文件拿pinia数据，需要传入pinia实例
const userStore = useUserStore(piniaFunction())
const router = useRouter()
const ruleFormRef = ref()
const uploadUrl = `${import.meta.env.VITE_BASE_URL}/files/upload`
const userinfo = JSON.parse(localStorage.getItem("userinfo"))
const user = ref(userinfo.userinfo)
const dialogVisible = ref(false)

const saveUser = async (formEl) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      // 保存当前的用户信息到数据库
      updateUserInfo(user.value).then((res) => {
        if (res.code === "200") {
          // 成功更新
          ElMessage.success("保存成功")
          userStore.loginOut()
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}

const savePassword = async (formEl) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      updatePassword(user.value).then((res) => {
        if (res.code === "200") {
          // 成功更新
          ElMessage.success("修改密码成功")
          userStore.loginOut()
        } else {
          ElMessage.error(res.message)
        }
      })
    }
  })
}

const handleAvatarSuccess = (response, file, fileList) => {
  // 把user的头像属性换成上传的图片的链接
  user.value.avatar = response.data
}

const validatePassword = (rule, value, callback) => {
  if (value === "") {
    callback(new Error("不能为空"))
  } else if (value !== user.value.newPassword) {
    callback(new Error("两次输入的密码不一致"))
  } else {
    callback()
  }
}
const rules = reactive({
  username: [{ required: true, message: "不能为空", trigger: "blur" }],
  nickname: [{ required: true, message: "不能为空", trigger: "blur" }],
  email: [{ required: true, message: "不能为空", trigger: "blur" }],
  phone: [{ required: true, message: "不能为空", trigger: "blur" }],
  password: [{ required: true, message: "不能为空", trigger: "blur" }],
  newPassword: [{ required: true, message: "不能为空", trigger: "blur" }],
  confirmPassword: [
    { validator: validatePassword, required: true, trigger: "blur" },
  ],
})
</script>

<style scoped>
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 200px;
  height: 200px;
  line-height: 120px;
  text-align: center;
  border-radius: 50%;
}
.avatar {
  width: 200px;
  height: 200px;
  display: block;
  border-radius: 50%;
}

body {
  font-family: myFont;
}
</style>

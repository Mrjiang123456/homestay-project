<template>
  <div class="login">
    <!-- vue书写一个登录页面 -->
    <el-row>
      <el-col :xs="0" :sm="0" :md="0" :lg="14">
        <div class="pic">
          <div class="title animate__animated animate__fadeIn">
            <text style="font-size: 40px">欢迎使用</text>
            <text style="font-size: 16px">
              基于 Hadoop + Hive + Spark + SpringBoot + Vue +
              Echarts的项目！！！
            </text>
          </div>
          <img src="../../assets/image/login/bg1.png" alt="" />
          <div
            class="out_img animate__animated animate__fadeInLeft animate__faster"
          >
            <img src="../../assets/image/login/people.svg" />
          </div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="10">
        <div class="form">
          <img class="bg2" src="../../assets/image/login/bg2.png" alt="" />
          <div
            class="content p-10 w400px rounded-xl animate__animated animate__fadeInDown animate__fast"
            :style="{ height: type ? '400px' : '480px' }"
          >
            <div class="name">
              <img :src="basic.logo" alt="" />
              <text>
                {{ basic.title }}
              </text>
            </div>

            <el-form :model="loginParams" :rules="rules">
              <div class="jump-link">
                <el-link type="primary" @click="handleChange">
                  {{ type ? "注册账号" : "返回登录" }}
                </el-link>
              </div>
              <el-form-item prop="username">
                <el-input
                  size="large"
                  placeholder="请输入用户名"
                  v-model="loginParams.username"
                >
                  <template #prefix>
                    <el-icon :size="20"><UserFilled /></el-icon>
                  </template>
                </el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input
                  size="large"
                  placeholder="请输入密码"
                  v-model="loginParams.password"
                  type="password"
                >
                  <template #prefix>
                    <el-icon :size="20"><Lock /></el-icon>
                  </template>
                </el-input>
              </el-form-item>
              <el-form-item prop="firmPassword" v-show="!type">
                <el-input
                  size="large"
                  placeholder="请输入密码"
                  v-model="loginParams.firmPassword"
                  type="password"
                >
                  <template #prefix>
                    <el-icon :size="20"><Lock /></el-icon>
                  </template>
                </el-input>
              </el-form-item>

              <el-form-item>
                <el-button
                  class="loginBtn"
                  @click="login"
                  type="primary"
                >
                  {{ type ? "登录" : "注册账号" }}
                </el-button>
              </el-form-item>
            </el-form>
            <img class="bg3" src="../../assets/image/login/bg3.png" alt="" />
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { notification } from "@/hooks/Element-plus"
import { basic } from "@/config/setting"
import { useUserStore } from "@/stores/modules/user"
import { reactive, ref } from "vue"
import { useRoute, useRouter } from "vue-router"
import { ElMessage } from "element-plus"
import { register } from "@/api"

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const loginParams = reactive({
  username: "",
  password: "",
  firmPassword: "",
})

// true: 登录， false: 注册
const type = ref(true)

const validateUser = (rule: any, value: any, callback: any) => {
  if (value.length < 4) {
    callback(new Error("用户名长度必须大于4位"))
  } else {
    callback()
  }
}
const validatePass = (rule: any, value: any, callback: any) => {
  if (value.length < 6) {
    callback(new Error("密码长度必须大于6位"))
  } else {
    callback()
  }
}
const validateConfirmPassword = (rule: any, value: any, callback: any) => {
  if (value === "") {
    callback(new Error("密码不能为空"))
  } else if (value !== loginParams.password) {
    callback(new Error("两次密码输入不一致"))
  } else {
    callback()
  }
}

let rules = reactive({
  username: [{ required: true, validator: validateUser, trigger: "blur" }],
  password: [{ required: true, validator: validatePass, trigger: "blur" }],
  firmPassword: [{ required: true, validator: validateConfirmPassword, trigger: "change" }],
})
const login = async () => {

  localStorage.setItem("username", loginParams.username)
  if (type.value) {
    const mes = await userStore.loginUp(loginParams)
    notification(mes, "您好，欢迎回来!", "success")
    router.push({ path: "/" })
  } else {
    register(loginParams).then((res) => {
      if (res.code == "200") {
        ElMessage.success("注册成功")
        type.value = true
      } else {
        ElMessage.error(res.message)
      }
    })
  }
}

const handleChange = () => {
  type.value = !type.value
  loginParams.username = ""
  loginParams.password = ""
}
</script>

<style lang="scss">
::-webkit-scrollbar {
  display: none;
}
// .el-input__inner {
// color: #fff !important;
// }
.el-input .el-input__wrapper {
  // background-color: #424666;
  //height: 45px;
}
.jump-link {
  text-align: right; /* 设置文本对齐方式为右对齐 */
  width: 100%; /* 确保占满父容器的宽度 */
  margin-bottom: 10px;
  margin-right: 10px;
}

.login {
  width: 100vw;
  display: flex;
  height: 100vh;
  overflow: hidden;

  .el-row {
    width: 100%;
    height: 100%;
    .el-col {
      width: 50%;
      .pic {
        width: 100%;
        height: 100%;
        position: relative;
        display: flex;
        .title {
          margin: auto;
          position: absolute;
          top: 160.0012px;
          right: 180px;
          display: flex;
          flex-direction: column;
          text {
            &:nth-child(1) {
              font-size: 28.0013px;
              font-weight: bold;
            }
            &:nth-child(2) {
              font-size: 12px;
              margin-top: 10.0012px;
              color: gray;
            }
            &:nth-child(3) {
              font-size: 12px;
              margin-top: 10.0012px;
              color: gray;
            }
          }
        }
        .out_img {
          width: 40%;
          height: 80%;
          position: absolute;
          top: 150px;
          img {
            width: 100%;
            height: 100%;

            transform: rotateY(180deg);
          }
        }
        img {
          position: absolute;
          bottom: 0;
          width: 100%;
        }
      }
      .form {
        width: 100%;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        position: relative;

        .bg2 {
          position: absolute;
          top: 0;
          width: 100%;
        }
        .el-form-item__error {
          margin-top: 1.9988px;
        }
        .content {
          box-shadow: 0px 0px 10.0012px 1.9988px rgba(0, 0, 0, 0.1);
          // background: url("../../assets/image/login/bg.jpg") no-repeat;
          // background: rgba(40, 57, 101, 0.9);
          .bg3 {
            width: 100%;
            height: 40px;
            position: absolute;
            bottom: 0px;
            right: 0px;
          }
          .name {
            font-size: 30px;
            width: 100%;
            height: 30%;
            display: flex;
            align-items: center;
            justify-content: center;
            img {
              width: 70.0012px;
              height: 70.0012px;
            }
            text {
              text-shadow: 0px 0px 10.0012px rgba(255, 255, 255, 0.6);
            }
          }
          .el-form {
            height: 80%;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            .el-form-item {
              width: 98%;
              .password {
                display: flex;
                width: 100%;
                justify-content: space-between;
                .el-checkbox__input.is-checked .el-checkbox__inner {
                  background-color: orange;
                  border: 0;
                }
                .el-checkbox__input.is-checked + .el-checkbox__label {
                  color: #fff;
                }
                .forgetPassword {
                  color: gray;
                  cursor: pointer;
                }
              }

              .loginBtn {
                cursor: pointer;
                width: 100%;
                height: 45px;
                display: flex;
                align-items: center;
                justify-content: center;
                border: 0;
                color: #fff;
                border-radius: 4.9988px;
                font-size: 16.0013px;
              }
            }
          }
        }
      }
    }
  }
}
</style>

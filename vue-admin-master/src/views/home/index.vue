<template>
  <div class="home">
    <div class="home-info">
      <div class="home_user">
        <el-row :gutter="10">
          <el-col class="leftCol" :xs="24" :sm="24" :md="24" :lg="10">
            <el-card style="margin-bottom: -10px">
              <div class="home-user">
                <el-avatar
                  :size="60"
                  :src="userStore.userinfo.avatar"
                  @error="() => true"
                >
                  <img
                    src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png"
                    alt=""
                  />
                </el-avatar>
                <div class="user_content">
                  <div class="tip">
                    Hello，{{ userStore.userinfo.nickname }}
                  </div>
                  <div class="content">
                    {{ address.city }}今日{{
                      weather.type ? weather.type : "多云"
                    }}，气温在{{ weather.low ? weather.low : "15℃" }}至{{
                      weather.high ? weather.high : "25℃"
                    }}之间，{{ weather.notice ? weather.notice : "天气舒适" }}。
                  </div>
                </div>
              </div>
              <div class="good_tip">
                <text>欲买桂花同载酒，终不似，少年游</text>
                <text>宋代刘过</text>
              </div>
            </el-card>
          </el-col>
          <el-col class="rightCol" :xs="24" :sm="24" :md="24" :lg="14">
            <el-card style="margin-bottom: -10px">
              <div class="introduce">
                <text>🚩欢迎来到大数据系统</text>
                <text>
                  这是一款基于 Hadoop + Hive + Spark + SpringBoot + Vue +
                  Echarts的项目！！！
                </text>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
      <div class="store">
        <el-card>
          <div class="tit">技术栈</div>
          <div class="store_content">
            <el-row :gutter="8">
              <el-col :xs="24" :md="12" :lg="8">
                <div class="store_item">
                  <div class="logo">
                    <img src="../../assets/image/hadoop.png" alt="" />
                    <el-button class="hadoop" @click="goHadoop" size="small">
                      Go
                    </el-button>
                  </div>
                  <div class="content">
                    Hadoop是分布式存储与处理大规模数据的开源框架。
                  </div>
                </div>
              </el-col>
              <el-col :xs="24" :md="12" :lg="8">
                <div class="store_item">
                  <div class="logo">
                    <img src="../../assets/image/hive.png" alt="" />
                    <el-button class="hive" @click="goHive" size="small">
                      Go
                    </el-button>
                  </div>
                  <div class="content">
                    Hive是基于Hadoop的数据仓库工具，支持SQL查询。
                  </div>
                </div>
              </el-col>
              <el-col :xs="24" :md="12" :lg="8">
                <div class="store_item">
                  <div class="logo">
                    <img src="../../assets/image/spark.png" alt="" />
                    <el-button @click="goSpark" class="spark" size="small">
                      Go
                    </el-button>
                  </div>
                  <div class="content">
                    Spark是快速的分布式数据处理引擎，适合实时和批处理。
                  </div>
                </div>
              </el-col>
              <el-col :xs="24" :md="12" :lg="8">
                <div class="store_item">
                  <div class="logo">
                    <img src="../../assets/image/springboot.png" alt="" />
                    <el-button
                      class="springboot"
                      @click="goSpringBoot"
                      size="small"
                    >
                      Go
                    </el-button>
                  </div>
                  <div class="content">
                    SpringBoot是Java应用开发框架，简化微服务开发。
                  </div>
                </div>
              </el-col>
              <el-col :xs="24" :md="12" :lg="8">
                <div class="store_item">
                  <div class="logo">
                    <img src="../../assets/image/vue.png" alt="" />
                    <el-button class="vue" @click="goVue" size="small">
                      Go
                    </el-button>
                  </div>
                  <div class="content">
                    Vue是轻量级前端框架，专注于构建用户界面。
                  </div>
                </div>
              </el-col>
              <el-col :xs="24" :md="12" :lg="8">
                <div class="store_item">
                  <div class="logo">
                    <img src="../../assets/image/mysql.png" alt="" />
                    <el-button @click="goMysql" class="mysql" size="small">
                      Go
                    </el-button>
                  </div>
                  <div class="content">
                    MySQL是广泛使用的关系型数据库管理系统。
                  </div>
                </div>
              </el-col>
            </el-row>
          </div>
        </el-card>
      </div>
    </div>
    <div class="EChart_Group">
      <el-row :gutter="10">
        <el-col :xs="24" :sm="24" :md="24" :lg="17">
          <el-card>
            <div class="tit">活跃度趋势</div>
            <div class="line">
              <component
                :is="chartComponent('singleLine')"
                :data="activeTrend"
                :colorStops="colorStops"
              />
            </div>
          </el-card>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="7">
          <el-card>
            <div class="tit">登录历史记录</div>
            <div class="pie">
              <LoginList :data="loginList" />
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useUserStore } from "@/stores/modules/user"
import { defineAsyncComponent, onMounted, ref } from "vue"
import { useRouter } from "vue-router"
import LoginList from "@/components/LoginList/index.vue"
import { getActiveTrend, getLoginList } from "@/api"

const userStore = useUserStore()
const address = JSON.parse(userStore.userinfo.address)
const weather = JSON.parse(userStore.userinfo.weather)

const router = useRouter()
const goHadoop = () => {
  window.open("https://hadoop.apache.org/")
}
const goHive = () => {
  window.open("https://hive.apache.org/")
}
const goSpark = () => {
  window.open("https://spark.apache.org/")
}
const goSpringBoot = () => {
  window.open("https://spring.io/projects/spring-boot")
}
const goVue = () => {
  window.open("https://vuejs.org/")
}
const goMysql = () => {
  window.open("https://www.mysql.com/")
}

const loginList = ref([])
const activeTrend = ref([])

onMounted(() => {
  getLoginList({ id: userStore.userinfo.id }).then((res: any) => {
    if (res.code === "200") {
      loginList.value = res.data
    }
  })
  getActiveTrend({ id: userStore.userinfo.id }).then((res: any) => {
    if (res.code === "200") {
      activeTrend.value = res.data
    }
  })
})

const colorStops = ref([
  {
    offset: 0,
    color: "#e8f2fd", // 渐变色的起始颜色
  },
  {
    offset: 1,
    color: "#0F5197", // 渐变线的结束颜色
  },
])

// 异步加载图表
const chartComponent = (val: string) => {
  return defineAsyncComponent(
    () => import(`@/components/EchartsChat/${val}.vue`),
  )
}
</script>

<style scoped lang="scss">
.el-card {
  border: 0;
}
.home {
  height: 100vh;

  .home-info {
    width: 100%;
    display: flex;
    flex-direction: column;

    .home_user {
      width: 100%;
      height: 100%;
      margin-bottom: 10px;
      .rightCol {
        margin-top: 8px;
      }
      .leftCol {
        margin-top: 8px;
      }
      .el-card {
        height: 100%;

        .home-user {
          display: flex;

          .user_content {
            display: flex;
            flex-direction: column;
            justify-content: space-around;
            margin-left: 10px;
            .tip {
              font-size: 18px;
              font-weight: bold;
            }
            .content {
              font-size: 12px;
              color: gray;
            }
          }
        }
        .good_tip {
          padding: 10px;
          font-size: 14px;
          color: gray;
          border-top: 1px var(--el-border-color-dark) solid;
          margin-top: 10px;
          text {
            width: 100%;
            &:nth-child(2) {
              display: flex;
              align-items: center;
              justify-content: flex-end;
              position: relative;
              color: #c5c3c3;
              &::before {
                content: "";
                position: absolute;
                width: 25px;
                height: 1px;
                background-color: #c5c3c3;
                top: 8px;
                right: 50px;
              }
            }
          }
        }
        .introduce {
          width: 100%;
          height: 120px;
          display: flex;
          flex-direction: column;
          text {
            &:nth-child(1) {
              font-size: 20px;
            }
            &:nth-child(2) {
              font-size: 12px;
              color: gray;
              margin-top: 20px;
            }
            &:nth-child(3) {
              width: 100%;
              display: flex;
              justify-content: flex-end;
              margin-top: 10px;
            }
          }
        }
      }
    }
    .message {
      display: flex;
      justify-content: flex-end;

      .mesItem {
        display: flex;
        flex-direction: column;
        margin: 5px 5px 0px 5px;
        text {
          &:nth-child(1) {
            font-size: 12px;
            color: gray;
          }
          &:nth-child(2) {
            font-size: 16px;
          }
        }
      }
    }
    .store {
      width: 100%;
      height: 100%;
      margin-bottom: 10px;
      .el-card {
        width: 100%;
        height: 100%;
        display: flex;
        flex-direction: column;
        :deep(.el-card__body) {
          padding: 0 !important;
        }

        .tit {
          width: 100%;
          border-bottom: 1px var(--el-border-color-dark) solid;
          padding: 20px;
          padding-bottom: 10px;
          font-size: 16px;
          font-weight: bold;
        }
        .store_content {
          display: flex;
          align-items: center;
          justify-content: space-evenly;
          flex-wrap: wrap;
          padding: 10px;
          .store_item {
            width: 100%;
            padding: 10px;
            height: 100px;
            border-radius: 5px;
            margin-bottom: 8px;
            border: 1px var(--el-border-color-dark) solid;
            .logo {
              display: flex;
              align-items: center;
              justify-content: space-between;
              .el-button {
                color: #fff;
              }
              .hadoop {
                background-color: #41b883;
              }
              .hive {
                background-color: #9c5cfe;
              }
              .spark {
                background-color: #409eff;
              }
              .springboot {
                background-color: #a9314d;
              }
              .vue {
                background-color: #ffc61e;
              }
              .mysql {
                background-color: #007acc;
              }
              img {
                width: 50px;
                height: 50px;
              }
            }
            .content {
              margin-top: 5px;
              // color: gray;
            }
          }
        }
      }
    }
  }
  .EChart_Group {
    .el-card {
      height: 400px;
      width: 100%;
      margin-bottom: 8px;
      .tit {
        font-size: 16px;
        font-weight: bold;
        padding-bottom: 10px;
        border-bottom: 1px var(--el-border-color-dark) solid;
      }
      .line {
        height: 300px;
        width: 100%;
        // justify-content: space-between;
      }
      .pie {
        height: 300px;
        width: 100%;
      }
    }
  }
}
</style>

<style lang="less">
body {
  font-family: myFont;
}
</style>

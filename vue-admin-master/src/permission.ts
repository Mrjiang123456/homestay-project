//路由鉴权
import nprogress from "./utils/nprogress"
import { basic } from "./config/setting"
import router from "@/router"
import { GETTOKEN } from "./utils/local"
import { ElMessage } from "element-plus"
import { piniaFunction } from "@/stores"
import { useUserStore } from "@/stores/modules/user"
//在其他文件拿pinia数据，需要传入pinia实例
const userStore = useUserStore(piniaFunction())
//全局前置守卫
router.beforeEach(async (to, from, next) => {
  nprogress.start()
  document.title = `${basic.title}-${to.meta.title}`
  try {
    if (GETTOKEN()) {
      if (to.path !== "/login") {
        //判断有没有用户信息

        if (
          Object.keys(
            JSON.parse(<string>localStorage.getItem("userinfo")).userinfo,
          ).length === 0
        ) {
          try {
            await userStore.getUserInfo()
            next({ ...to })
          } catch (error: any) {
            ElMessage.error("重新登录")
            await userStore.loginOut()
            next({ path: "/login", query: { url: to.path } })
          }
        } else {
          next()
        }
      } else {
        next({ path: "/" })
      }
    } else {
      if (to.path !== "/login") {
        next({ path: "/login", query: { url: to.path } })
      } else {
        next()
      }
    }
  } catch (e) {
    /* empty */
  }
})
// router.beforeEach((to, from) => {
//   if (!GETTOKEN() && to.path !== "/login") {
//     return "/login"
//   } else if (GETTOKEN() && to.path === "/login") {
//     return "/"
//   } else {
//     return true
//   }
// })

//全局后置守卫
router.afterEach((to, from, next) => {
  nprogress.done()
})

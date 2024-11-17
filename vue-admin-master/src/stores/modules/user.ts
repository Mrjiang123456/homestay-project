import { getMenuPermission, getUserInfoApi, loginApi } from "@/api/index"
import router from "@/router"
import { GETTOKEN, REMOVETOKEN, SETTOKEN } from "@/utils/local"
import { defineStore } from "pinia"
import { ref, toRaw } from "vue"

export const useUserStore = defineStore(
  "user",
  () => {
    /***
     * @登录
     */
    const token = ref("" || GETTOKEN())
    const userinfo = ref(<any>{})
    // 用户登录
    const loginUp = async (loginParams: any) => {
      const res: any = await loginApi(loginParams)
      if (res.code === "200") {
        token.value = res.data
        SETTOKEN(res.data)
        // 获取用户信息
        await getUserInfo()
        return "登录成功"
      } else {
        return Promise.reject(new Error(res.message))
      }
    }

    // 路由表
    const routerMenuList = ref([])
    // 登录获取用户信息
    const getUserInfo = async () => {
      const res: any = await getUserInfoApi()

      if (res.code === "200") {
        userinfo.value = res.data
        const permission = await getMenuPermission()
        addDynamicMenu(permission.data)
        if (routerMenuList.value && routerMenuList.value.length > 0) {
          routerMenuList.value.forEach((item: any) => {
            router.addRoute("layout", item)
          })
        }
      } else {
        return Promise.reject(new Error(res.message))
      }
    }
    // 重置路由
    const resetRoutes = () => {
      //获取所有路由
      routerMenuList.value.forEach((route: any) => {
        router.removeRoute(route.name)
      })
    }

    // 退出登录
    const loginOut = () => {
      router.push("/")
      // 重置路由
      resetRoutes()
      REMOVETOKEN()
      userinfo.value = {}
      // 置空侧边栏
      routerMenuList.value = []
    }

    // 动态路由
    const addDynamicMenu = (menus: any) => {
      const modules = import.meta.glob("../../views/**/**/*.vue")
      function routerSet(router: any) {
        if (router) {
          router.forEach((route: any) => {
            if (route.children.length === 0) {
              const url = `../../views${route.path}/index.vue`
              route.component = modules[url]
            } else {
              routerSet(route.children)
            }
          })
        }
      }
      routerSet(menus)
      routerMenuList.value = menus
    }

    return {
      token,
      routerMenuList,
      userinfo,
      loginUp,
      loginOut,
      getUserInfo,
      addDynamicMenu,
    }
  },
  {
    persist: {
      key: "userinfo",
      paths: ["userinfo", "routerMenuList"],
    },
  },
)

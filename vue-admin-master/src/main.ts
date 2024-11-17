import { createApp } from "vue"
import { piniaFunction } from "./stores/index"
import component from "@/components/index"
import App from "./App.vue"
import router from "./router"
import "./style.css"
import "element-plus/theme-chalk/dark/css-vars.css"
import "virtual:uno.css"
import "default-passive-events"

import "@/styles/index.scss"
// svg需要的配置代码
import "virtual:svg-icons-register"
// 导入路由鉴权
import "./permission"
import { useUserStore } from "@/stores/modules/user"

// 动态路由
const menus: any = localStorage.getItem("userinfo")
const store = useUserStore()
if (menus) {
  store.addDynamicMenu(JSON.parse(menus).routerMenuList)
  if(store.routerMenuList && store.routerMenuList.length > 0) {
    store.routerMenuList.forEach((item: any) => {
      router.addRoute("layout", item)
    })
  }
}

const pinia = piniaFunction()
const app = createApp(App)
app.use(component)
app.use(pinia)
app.use(router)
app.mount("#app")

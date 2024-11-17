// element-plus图标
import * as ElementPlusIconsVue from "@element-plus/icons-vue"

export default {
  install: (app: any) => {
    // 全局注册图标
    for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
      app.component(key, component)
    }
  },
}

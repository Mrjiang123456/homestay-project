import { useSettingStore } from "@/stores/modules/setting"
import { defaultSettings } from "@/config/setting"
import debounce from "lodash/throttle"

export const publicFun = () => {
  const settings = useSettingStore()
  window.addEventListener(
    "resize",
    debounce(() => {
      const width = document.documentElement.clientWidth

      if (width < 768) {
        settings.flag = true
      } else {
        settings.flag = false
      }
    }, 500),
  )
  const width = document.documentElement.clientWidth
  if (width < 375) {
    defaultSettings.isShowMenu = false
    settings.flag = true
  } else {
    defaultSettings.isShowMenu = true
    settings.flag = false
  }
}

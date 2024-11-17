import { GETDARK } from "./local"
import { useSettingStore } from "@/stores/modules/setting"
export const toggleDark = () => {
  const settingStore = useSettingStore()
  //拿去dark的状态
  settingStore.changeDark()
  if (GETDARK()) {
    document.documentElement.className = "dark"
  } else {
    document.documentElement.className = ""
  }
}

import { GETDARK, SETDARK } from "@/utils/local"
import { defineStore } from "pinia"
import { reactive, ref } from "vue"

export const useSettingStore = defineStore(
  "setting",
  () => {
    /***
     * @刷新
     */
    const flush = ref(false)
    const changeFlush = () => {
      flush.value = !flush.value
    }
    /***
     * @侧边栏的折叠
     */
    const flag = ref(false)
    const changeFlag = () => {
      flag.value = !flag.value
    }
    /**
     * @主题相关
     */
    const dark = ref(false || GETDARK())
    const changeDark = () => {
      dark.value = !dark.value
      SETDARK(dark.value)
    }
    /**
     * @页面设置
     */
    const page_setting = reactive({
      isShow_Tagview: true,
      isShow_Logo: true,
      isShow_Bread: true,
    })

    return {
      flag,
      dark,
      flush,
      page_setting,
      changeFlag,
      changeFlush,
      changeDark,
    }
  },
  {
    persist: {
      key: "page_setting",
      paths: ["page_setting"],
    },
  },
)

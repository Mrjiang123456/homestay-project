import { message } from "@/hooks/Element-plus"
import { useUserStore } from "@/stores/modules/user"
import { ElMessage } from "element-plus"
import axios from "axios"
import { error_code } from "./error-code"

const service = axios.create({
  baseURL: import.meta.env.VITE_BASE_URL,
  timeout: 5000,
})

// console.log(import.meta.env.VITE_BASE_URL)
// 请求拦截器
service.interceptors.request.use((config) => {
  const userStore = useUserStore()
  if (userStore.token) {
    config.headers.token = userStore.token
  }
  config.headers.username = localStorage.getItem("username") || ""
  return config
})
// 必须先拦截一下
// 响应拦截器
service.interceptors.response.use(
  (response) => {
    const userStore = useUserStore()
    if (response.data.code === "5008") {
      ElMessage.error(response.data.message)
      userStore.loginOut()
    }
    return response.data
  },
  (err) => {
    ElMessage.error(error_code(err.response.status) || "网络问题")
    return Promise.reject(new Error(err))
  },
)
export default service

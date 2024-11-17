import { createPinia } from "pinia"
// pinia持久化存储插件

import persistedState from "pinia-plugin-persistedstate"
export const piniaFunction = () => {
  const pinia = createPinia()
  pinia.use(persistedState)
  return pinia
}

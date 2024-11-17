//存token
export const SETTOKEN = (token: string) => {
  localStorage.setItem("TOKEN", token)
}
export const GETTOKEN = () => {
  return localStorage.getItem("TOKEN")
}
export const REMOVETOKEN = () => {
  localStorage.removeItem("TOKEN")
}
//暗黑主题
export const SETDARK = (flag: boolean) => {
  localStorage.setItem("DARK", JSON.stringify(flag))
}
export const GETDARK = () => {
  return JSON.parse(localStorage.getItem("DARK") as string)
}
//颜色主题
export const SETCOLOR = (color: string) => {
  localStorage.setItem("COLOR", color)
}
export const GETCOLOR = () => {
  return localStorage.getItem("COLOR")
}
// 用户信息
export const GETUSERINFO = () => {
  return JSON.parse(localStorage.getItem("userinfo") || "").userinfo
}

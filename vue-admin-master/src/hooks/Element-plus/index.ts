import { ElMessage, ElMessageBox, ElNotification } from "element-plus"
// 提示
export const message = (type: string, message: string) => {
  ElMessage({
    // @ts-expect-error 禁止飘红
    message,
    type,
    plain: true,
    duration: 1000,
  })
}
// 通知
export const notification = (title: string, message: string, type: string) => {
  ElNotification({
    //@ts-expect-error 禁止飘红
    title,
    message,
    type,
    duration: 1000,
    showClose: false,
  })
}
// 确认弹窗
export const messageBox = async (
  title: string,
  content: string,
  type: string,
) => {
  await new Promise((resolve, reject) => {
    //@ts-expect-error 禁止飘红
    ElMessageBox.confirm(content, title, {
      confirmButtonText: "确定",
      cancelButtonText: "取消",

      type,
    })
      .then(() => {
        resolve("ok")
      })
      .catch(() => {
        reject("用户点击取消！")
      })
  })
}

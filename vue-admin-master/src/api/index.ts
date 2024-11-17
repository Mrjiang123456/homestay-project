import request from "@/utils/request"

// 登录接口
export const loginApi = (data: any) => {
  return request.post("/login", data)
}

// 修改密码
export const updatePassword = (data: any) => {
  return request.put("/update-password", data)
}

// 修改密码
export const register = (data: any) => {
  return request.post("/register", data)
}

// 获取用户信息接口
export const getUserInfoApi = () => {
  return request.get("/user/info")
}

// 获取菜单接口
export const getMenuPermission = () => {
  return request.get("/permission/menu-tree")
}

// 获取用户分页
export const getUserInfoPage = (params: any) => {
  return request.get("/user/page", { params })
}

// 修改用户信息
export const addOrUpdateUserInfo = (data: any) => {
  return request.post("/user/add", data)
}

// 获取角色列表
export const getRoleInfoList = () => {
  return request.get("/role/list")
}

// 批量删除用户
export const deleteUserInfo = (data: any) => {
  return request.post("/user/delete", data)
}

// 获取角色分页
export const getRoleInfoPage = (params: any) => {
  return request.get("/role/page", { params })
}

// 删除角色信息
export const deleteRoleInfo = (data: any) => {
  return request.post("/role/delete", data)
}

// 增加或更新角色信息
export const addOrUpdateRoleInfo = (data: any) => {
  return request.post("/role/add", data)
}

// 获取权限树列表
export const getPermissionTree = () => {
  return request.get("/permission/menu-tree")
}

// 获取菜单列表
export const getPermissionList = () => {
  return request.get("/permission/list")
}

// 新增或者修改菜单
export const addOrUpdatePermissionInfo = (data: any) => {
  return request.post("/permission/add", data)
}

// 删除菜单信息
export const deletePermissionInfo = (data: any) => {
  return request.post("/permission/delete", data)
}

// 获取权限总数
export const getPermissionCount = () => {
  return request.get("/permission/count")
}

// 修改个人资料
export const updateUserInfo = (data: any) => {
  return request.put("/user/update", data)
}

// 获取评论接口
export const getCommentList = () => {
  return request.get("/comment/list")
}

// 添加评论接口
export const addCommentInfo = (data: any) => {
  return request.post("/comment/add", data)
}

// 删除评论接口
export const deleteCommentInfo = (params: any) => {
  return request.delete("/comment/delete", { params })
}

// 日志数据分页
export const getLogInfoPage = (params: any) => {
  return request.get("/log/page", { params })
}

// 日志数据删除
export const deleteLogInfo = (data: any) => {
  return request.post("/log/delete", data)
}

// 日志历史登录
export const getLoginList = (params: any) => {
  return request.get("/log/login-list", { params })
}

// 日志活跃趋势
export const getActiveTrend = (params: any) => {
  return request.get("/log/active-trend", { params })
}

// 户型与均价的关系
export const getAnalyze1 = () => {
  return request.get("/analysis/analyze1")
}

// 区域民宿数量分析
export const getAnalyze2 = () => {
  return request.get("/analysis/analyze2")
}

// 评分与消费的关系
export const getAnalyze3 = () => {
  return request.get("/analysis/analyze3")
}

// 价格与消费的关系
export const getAnalyze4 = () => {
  return request.get("/analysis/analyze4")
}

// 收藏与消费的关系
export const getAnalyze5 = () => {
  return request.get("/analysis/analyze5")
}

// 民宿标签词云
export const getAnalyze6 = () => {
  return request.get("/analysis/analyze6")
}

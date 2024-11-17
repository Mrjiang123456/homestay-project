type error_code_type = {
  [key: string]: string
}
export const error_code = (code: string) => {
  const errorCode: error_code_type = {
    "400": "请求异常",
    "401": "认证失败，无法访问系统资源",
    "403": "拒绝访问",
    "404": "请求地址错误",
    "405": "请求方式错误",
    "408": "请求超时",
    "410": "请求资源不存在",
    "413": "请求负载",
    "500": "服务器内部错误",
    "502": "服务器网管错误",
    "503": "服务器暂时无法使用",
    "504": "服务响应超时",
    "505": "HTTP版本不受支持",
  }
  return errorCode[code]
}

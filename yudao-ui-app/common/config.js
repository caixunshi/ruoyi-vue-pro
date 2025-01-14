import wechat from './wechat'

module.exports = {
  //后端接口地址
  baseUrl: 'http://192.168.1.111:48080/app-api',
  //baseUrl: 'http://api-dashboard.yudao.iocoder.cn/app-api',
  // 超时
  timeout: 30000,
  // 禁用 Cookie 等信息
  withCredentials: false,
  header: {
    //租户ID
    'tenant-id': wechat.getUrlParams("state")
  }
}

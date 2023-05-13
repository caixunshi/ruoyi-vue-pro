//请求工具参考https://ext.dcloud.net.cn/plugin?id=392
const { http} = uni.$u
// 获取滚动图数据
export const getBannerData = params => http.get('/market/banner/list', { params })
// 获取店铺列表数据
export const getNoticeData = params => http.get('/notice', { params })

// 获取店铺列表数据
export const getStoreList = params => http.get('/store/page', { params })

// 获取用户openID
export const getOpenId = params => http.get('/wx/getOpenId', { params })

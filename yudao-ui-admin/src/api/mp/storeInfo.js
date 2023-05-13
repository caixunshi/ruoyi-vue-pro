import request from '@/utils/request'

// 创建门店信息
export function createStoreInfo(data) {
  return request({
    url: '/mp/store-info/create',
    method: 'post',
    data: data
  })
}

// 更新门店信息
export function updateStoreInfo(data) {
  return request({
    url: '/mp/store-info/update',
    method: 'put',
    data: data
  })
}

// 删除门店信息
export function deleteStoreInfo(id) {
  return request({
    url: '/mp/store-info/delete?id=' + id,
    method: 'delete'
  })
}

// 获得门店信息
export function getStoreInfo(id) {
  return request({
    url: '/mp/store-info/get?id=' + id,
    method: 'get'
  })
}

// 获得门店信息分页
export function getStoreInfoPage(query) {
  return request({
    url: '/mp/store-info/page',
    method: 'get',
    params: query
  })
}

// 导出门店信息 Excel
export function exportStoreInfoExcel(query) {
  return request({
    url: '/mp/store-info/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}

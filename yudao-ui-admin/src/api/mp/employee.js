import request from '@/utils/request'

// 创建员工信息
export function createEmployee(data) {
  return request({
    url: '/mp/employee/create',
    method: 'post',
    data: data
  })
}

// 更新员工信息
export function updateEmployee(data) {
  return request({
    url: '/mp/employee/update',
    method: 'put',
    data: data
  })
}

// 删除员工信息
export function deleteEmployee(id) {
  return request({
    url: '/mp/employee/delete?id=' + id,
    method: 'delete'
  })
}

// 获得员工信息
export function getEmployee(id) {
  return request({
    url: '/mp/employee/get?id=' + id,
    method: 'get'
  })
}

// 获得员工信息分页
export function getEmployeePage(query) {
  return request({
    url: '/mp/employee/page',
    method: 'get',
    params: query
  })
}

// 导出员工信息 Excel
export function exportEmployeeExcel(query) {
  return request({
    url: '/mp/employee/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}

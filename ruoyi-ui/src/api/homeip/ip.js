import request from '@/utils/request'

// 查询IP地址列表
export function listIp(query) {
  return request({
    url: '/homeip/ip/list',
    method: 'get',
    params: query
  })
}

// 查询IP地址详细
export function getIp(id) {
  return request({
    url: '/homeip/ip/' + id,
    method: 'get'
  })
}

// 新增IP地址
export function addIp(data) {
  return request({
    url: '/homeip/ip',
    method: 'post',
    data: data
  })
}

// 修改IP地址
export function updateIp(data) {
  return request({
    url: '/homeip/ip',
    method: 'put',
    data: data
  })
}

// 删除IP地址
export function delIp(id) {
  return request({
    url: '/homeip/ip/' + id,
    method: 'delete'
  })
}

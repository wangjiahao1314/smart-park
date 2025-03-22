import request from '@/utils/request.js'
/**
 * 分页查询
 * @param {*} params 
 * @returns 
 */
export function getPageQuery(params) {
  return request({
    url: "/tenant/list",
    method: "get",
    params
  })
}

/**
 * 新增
 * @param {*} data 
 * @returns 
 */
export function addTenantry(data) {
  return request({
    url: "/tenant/add",
    method: "post",
    data
  })
}

/**
 * 查询详细
 * @param {*} tenantId id
 * @returns 
 */
export function inquireTenantry(tenantId) {

  return request({
    url: `/tenant/${tenantId}`,
    method: "get",

  })
}

/**
 * 编辑
 * @param {*} params 
 * @returns 
 */
export function editTenantry(data) {
  return request({
    url: "/tenant",
    method: "put",
    data
  })
}

/**
 * 删除
 * @param {*} data 
 * @returns 
 */
export function deleteTenantry(data) {
  let tenantIds = Array.isArray(data) ? data.join(',') : data
  return request({
    url: `/tenant/${tenantIds}`,
    method: "delete",

  })
}
import request from '@/utils/request.js'
/**
 * 分页查询
 * @param {*} params 
 * @returns 
 */
export function getPageQuery(params) {
  return request({
    url: "/zone/list",
    method: "get",
    params
  })
}

/**
 * 新增
 * @param {*} data 
 * @returns 
 */
export function addPark(data) {
  return request({
    url: "/zone/add",
    method: "post",
    data
  })
}


/**
 * 查询详细
 * @param {*} zoneId id
 * @returns 
 */
export function inquirePark(zoneId) {

  return request({
    url: `/zone/${zoneId}`,
    method: "get",

  })
}


/**
 * 编辑
 * @param {*} params 
 * @returns 
 */
export function editPark(data) {
  return request({
    url: "/zone",
    method: "put",
    data
  })
}



/**
 * 删除
 * @param {*} data 
 * @returns 
 */
export function deletePark(data) {
  let zoneIds = Array.isArray(data) ? data.join(',') : data
  return request({
    url: `/zone/${zoneIds}`,
    method: "delete",
  })
}
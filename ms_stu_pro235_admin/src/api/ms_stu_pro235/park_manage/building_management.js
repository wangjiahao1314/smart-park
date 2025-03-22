import request from '@/utils/request.js'
/**
 * 分页查询
 * @param {*} params 
 * @returns 
 */
export function getPageQuery(params) {
  return request({
    url: "/building/list",
    method: "get",
    params
  })
}

/**
 * 获得园区下拉框
 * @param {*} data 
 * @returns 
 */
export function getParkSelect(data) {
  return request({
    url: "/zone/select",
    method: "get",
    data
  })
}

/**
 * 新增
 * @param {*} data 
 * @returns 
 */
export function addBuillding(data) {
  return request({
    url: "/building/add",
    method: "post",
    data
  })
}

/**
 * 查询详细
 * @param {*} buildingId id
 * @returns 
 */
export function inquireBuilding(buildingId) {

  return request({
    url: `/building/${buildingId}`,
    method: "get",

  })
}

/**
 * 编辑
 * @param {*} params 
 * @returns 
 */
export function editBuilding(data) {
  return request({
    url: "/building",
    method: "put",
    data
  })
}

/**
 * 删除
 * @param {*} data 
 * @returns 
 */
export function deleteBuilding(data) {
  let buildingIds = Array.isArray(data) ? data.join(',') : data
  return request({
    url: `/building/${buildingIds}`,
    method: "delete",
  })
}

// 楼层模块

/**
 * 分页查询
 * @param {*} params 
 * @returns 
 */
export function getFloorPageQuery(params) {
  return request({
    url: "/floor/select",
    method: "get",
    params
  })
}

/**
 * 新增
 * @param {*} data 
 * @returns 
 */
export function addFloor(data) {
  return request({
    url: "/floor/add",
    method: "post",
    data
  })
}

/**
 * 删除
 * @param {*} data 
 * @returns 
 */
export function deleteFloor(data) {
  let floorIds = Array.isArray(data) ? data.join(',') : data
  return request({
    url: `/floor/${floorIds}`,
    method: "delete",
  })
}


/**
 * 编辑
 * @param {*} params 
 * @returns 
 */
export function editFloor(data) {
  return request({
    url: "/floor",
    method: "put",
    data
  })
}


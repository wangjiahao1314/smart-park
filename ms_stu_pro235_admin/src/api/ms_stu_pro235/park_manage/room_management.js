import request from '@/utils/request.js'
/**
 * 分页查询 (列表)
 * @param {*} params 
 * @returns 
 */
export function getListPageQuery(params) {
  return request({
    url: "/room/list",
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
 * 分页查询 (房态)
 * @param {*} params 
 * @returns 
 */
export function getRoomPageQuery(params) {
  return request({
    url: "/room/room",
    method: "get",
    params
  })
}

/**
 * 编辑上架状态
 * @param {*} data 
 * @returns 
 */
export function editsShelfState(params) {
  return request({
    url: "/room/shelf",
    method: "put",
    params
  })
}



/**
 * 删除
 * @param {*} data 
 * @returns 
 */
export function deleteRoom(data) {
  let roomIds = Array.isArray(data) ? data.join(',') : data
  return request({
    url: `/room/${roomIds}`,
    method: "delete",
  })
}

/**
 * 查询房间日志
 * @param {*} roomId 
 * @returns 
 */
export function getRoomLogs(roomId) {
  return request({
    url: `/room/record/${roomId}`,
    method: "get",
  })
}


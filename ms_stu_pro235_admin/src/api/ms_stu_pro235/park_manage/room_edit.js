import request from '@/utils/request.js'

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
 * 获得楼宇下拉框数据
 * @param {*}  
 * @returns 
 */
export function getBuildingSelect() {
  return request({
    url: "/building/select",
    method: "get",

  })
}

/**
 * 获得楼层下拉框数据
 * @param {*}  
 * @returns 
 */
export function getFloorSelect() {
  return request({
    url: "/floor/select",
    method: "get",

  })
}

/**
 * 查询房间信息详情
 * @param {*} roomId 
 * @returns 
 */
export function inquireRoomDetails(roomId) {
  return request({
    url: `/room/record/${roomId}`,
    method: "get",
  })
}

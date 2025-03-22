import request from '@/utils/request'

/**
 * 分页查询房间信息
 * @params {*} params 
 * @returns 
 */
export function pageList(params) {
    return request({
        url: '/room/list',
        method: 'GET',
        params
    })
}
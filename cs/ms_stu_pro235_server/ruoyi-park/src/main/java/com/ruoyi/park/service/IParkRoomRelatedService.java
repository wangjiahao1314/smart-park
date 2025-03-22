package com.ruoyi.park.service;

import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.park.domain.param.room.*;
import com.ruoyi.park.domain.vo.room.*;

import java.util.List;

/**
 * @author hezining
 * @date 2025/3/9 11:02
 * @description: 房间相关的服务层接口
 */
public interface IParkRoomRelatedService {

    /**
     * 批量删除房间
     *
     * @param roomIds
     * @return
     */
    public Boolean removeRoom(Long[] roomIds);

    /**
     * 分页查询房间信息（列表）
     *
     * @param param
     */
    public TableDataInfo<RoomListVo> getPageRoomList(RoomListParam param);

    /**
     * 查询房间房态列表
     *
     * @param param
     * @return
     */
    public RoomStatusInfoVo roomStatusList(RoomStatusListParam param);

    /**
     * 编辑上架状态
     *
     * @param param
     * @return
     */
    public Boolean editShelfTime(EditShelfStatusParam param);

    /**
     * 分页获取租户端我的房间列表
     *
     * @param param
     * @return
     */
    public TableDataInfo<List<TntRoomListVo>> getTntRoomList(TntRoomListParam param, Long userId);

    /**
     * 申请退租
     *
     * @param param
     * @param userId
     * @return
     */
    public int terminateRental(TntTerminateRentalParam param, Long userId);

    /**
     * 查询房间记录信息
     *
     * @param roomId
     * @return
     */
    List<RoomRecordsVo> getRoomRecords(Long roomId);
}

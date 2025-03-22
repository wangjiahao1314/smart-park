package com.ruoyi.park.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.park.domain.entity.ParkRoom;
import com.ruoyi.park.domain.param.room.RoomListParam;
import com.ruoyi.park.domain.vo.room.RoomListVo;
import com.ruoyi.park.domain.vo.room.RoomRecordsVo;
import com.ruoyi.park.domain.vo.room.TntRoomListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hezining
 * @date 2025/3/7 10:18
 * @description: 房间持久层接口
 */
public interface ParkRoomMapper extends BaseMapper<ParkRoom> {
    /**
     * 通过楼宇ID判断是否存在对应房间
     *
     * @param buildingIds
     * @return
     */
    default boolean existRoomByBuildingIds(Long[] buildingIds) {
        LambdaQueryWrapper<ParkRoom> lqw = new LambdaQueryWrapper<>();
        lqw.in(ParkRoom::getBuildingId, buildingIds);
        return exists(lqw);
    }

    /**
     * 通过楼层ID判断是否存在对应房间
     *
     * @param floorIds
     * @return
     */
    default boolean existRoomByFloorIds(Long[] floorIds) {
        LambdaQueryWrapper<ParkRoom> lqw = new LambdaQueryWrapper<>();
        lqw.in(ParkRoom::getFloorId, floorIds);
        return exists(lqw);
    }

    /**
     * 判断房间名称的重复性
     * (一栋房子的房间名称唯一)
     *
     * @param buildingId
     * @param roomName
     * @return
     */
    default boolean checkRoomNameDuplicate(Long buildingId, String roomName, Long roomId) {
        LambdaQueryWrapper<ParkRoom> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ParkRoom::getBuildingId, buildingId)
                .ne(roomId != null, ParkRoom::getRoomId, roomId)
                .eq(ParkRoom::getRoomName, roomName);
        return exists(lqw);
    }

    /**
     * 分页查询房间信息
     *
     * @param page
     * @param wrapper
     * @return
     */
    Page<RoomListVo> pageSelectRoomList(@Param("page") Page<RoomListVo> page, @Param(Constants.WRAPPER) Wrapper<RoomListVo> wrapper);

    /**
     * 分页获取租户端我的房间列表
     *
     * @param tntRoomListVoPage
     * @param qw
     * @return
     */
    Page<TntRoomListVo> getTntRoomList(@Param("page") Page<TntRoomListVo> tntRoomListVoPage, @Param(Constants.WRAPPER) QueryWrapper<TntRoomListVo> qw);

    List<RoomRecordsVo> getRoomRecords(Long roomId);
}

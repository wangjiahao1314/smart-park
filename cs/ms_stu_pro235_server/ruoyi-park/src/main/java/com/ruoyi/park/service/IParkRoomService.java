package com.ruoyi.park.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.park.domain.entity.ParkRoom;
import com.ruoyi.park.domain.param.room.AddRoomParam;
import com.ruoyi.park.domain.param.room.EditRoomParam;
import com.ruoyi.park.domain.param.room.RoomListParam;
import com.ruoyi.park.domain.param.room.RoomStatusListParam;
import com.ruoyi.park.domain.vo.room.RoomInfoVo;
import com.ruoyi.park.domain.vo.room.RoomStatusInfoVo;

import java.util.List;

/**
 * @author hezining
 * @date 2025/3/7 10:18
 * @description: 房间服务层接口
 */
public interface IParkRoomService extends IService<ParkRoom> {

    /**
     * 新增房间
     *
     * @param param
     * @return
     */
    public Boolean addRoom(AddRoomParam param);


    /**
     * 编辑房间信息
     *
     * @param param
     * @return
     */
    public int editRoom(EditRoomParam param);

    /**
     * 根据房间ID查询房间详情
     *
     * @param roomId
     * @return
     */
    public RoomInfoVo getRoomInfo(Long roomId);


}

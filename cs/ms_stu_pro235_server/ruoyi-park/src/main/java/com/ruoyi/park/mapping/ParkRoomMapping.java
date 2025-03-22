package com.ruoyi.park.mapping;

import com.ruoyi.park.domain.entity.ParkRoom;
import com.ruoyi.park.domain.param.room.AddRoomParam;
import com.ruoyi.park.domain.param.room.EditRoomParam;
import com.ruoyi.park.domain.vo.room.RoomInfoVo;
import com.ruoyi.park.domain.vo.room.RoomListVo;
import com.ruoyi.park.domain.vo.room.RoomStatusRoomVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author hezining
 * @date 2025/3/9 18:17
 * @description: 房间映射接口
 */

@Mapper
public interface ParkRoomMapping {

    public ParkRoomMapping INSTANCE = Mappers.getMapper(ParkRoomMapping.class);

    ParkRoom to(EditRoomParam param);


    ParkRoom to(AddRoomParam param);

    RoomInfoVo toVo(ParkRoom room);

    RoomListVo toListVo(RoomListVo room);

    List<RoomStatusRoomVo> toVoList(List<ParkRoom> room);

}

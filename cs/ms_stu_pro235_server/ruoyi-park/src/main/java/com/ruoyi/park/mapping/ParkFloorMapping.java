package com.ruoyi.park.mapping;

import com.ruoyi.park.domain.entity.ParkFloor;
import com.ruoyi.park.domain.param.floor.AddFloorParam;
import com.ruoyi.park.domain.param.floor.EditFloorParam;
import com.ruoyi.park.domain.vo.floor.FloorSelectVo;
import com.ruoyi.park.domain.vo.room.RoomStatusFloorVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author hezining
 * @date 2025/3/8 10:16
 * @description: 楼层映射接口
 */

@Mapper
public interface ParkFloorMapping {

    public ParkFloorMapping INSTANCE = Mappers.getMapper(ParkFloorMapping.class);

    ParkFloor to(AddFloorParam param);

    ParkFloor to(EditFloorParam param);

    List<FloorSelectVo> toSelectVo(List<ParkFloor> floor);

    List<RoomStatusFloorVo> toVoList(List<ParkFloor> floors);
}

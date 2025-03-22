package com.ruoyi.park.mapping;

import com.ruoyi.park.domain.entity.ParkBuilding;
import com.ruoyi.park.domain.param.building.AddBuildingParam;
import com.ruoyi.park.domain.param.building.EditBuildingParam;
import com.ruoyi.park.domain.vo.building.BuildingInfoVo;
import com.ruoyi.park.domain.vo.building.BuildingSelectVo;
import com.ruoyi.park.domain.vo.room.RoomStatusBuildingVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author hezining
 * @date 2025/3/6 17:02
 * @description: 楼宇映射接口
 */

@Mapper
public interface ParkBuildingMapping {

    ParkBuildingMapping INSTANCE = Mappers.getMapper(ParkBuildingMapping.class);

    ParkBuilding toBuilding(AddBuildingParam param);

    ParkBuilding toBuilding(EditBuildingParam param);

    BuildingInfoVo toVo(ParkBuilding building);

    List<BuildingSelectVo> toSelVoList(List<ParkBuilding> building);

    List<RoomStatusBuildingVo> toVoList(List<ParkBuilding> buildingList);
}

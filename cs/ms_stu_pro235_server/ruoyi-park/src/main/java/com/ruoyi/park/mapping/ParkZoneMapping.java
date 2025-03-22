package com.ruoyi.park.mapping;





import com.ruoyi.park.domain.entity.ParkZone;
import com.ruoyi.park.domain.param.zone.AddZoneParam;
import com.ruoyi.park.domain.param.zone.EditZoneParam;
import com.ruoyi.park.domain.vo.zone.ZoneListVo;
import com.ruoyi.park.domain.vo.zone.ZoneSelectVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author hezining
 * @date 2025/3/4 10:56
 * @description: 园区映射接口
 */

@Mapper
public interface ParkZoneMapping {

    public ParkZoneMapping INSTANCE = Mappers.getMapper(ParkZoneMapping.class);

    ParkZone toZone(AddZoneParam param);

    ParkZone toZone(EditZoneParam param);

    List<ZoneSelectVo> toSelectVo(List<ParkZone> zone);

    List<ZoneListVo> toListVo(List<ParkZone> zone);
}

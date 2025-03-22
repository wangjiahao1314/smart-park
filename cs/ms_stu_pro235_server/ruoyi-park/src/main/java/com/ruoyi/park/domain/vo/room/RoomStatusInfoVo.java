package com.ruoyi.park.domain.vo.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author hezining
 * @date 2025/3/10 16:02
 * @description: 房态模式房间列表信息
 */

@Data
@Accessors(chain = true)
@ApiModel(description = "房态视图对象")
public class RoomStatusInfoVo {

    @ApiModelProperty(value = "区域ID", example = "1", required = true)
    private Long zoneId;

    @ApiModelProperty(value = "区域名称", example = "Zone A", required = true)
    private String zoneName;

    @ApiModelProperty(value = "建筑列表", required = true)
    private List<RoomStatusBuildingVo> buildingList;
}

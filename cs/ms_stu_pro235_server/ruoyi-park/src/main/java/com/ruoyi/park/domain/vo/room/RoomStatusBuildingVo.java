package com.ruoyi.park.domain.vo.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author hezining
 * @date 2025/3/10 16:15
 * @description: 房态模式中园区对应的楼宇信息
 */

@Data
@ApiModel(description = "楼宇视图对象")
public class RoomStatusBuildingVo {

    @ApiModelProperty(value = "楼宇ID", example = "12345", required = true)
    private Long buildingId;

    @ApiModelProperty(value = "楼宇名称", example = "Main Building", required = true)
    private String buildingName;

    @ApiModelProperty(value = "楼层列表", required = true)
    private List<RoomStatusFloorVo> floorList;
}

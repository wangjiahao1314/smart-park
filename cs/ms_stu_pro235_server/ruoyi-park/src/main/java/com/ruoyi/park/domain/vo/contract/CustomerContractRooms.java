package com.ruoyi.park.domain.vo.contract;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerContractRooms {

    @ApiModelProperty("房间id")
    private Long roomId;
    @ApiModelProperty("园区")
    private Long zoneId;
    @ApiModelProperty("楼栋id")
    private Long buildingId;
    @ApiModelProperty("楼层id")
    private Long floorId;
    @ApiModelProperty("房间面积")
    private BigDecimal squareMeasure;
    @ApiModelProperty("房间名称")
    private String roomName;
    @ApiModelProperty("园区名称")
    private String zoneName;
    @ApiModelProperty("楼栋名称")
    private String buildingName;
    @ApiModelProperty("楼层名称")
    private String floorName;

}

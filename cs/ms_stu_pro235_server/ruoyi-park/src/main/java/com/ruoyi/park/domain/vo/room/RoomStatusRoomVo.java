package com.ruoyi.park.domain.vo.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hezining
 * @date 2025/3/10 16:27
 * @description: 房态模式中楼层所对应房间VO
 */

@Data
@ApiModel(description = "房间视图对象")
public class RoomStatusRoomVo {

    @ApiModelProperty(value = "房间ID", example = "101", required = true)
    private Long roomId;

    @ApiModelProperty(value = "房间名称", example = "Room A", required = true)
    private String roomName;

    @ApiModelProperty(value = "房间面积（平方米）", example = "50.5", required = true)
    private Double squareMeasure;

    @ApiModelProperty(value = "租金", example = "2000.00", required = true)
    private Double rent;

    @ApiModelProperty(value = "租金类型（0(元/㎡/天) 1(元/㎡/月) 2(元/天) 3(元/月)）", required = true, example = "0")
    private String rentType;

    @ApiModelProperty(value = "租赁状态（0在租 1未租）", example = "0", required = true)
    private String rentalStatus;

    @ApiModelProperty(value = "空闲天数", example = "30", required = false)
    private Integer idleDays;
}

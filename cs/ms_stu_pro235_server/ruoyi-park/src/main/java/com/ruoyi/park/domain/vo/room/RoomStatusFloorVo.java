package com.ruoyi.park.domain.vo.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author hezining
 * @date 2025/3/10 16:20
 * @description: 房态模式中楼宇对应楼层VO
 */


@Data
@ApiModel(description = "楼层视图对象")
public class RoomStatusFloorVo {

    @ApiModelProperty(value = "楼层ID", example = "67890", required = true)
    private Long floorId;

    @ApiModelProperty(value = "楼层名称", example = "Floor 1", required = true)
    private String floorName;

    @ApiModelProperty(value = "房间列表", required = true)
    private List<RoomStatusRoomVo> roomList;
}

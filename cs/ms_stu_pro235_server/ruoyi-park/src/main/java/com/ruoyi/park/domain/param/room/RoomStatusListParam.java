package com.ruoyi.park.domain.param.room;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author hezining
 * @date 2025/3/10 15:58
 * @description: 房间房态列表参数
 */

@Data
public class RoomStatusListParam {

    @ApiModelProperty(value = "园区ID", required = true, example = "1")
    @Size(max = 50, message = "园区ID长度不能超过50个字符")
    @NotNull(message = "园区ID不能为空")
    private Long zoneId;

    @ApiModelProperty(value = "楼宇名称", required = false, example = "楼1")
    @Size(max = 100, message = "楼宇名称长度不能超过100个字符")
    private String buildingName;

    @ApiModelProperty(value = "房间名称", required = false, example = "A201")
    @Size(max = 100, message = "房间名称长度不能超过100个字符")
    private String roomName;

    @ApiModelProperty(value = "租赁状态（0在租 1未租）", required = false, example = "0")
    @Pattern(regexp = "^[01]$", message = "租赁状态只能是0或1")
    private String rentalStatus;

}

package com.ruoyi.park.domain.param.room;

import com.ruoyi.park.domain.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author hezining
 * @date 2025/3/9 20:19
 * @description: 分页查询房间信息参数
 */
@ApiModel("分页查询房间信息参数")
@Data
@EqualsAndHashCode(callSuper = true)
public class RoomListParam extends PageParam {

    @ApiModelProperty(value = "园区ID", required = false, example = "1")
    @Size(max = 50, message = "园区ID长度不能超过50个字符")
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

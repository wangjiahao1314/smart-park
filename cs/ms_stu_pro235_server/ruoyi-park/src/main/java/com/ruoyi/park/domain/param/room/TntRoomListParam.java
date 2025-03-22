package com.ruoyi.park.domain.param.room;

import com.ruoyi.park.domain.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author hezining
 * @date 2025/3/12 21:44
 * @description: 查询我的房间列表参数
 */


@Data
@ApiModel(description = "租户我的房间列表查询参数")
public class TntRoomListParam extends PageParam {

    @ApiModelProperty(value = "园区名称", example = "Zone A", required = false)
    @Size(max = 50, message = "园区名称长度不能超过50个字符")
    private String zoneName;

    @ApiModelProperty(value = "楼宇名称", example = "Building 1", required = false)
    @Size(max = 100, message = "楼宇名称长度不能超过100个字符")
    private String buildingName;

    @ApiModelProperty(value = "房间名称", example = "Room 101", required = false)
    @Size(max = 100, message = "房间名称长度不能超过100个字符")
    private String roomName;
}

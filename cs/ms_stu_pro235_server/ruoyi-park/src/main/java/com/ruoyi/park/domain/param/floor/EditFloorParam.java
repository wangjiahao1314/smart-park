package com.ruoyi.park.domain.param.floor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hezining
 * @date 2025/3/8 12:39
 * @description: 编辑楼层信息参数
 */

@Data
public class EditFloorParam {

    @ApiModelProperty(value = "楼层ID", required = true)
    @NotNull(message = "楼层ID")
    private Long floorId;

    @ApiModelProperty(value = "楼层编号", required = true)
    @NotBlank(message = "floorNum 不能为空")
    private String floorNum;

    @ApiModelProperty(value = "楼层名称", required = true)
    @NotBlank(message = "floorName 不能为空")
    private String floorName;
}

package com.ruoyi.park.domain.param.floor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hezining
 * @date 2025/3/8 10:06
 * @description: 新增楼层参数
 */

@Data
@ApiModel(description = "新增楼层参数")
public class AddFloorParam {

    @ApiModelProperty(value = "楼宇ID", required = true)
    @NotNull(message = "buildingId 不能为空")
    private Long buildingId;


    @ApiModelProperty(value = "楼层编号", required = true)
    @NotBlank(message = "floorNum 不能为空")
    private String floorNum;

    @ApiModelProperty(value = "楼层名称", required = true)
    @NotBlank(message = "floorName 不能为空")
    private String floorName;
}
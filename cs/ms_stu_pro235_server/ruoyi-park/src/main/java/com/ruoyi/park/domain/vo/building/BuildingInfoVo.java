package com.ruoyi.park.domain.vo.building;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author hezining
 * @date 2025/3/7 12:01
 * @description: 楼宇详情VO
 */

@Data
@ApiModel(description = "楼宇详情视图对象")
public class BuildingInfoVo {

    @ApiModelProperty(value = "楼宇ID", required = true, example = "101")
    @NotNull(message = "楼宇ID不能为空")
    private Long buildingId;

    @ApiModelProperty(value = "楼宇名称", required = true, example = "科技楼")
    @NotBlank(message = "楼宇名称不能为空")
    @Size(max = 50, message = "楼宇名称长度不能超过50个字符")
    private String buildingName;

    @ApiModelProperty(value = "备注", example = "这是一个备注信息")
    @Size(max = 200, message = "备注长度不能超过200个字符")
    private String remark;
}
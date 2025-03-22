package com.ruoyi.park.domain.param.building;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author hezining
 * @date 2025/3/6 16:06
 * @description: 新增楼宇参数
 */

@Data
@ApiModel(description = "新增楼宇参数")
public class AddBuildingParam {

    @NotNull(message = "园区ID不能为空")
    @ApiModelProperty(value = "园区ID", required = true, example = "123456", position = 0)
    private Long zoneId;

    @NotBlank(message = "楼宇名称不能为空")
    @Size(max = 255, message = "楼宇名称长度不能超过255个字符")
    @ApiModelProperty(value = "楼宇名称", required = true, example = "科技大厦", position = 1)
    private String buildingName;

    @ApiModelProperty(value = "备注信息", example = "这是关于该楼宇的一些备注信息", position = 2)
    @Size(max = 500, message = "备注信息长度不能超过500个字符")
    private String remark;
}

package com.ruoyi.park.domain.param.zone;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author hezining
 * @date 2025/3/3 20:32
 * @description: 新增园区参数
 */

@Data
@ApiModel(value = "AddZoneParam", description = "新增园区请求参数")
public class AddZoneParam {

    @ApiModelProperty(value = "园区名称", required = true, example = "华东区", notes = "必填项，长度不超过50个字符")
    @NotBlank(message = "园区名称不能为空")
    private String zoneName;

    @ApiModelProperty(value = "备注信息", required = false, example = "此区域为测试用途", notes = "选填项，长度不超过200个字符")
    private String remark;
}

package com.ruoyi.park.domain.param.zone;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hezining
 * @date 2025/3/4 18:14
 * @description: 修改园区参数
 */

@Data
@ApiModel(description = "编辑区域参数对象")
public class EditZoneParam {

    @ApiModelProperty(value = "区域ID", example = "1", required = true)
    private Long zoneId;

    @ApiModelProperty(value = "区域名称", example = "区域A", required = true)
    private String zoneName;

    @ApiModelProperty(value = "备注", example = "123", required = true)
    private String remark;
}
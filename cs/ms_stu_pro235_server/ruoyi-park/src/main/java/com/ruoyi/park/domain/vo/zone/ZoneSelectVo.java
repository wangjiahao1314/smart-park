package com.ruoyi.park.domain.vo.zone;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hezining
 * @date 2025/3/4 17:52
 * @description: 园区下拉框Vo
 */

@ApiModel(value = "ZoneSelectVo", description = "园区下拉框传输对象")
@Data
public class ZoneSelectVo {

    @ApiModelProperty(value = "园区ID", example = "1", required = true)
    private Long zoneId;

    @ApiModelProperty(value = "园区名称", example = "Zone A", required = true)
    private String zoneName;
}

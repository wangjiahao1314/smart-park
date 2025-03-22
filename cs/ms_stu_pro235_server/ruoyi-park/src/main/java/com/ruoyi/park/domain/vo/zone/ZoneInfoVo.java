package com.ruoyi.park.domain.vo.zone;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hezining
 * @date 2025/3/5 21:12
 * @description: 根据园区Id获取园区详情Vo
 */

@Data
@ApiModel(description = "区域信息视图对象")
public class ZoneInfoVo {

    @ApiModelProperty(value = "区域ID", example = "123456", required = true)
    private Long zoneId;

    @ApiModelProperty(value = "区域名称", example = "North Zone", required = true)
    private String zoneName;

    @ApiModelProperty(value = "备注信息", example = "This is a remark.", required = false)
    private String remark;
}

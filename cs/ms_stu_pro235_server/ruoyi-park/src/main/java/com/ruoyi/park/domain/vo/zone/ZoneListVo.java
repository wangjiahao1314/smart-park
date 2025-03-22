package com.ruoyi.park.domain.vo.zone;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author hezining
 * @date 2025/3/5 22:07
 * @description: 分页查询园区列表Vo
 */

@Data
@ApiModel(description = "区域列表视图对象")
public class ZoneListVo {

    @ApiModelProperty(value = "园区ID", example = "zoneID", required = true)
    private Long zoneId;

    @ApiModelProperty(value = "园区名称", example = "North Zone", required = true)
    private String zoneName;

    @ApiModelProperty(value = "备注信息", example = "This is a remark.", required = false)
    private String remark;

    @ApiModelProperty(value = "创建时间", example = "2023-03-05 22:39:00", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "创建者", example = "admin", required = true)
    private String createBy;
}

package com.ruoyi.park.domain.vo.floor;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author hezining
 * @date 2025/3/8 14:32
 * @description: 下拉框查询楼层VO
 */

@Data
@ApiModel(value = "FloorSelectVo", description = "下拉框查询楼层VO")
public class FloorSelectVo {

    @ApiModelProperty(value = "楼层ID", required = true)
    @NotNull(message = "floorId 不能为空")
    private Long floorId;

    @ApiModelProperty(value = "楼层", required = true)
    @NotNull(message = "floorNum 不能为空")
    private String floorNum;

    @ApiModelProperty(value = "楼层名称", required = true)
    @NotNull(message = "floorName 不能为空")
    private String floorName;

    @ApiModelProperty(value = "创建时间", example = "2023-03-05 22:39:00", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "创建者", example = "admin", required = true)
    private String createBy;


}
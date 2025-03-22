package com.ruoyi.park.domain.vo.building;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author hezining
 * @date 2025/3/7 16:19
 * @description: 分页查询楼宇VO
 */


@Data
@ApiModel(description = "楼宇列表VO")
public class BuildingListVo {

    @ApiModelProperty(value = "楼宇ID", required = true, example = "101")
    @NotNull(message = "楼宇ID不能为空")
    private Long buildingId;

    @ApiModelProperty(value = "区域ID", required = true, example = "1")
    @NotNull(message = "区域ID不能为空")
    private Long zoneId;

    @ApiModelProperty(value = "园区名称", required = true, example = "大学城")
    @NotBlank(message = "园区名称不能为空")
    private String zoneName;

    @ApiModelProperty(value = "楼宇名称", required = true, example = "科技楼")
    @NotBlank(message = "楼宇名称不能为空")
    private String buildingName;

    @ApiModelProperty(value = "创建时间", required = true, example = "2025-03-07 12:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "创建人", required = true, example = "admin")
    @NotBlank(message = "创建人不能为空")
    private String createBy;

    @ApiModelProperty(value = "备注", example = "这是一个备注信息")
    private String remark;
}

package com.ruoyi.park.domain.vo.building;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author hezining
 * @date 2025/3/7 20:45
 * @description: 楼宇下拉框VO
 */


@Data
@ApiModel(description = "楼宇下拉框视图对象")
public class BuildingSelectVo {

    /**
     * 建筑ID
     */
    @ApiModelProperty(value = "楼宇ID", required = true, example = "12345")
    @NotNull(message = "楼宇ID不能为空")
    private Long buildingId;

    /**
     * 建筑名称
     */
    @ApiModelProperty(value = "楼宇名称", required = true, example = "科技大厦")
    @NotNull(message = "楼宇名称不能为空")
    @Size(max = 100, message = "楼宇名称长度不能超过100个字符")
    private String buildingName;

}

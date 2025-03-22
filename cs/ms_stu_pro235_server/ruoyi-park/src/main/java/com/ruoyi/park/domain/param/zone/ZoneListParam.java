package com.ruoyi.park.domain.param.zone;

import com.ruoyi.park.domain.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author hezining
 * @date 2025/3/5 22:57
 * @description: 园区分页查询参数
 */

@Data
@ApiModel("园区分页查询参数")
@EqualsAndHashCode(callSuper = true)
public class ZoneListParam extends PageParam {

    @ApiModelProperty(
            value = "园区名称",
            example = "五一广场",
            notes = "用于根据园区名称进行模糊查询。如果为空，则不进行此条件的筛选。",
            required = false
    )
    private String zoneName;



}

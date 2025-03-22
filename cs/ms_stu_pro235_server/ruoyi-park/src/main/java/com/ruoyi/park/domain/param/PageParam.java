package com.ruoyi.park.domain.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author hezining
 * @date 2025/3/7 11:38
 * @description: 分页参数
 */

@Data
@ApiModel("分页参数")
public class PageParam {

    @ApiModelProperty(
            value = "当前页码",
            example = "1",
            notes = "分页查询的当前页码，必须为大于0的整数。",
            required = true
    )
    @NotNull(message = "当前页码不能为空")
    @Min(value = 1, message = "当前页码必须大于等于1")
    private Integer pageNum;

    @ApiModelProperty(
            value = "每页大小",
            example = "10",
            notes = "分页查询的每页记录数，必须在1到100之间。",
            required = true
    )
    @NotNull(message = "每页大小不能为空")
    @Min(value = 1, message = "每页大小必须大于等于1")
    @Max(value = 100, message = "每页大小不能超过100")
    private Integer pageSize;
}

package com.ruoyi.common.core.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("分页参数")
public class PageQuery {
    /** 当前记录起始索引 */
    @ApiModelProperty(value = "当前记录起始索引",required = true)
    @NotNull(message = "当前记录起始索引不为空")
    private Integer pageNum;

    /** 每页显示记录数 */
    @ApiModelProperty(value = "每页显示记录数",required = true)
    @NotNull(message = "每页显示记录数不为空")
    private Integer pageSize;

}
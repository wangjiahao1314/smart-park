package com.ruoyi.park.domain.param.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author hezining
 * @date 2025/3/11 9:06
 * @description: 编辑上架状态参数
 */

@Data
@ApiModel(description = "编辑上架状态参数")
public class EditShelfStatusParam {

    @ApiModelProperty(value = "房间ID", required = true)
    @NotNull(message = "房间ID不能为空")
    private Long roomId;

    @ApiModelProperty(value = "上架状态（0上架 1下架）", required = true)
    @NotNull(message = "上架状态不能为空")
    @Pattern(regexp = "^[0-1]$", message = "上架状态必须是 0 或 1")
    private String shelfStatus;
}

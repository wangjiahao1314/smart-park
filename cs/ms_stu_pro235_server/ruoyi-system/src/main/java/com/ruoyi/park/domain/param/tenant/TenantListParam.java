package com.ruoyi.park.domain.param.tenant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author hezining
 * @date 2025/3/1 20:37
 * @description: 分页获取租户列表参数
 * 该类用于封装分页查询租户列表时所需的筛选条件参数。
 */
@Data
@ApiModel("分页获取租户列表参数")
public class TenantListParam {

    @ApiModelProperty(
            value = "租户名称",
            example = "张三",
            notes = "用于根据租户昵称进行模糊查询。如果为空，则不进行此条件的筛选。",
            required = false
    )
    private String nickName;

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
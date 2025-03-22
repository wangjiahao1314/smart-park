package com.ruoyi.park.domain.param.rental;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * @author hezining
 * @date 2025/3/12 0:11
 * @description: 租赁申请列表参数
 */

@Data
public class RentalListParam {

    @ApiModelProperty(value = "租户名称（申请人）")
    private String nickName;

    @ApiModelProperty(value = "手机号码")
    private String phonenumber;

    @ApiModelProperty(value = "审批状态（0待审批 1审批通过 2已拒绝 3已取消）")
    @Pattern(regexp = "^(0|1|2|3)$", message = "审批状态必须是0, 1, 2, 或 3")
    private String approvalStatus;

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

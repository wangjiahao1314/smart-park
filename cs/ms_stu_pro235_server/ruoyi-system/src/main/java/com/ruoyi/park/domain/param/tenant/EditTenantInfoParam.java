package com.ruoyi.park.domain.param.tenant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author hezining
 * @date 2025/3/1 1:26
 * @description: 修改租户信息参数
 * 该类用于封装修改租户信息时所需的参数，包括姓名、邮箱、家庭区域和详细地址。
 */
@Data
@ApiModel("修改租户信息参数")
public class EditTenantInfoParam {

    @ApiModelProperty(
            value = "租户ID",
            example = "123",
            required = true,
            notes = "租户的唯一标识符"
    )
    @NotNull(message = "租户ID不能为空")
    private Long tenantId;

    @ApiModelProperty(
            value = "真实姓名",
            example = "张三",
            required = true,
            notes = "租户的昵称或显示名称，用于标识租户身份，不能为空，长度为2-20个字符。"
    )
    @NotNull(message = "真实姓名不能为空")
    @Size(min = 2, max = 20, message = "真实姓名长度必须在2到20个字符之间")
    private String nickName;

    @ApiModelProperty(
            value = "邮箱",
            example = "example@example.com",
            required = false, // 邮箱可选，如果不需要修改可以不传
            notes = "租户的电子邮箱地址，用于接收系统通知等信息。如果不需要修改邮箱，可以不传该字段。"
    )
    @Email(message = "邮箱格式不正确")
    @Size(max = 254, message = "邮箱长度不能超过254个字符")
    private String email;

    @ApiModelProperty(
            value = "家庭区域",
            example = "北京市海淀区",
            required = true,
            notes = "租户所在的家庭区域，例如省份或城市。必须提供明确的地理位置信息，长度不超过50个字符。"
    )
    @NotNull(message = "家庭区域不能为空")
    @Size(max = 50, message = "家庭区域长度不能超过50个字符")
    private String homeArea;

    @ApiModelProperty(
            value = "详细地址",
            example = "中关村大街1号院",
            required = true,
            notes = "租户的具体居住地址，包括街道、小区、楼号等详细信息。必须提供完整的地址描述，长度不超过100个字符。"
    )
    @NotNull(message = "详细地址不能为空")
    @Size(max = 100, message = "详细地址长度不能超过100个字符")
    private String addressDetails;
}
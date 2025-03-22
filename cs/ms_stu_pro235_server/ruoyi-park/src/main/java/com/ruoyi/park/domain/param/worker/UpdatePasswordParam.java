package com.ruoyi.park.domain.param.worker;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author raoce
 * @date 2025/3/6 19:21
 * @description:
 */
@ApiModel("更改密码实体")
@Data
public class UpdatePasswordParam {
    @NotNull
    @NotBlank(message = "旧密码不能为空！")
    @ApiModelProperty("旧密码")
    String pastPassword;
    @NotNull
    @NotBlank(message = "新密码不能为空！")
    @ApiModelProperty("新密码")
    String newPassword;
    @NotNull
    @NotBlank(message = "确定密码不能为空！")
    @ApiModelProperty("确定密码")
    String confirmedPassword;
}

package com.ruoyi.park.domain.param.tenant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author hezining
 * @date 2025/2/25 22:06
 * @description: 新增租户参数
 */

@Data
@ApiModel(value = "AddTenantParam", description = "新增租户参数")
public class AddTenantParam {

    @ApiModelProperty(value = "用户名(账户名)", required = true, example = "testUser", notes = "必填项，用于登录的账户名")
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty(value = "昵称", required = true, example = "张三", notes = "必填项，用户的显示名称")
    @NotBlank(message = "昵称不能为空")
    private String nickName;

    @ApiModelProperty(value = "身份证号码", required = true, example = "123456789012345678", notes = "必填项，需符合身份证号码规则")
    @Pattern(regexp = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)", message = "身份证号码格式不正确")
    @NotBlank(message = "身份证号码不能为空")
    private String idCardNumber;

    @ApiModelProperty(value = "电子邮箱", required = false, example = "example@example.com", notes = "非必填项，需符合邮箱格式")
    @Email(message = "邮箱格式不正确")
    private String email;

    @ApiModelProperty(value = "手机号码", required = true, example = "13800138000", notes = "必填项，需符合手机号码规则")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号码格式不正确")
    @NotBlank(message = "手机号码不能为空")
    private String phonenumber;

    @ApiModelProperty(value = "家庭区域", required = true, example = "北京市朝阳区", notes = "必填项，用户所在的家庭区域")
    @NotBlank(message = "家庭区域不能为空")
    private String homeArea;

    @ApiModelProperty(value = "详细地址", required = true, example = "北京市朝阳区xx街道xx号", notes = "必填项，用户的详细居住地址")
    @NotBlank(message = "详细地址不能为空")
    private String addressDetails;

    @ApiModelProperty(value = "密码", required = true, example = "password123", notes = "必填项，用户登录密码，长度为6-20位")
    @Size(min = 6, max = 20, message = "密码长度应在6-20位之间")
    @NotBlank(message = "密码不能为空")
    private String password;
}

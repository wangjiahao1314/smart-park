package com.ruoyi.common.core.domain.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 用户注册对象
 * 
 * @author ruoyi
 */

@ApiModel("用户注册对象")
public class RegisterBody extends LoginBody {

    @ApiModelProperty(value = "用户姓名", required = true, example = "张三", notes = "长度为2-10个字符，不能为空")
    @NotBlank(message = "用户姓名不能为空")
    @Size(min = 2, max = 10, message = "用户姓名长度必须在2到10个字符之间")
    private String nickName;

    @ApiModelProperty(value = "身份证号码", required = true, example = "110101199001011234", notes = "符合中国大陆身份证号码规则，不能为空")
    @NotBlank(message = "身份证号码不能为空")
    @Pattern(regexp = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)", message = "身份证号码格式不正确")
    private String idCardNumber;

    @ApiModelProperty(value = "电话号码", required = true, example = "13812345678", notes = "符合中国大陆手机号码规则，不能为空")
    @NotBlank(message = "电话号码不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "电话号码格式不正确")
    private String phonenumber;

    @ApiModelProperty(value = "租户注册（tenant）/ 维修工注册（repair）", required = true, example = "tenant", notes = "只能是 tenant 或 repair，不能为空")
    @NotBlank(message = "角色标识不能为空")
    @Pattern(regexp = "^(tenant|repair)$", message = "角色标识只能是 tenant 或 repair")
    private String roleKey;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }
}

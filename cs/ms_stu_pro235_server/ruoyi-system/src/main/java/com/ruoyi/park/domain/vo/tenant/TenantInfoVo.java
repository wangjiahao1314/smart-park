package com.ruoyi.park.domain.vo.tenant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hezining
 * @date 2025/3/1 0:18
 * @description: 根据租户ID获取租户详情Vo
 */

@Data
@ApiModel(value = "租户详情VO", description = "该类用于封装租户详细信息，包括昵称、身份证号、邮箱、手机号、家庭区域及详细地址等信息")
public class TenantInfoVo {

    @ApiModelProperty(value = "租户ID", example = "132", required = true)
    private String tenantId;

    @ApiModelProperty(value = "用户名", example = "tenant123456", required = true, notes = "用于登录")
    private String userName;

    @ApiModelProperty(value = "真实姓名", example = "张三", required = true, notes = "租户的昵称或显示名称")
    private String nickName;

    @ApiModelProperty(value = "身份证号码", example = "110101199001011234", required = true, notes = "租户的身份证号码，用于身份验证")
    private String idCardNumber;

    @ApiModelProperty(value = "邮箱", example = "example@example.com", required = true, notes = "租户的电子邮箱地址")
    private String email;

    @ApiModelProperty(value = "手机号码", example = "13800138000", required = true, notes = "租户的手机号码，用于联系租户")
    private String phonenumber;

    @ApiModelProperty(value = "家庭区域", example = "北京市海淀区", required = true, notes = "租户所在的家庭区域，例如省份或城市")
    private String homeArea;

    @ApiModelProperty(value = "详细地址", example = "中关村大街1号院", required = true, notes = "租户的具体居住地址")
    private String addressDetails;
}
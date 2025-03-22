package com.ruoyi.park.domain.vo.contract;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerContractOthers {


    @ApiModelProperty("租户ID")
    private Long tenantId;


    @ApiModelProperty("身份证号码")
    private String idCardNumber;

    @ApiModelProperty("手机号")
    private String phonenumber;


    @ApiModelProperty("租户名称")
    private String userName;

    @ApiModelProperty("邮箱")
    private String email;
}

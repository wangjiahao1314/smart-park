package com.ruoyi.park.domain.vo.repair;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author raoce
 * @date 2025/3/5 16:24
 * @description: 基本资料
 */
@Data
@ApiModel("基本资料")
public class BasicInfo {
    @ApiModelProperty("真实姓名")
    private String nickName;
    @ApiModelProperty("身份证")
    private String idCardNumber;
    @ApiModelProperty("联系人电话")
    private String workerNumber;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("家庭住址")
    private String homeArea;
}

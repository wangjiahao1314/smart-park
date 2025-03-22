package com.ruoyi.park.domain.vo.tenant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 *
 * @author raoce
 * @date 2025/3/5 15:58
 * @description:个人中心
 */
@Data
@ApiModel("租户个人中心")
public class TenantIndividualCenterVo {
    @ApiModelProperty("账户")
    private String userName;
    @ApiModelProperty("真实姓名")
    private String nickName;
    @ApiModelProperty("身份证")
    private String idCardNumber;
    @ApiModelProperty("联系人电话")
    private String tenantNumber;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("所属角色")
    private String userType;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("家庭住址")
    private String homeArea;
    @ApiModelProperty("现住住址")
    private String addressDetail;
}

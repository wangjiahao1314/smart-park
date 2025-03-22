package com.ruoyi.park.domain.vo.contract;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ParkContractListVo {

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("合同编号")
    private String contractNo;

    @ApiModelProperty("合同名称")
    private String contractName;

    @ApiModelProperty("合同备注")
    private String contractRemark;

    @ApiModelProperty("合同状态; 0: 待签定(default); 1:执行中; 2:已过期；")
    private String contractType;


    @ApiModelProperty("合同生效时间")
    private Date startTime;

    @ApiModelProperty("合同截止时间")
    private Date endTime;

    @ApiModelProperty("电话号码")
    private String phonenumber;


}

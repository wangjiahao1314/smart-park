package com.ruoyi.park.domain.vo.bill;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AdminBillListVo {
    @ApiModelProperty(value = "账单编号")
    private String billNo;
    @ApiModelProperty("账单ID")
    private Long billId;
    @ApiModelProperty("账单名称")
    private String billName;
    @ApiModelProperty("账单开始时间")
    private Date billStartTime;
    @ApiModelProperty("账单结束时间")
    private Date billEndTime;
    @ApiModelProperty("账单状态")
    private String billStatus;
    @ApiModelProperty("账单逾期时间")
    private Date billOvertime;
    @ApiModelProperty("账单描述")
    private String description;
    @ApiModelProperty("应收租金")
    private BigDecimal rentDue;
    @ApiModelProperty("已收租金")
    private BigDecimal rentPaid;
    @ApiModelProperty("水费")
    private BigDecimal waterFee;
    @ApiModelProperty("电费")
    private BigDecimal electricityFee;
    @ApiModelProperty("其他费用")
    private BigDecimal otherFees;
    @ApiModelProperty("退费")
    private BigDecimal refund;
    @ApiModelProperty("物业管理费")
    private BigDecimal propertyManagementFee;
    @ApiModelProperty("合计应收")
    private BigDecimal totalDue;
    @ApiModelProperty("合计已收")
    private BigDecimal totalPaid;
    @ApiModelProperty("合计未收")
    private BigDecimal totalUnpaid;
    @ApiModelProperty("合同编号")
    private String contractNo;
    @ApiModelProperty("合同名称")
    private String contractName;
    @ApiModelProperty("押金")
    private BigDecimal deposit;
    @ApiModelProperty("租户名称")
    private String tenantName;
}

package com.ruoyi.park.domain.vo.contract;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ParkContractAddVo {


    @ApiModelProperty(value = "合同编号",required = true)
    @NotNull(message = "合同编号不为空")
    private String contractNo;

    @ApiModelProperty(value = "客户名称",required = true)
    @NotNull(message = "客户名称不为空")
    private String customerName;

    @ApiModelProperty(value = "合同名称",required = true)
    @NotNull(message = "合同名称不为空")
    private String contractName;

    @ApiModelProperty("合同备注")
    private String contractRemark;

    @ApiModelProperty(value = "合同状态; 0: 待签定(default); 1:执行中; 2:已终止 3:申请退租",required = true)
    @NotNull(message = "合同状态不为空")
    private String contractType;


    @ApiModelProperty(value = "合同生效时间",required = true)
    @NotNull(message = "合同生效时间不为空")
    private Date startTime;

    @ApiModelProperty(value = "合同截止时间",required = true)
    @NotNull(message = "合同截止时间不为空")
    private Date endTime;

    @ApiModelProperty(value = "租金单价",required = true)
    @NotNull(message = "租金单价不为空")
    private BigDecimal rentPrice;

    @ApiModelProperty(value = "园区id",required = true)
    @NotNull(message = "园区id不为空")
    private Long zoneId;

    @ApiModelProperty(value = "楼宇id",required = true)
    @NotNull(message = "楼宇id不为空")
    private Long buildingId;

    @ApiModelProperty(value = "楼层id",required = true)
    @NotNull(message = "楼层id不为空")
    private Long floorId;

    @ApiModelProperty(value = "房间id",required = true)
    @NotNull(message = "房间id不为空")
    private Long roomId;

    @ApiModelProperty(value = "租户id",required = true)
    @NotNull(message = "租户id不为空")
    private Long tenantId;


    @ApiModelProperty("账单id")
    private Long billId;

    @ApiModelProperty(value = "出租类型 0：固定月租；1：按天数",required = true)
    @NotNull(message = "出租类型不为空")
    private String rentType;

    @ApiModelProperty("按月租金")
    private BigDecimal rentMonthPrice;

    @ApiModelProperty("按日租金")
    private BigDecimal rentDayPrice;

    @ApiModelProperty("物业费")
    @NotNull(message = "物业费不为空")
    private BigDecimal mangerPrice;

    @ApiModelProperty("物业费单位（0(元/㎡/天) 1(元/㎡/月) 2(元/天) 3(元/月))")
    private Boolean mangerPriceType;

    @ApiModelProperty("日管理费")
    private BigDecimal dayMangerPrice;

    @ApiModelProperty("月管理费")
    private BigDecimal monthMangerPrice;

    @ApiModelProperty("月管理费类型(0:28/天；1:30/天；2:31/天)")
    private String monthMangerPriceType;

    @ApiModelProperty(value = "支付押金的月份",required = true)
    @NotNull(message = "支付押金的月份不为空")
    private Integer mortgage;

    @ApiModelProperty(value = "押金",required = true)
    @NotNull(message = "押金不为空")
    private BigDecimal deposit;

    @ApiModelProperty(value = "押金期限",required = true)
    @NotNull(message = "押金期限不为空")
    private Integer depositPeriod;

}

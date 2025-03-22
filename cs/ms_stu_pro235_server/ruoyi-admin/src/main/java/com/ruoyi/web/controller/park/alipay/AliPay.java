package com.ruoyi.web.controller.park.alipay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AliPay {
    @ApiModelProperty("商户订单号")
    private String traceNo;
    @ApiModelProperty(value = "交易金额")
    private double totalAmount;
    @ApiModelProperty(value = "交易名称")
    private String subject;
    @ApiModelProperty(value = "支付宝交易凭证号")
    private String alipayTraceNo;
}
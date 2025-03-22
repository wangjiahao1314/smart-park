package com.ruoyi.park.domain.vo.bill;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
public class ParkBillSearchVo {
    @ApiModelProperty(value = "客户名称", example = "请输入客户名称")
    private String tenantName;

    @ApiModelProperty(value = "账单编号", example = "请输入账单编号")
    private String billNo;

    @ApiModelProperty(value = "账单月份", example = "请输入账单月份")
    private String billMonth;


    @ApiModelProperty(value = "合同编号", example = "请输入合同编号")
    private String contractNo;

    @ApiModelProperty(value = "账单状态(0'未支付', 1'已支付'')", example = "请输入账单状态")
    private String billStatus;




    @ApiModelProperty(value = "排序顺序（asc/desc）", example = "asc")
    private String sortOrder;





}

package com.ruoyi.park.domain.vo.backcontract;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ParkBackContractVo {



    @ApiModelProperty(value = "合同编号", required = true)
    @NotNull(message = "合同编号不能为空")
    private String contractNo;


    @ApiModelProperty(value = "合同名称", required = true)
    private String contractName;

    @ApiModelProperty(value = "租户名称", required = true)
    private String tenantName;

    @ApiModelProperty(value = "退租日期", required = true)
    private Date terminationDate;

    @ApiModelProperty(value = "退租状态")
    private String terminationStatus;

    @ApiModelProperty(value = "所属园区")
    private String parkName;

    @ApiModelProperty(value = "所属楼宇")
    private String buildingName;

    @ApiModelProperty(value = "所属楼层")
    private String floor;

    @ApiModelProperty(value = "房间名称")
    private String roomName;

    @ApiModelProperty(value = "收租面积（平方米）")
    private BigDecimal rentableArea;

    @NotNull(message = "应收部分金额不能为空")
    @ApiModelProperty(value = "应收部分金额", required = true)
    private BigDecimal receivableAmount;
    @NotNull(message = "应退部分金额不能为空")
    @ApiModelProperty(value = "应退部分金额", required = true)
    private BigDecimal refundAmount;
    @NotNull(message = "总计金额不能为空")
    @ApiModelProperty(value = "总计金额", required = true)
    private BigDecimal totalAmount;
    @NotNull(message = "退租原因不能为空")
    @ApiModelProperty(value = "退租原因", required = true)
    private String terminationReason;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

    @ApiModelProperty(value = "存档图片路径")
    private String archiveImages;

}

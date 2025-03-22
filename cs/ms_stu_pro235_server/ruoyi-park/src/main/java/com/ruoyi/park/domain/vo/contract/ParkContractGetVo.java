package com.ruoyi.park.domain.vo.contract;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data

public class ParkContractGetVo {
    @ApiModelProperty(value = "客户名称")
    private String customerName;
    @ApiModelProperty(value = "合同名称")
    private String contractName;
    @ApiModelProperty(value = "合同编号")
    private String contractNo;
    @ApiModelProperty(value = "合同状态; 0: 待签定(default); 1:执行中; 2:已过期；")
    private String contractType;

    @ApiModelProperty(value = "排序字段 asc :升序 desc:降序")
    private String sort="desc";
}

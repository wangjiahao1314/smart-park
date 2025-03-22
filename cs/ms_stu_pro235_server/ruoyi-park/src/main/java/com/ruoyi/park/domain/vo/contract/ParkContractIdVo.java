package com.ruoyi.park.domain.vo.contract;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class ParkContractIdVo {

    @ApiModelProperty(value = "合同id",required = true)
    @NotNull(message = "合同Id不为空")
    private Long contractId;
}

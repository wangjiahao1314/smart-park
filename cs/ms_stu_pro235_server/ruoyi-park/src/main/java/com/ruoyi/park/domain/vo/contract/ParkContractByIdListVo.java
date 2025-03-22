package com.ruoyi.park.domain.vo.contract;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ParkContractByIdListVo {

    @ApiModelProperty(value = "合同id", required = true)
    @NotNull(message = "合同列表Id不为空")
    private List<Long> contractListId;
    @ApiModelProperty(value = "合同状态; 0: 待签定(default); 1:执行中; 2:已终止 3:申请退租")
    @NotNull(message = "合同状态不为空s")
    private String contractType;

}

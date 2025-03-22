package com.ruoyi.park.domain.param.repair;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * @author raoce
 * @date 2025/3/16 0:12
 * @description:
 */
@ApiModel("指派维修工")
@Data
public class AssignWorkerParam {
    @ApiModelProperty("报修单Id")
    Long repairId;
    @ApiModelProperty("工人id")
    Long workerId;
}

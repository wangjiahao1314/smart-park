package com.ruoyi.park.domain.vo.repair;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author raoce
 * @date 2025/3/13 21:10
 * @description:
 */
@ApiModel("维修工列表")
@Data
public class WorkerListVo {
    @ApiModelProperty("名称")
    private String workerName;
}

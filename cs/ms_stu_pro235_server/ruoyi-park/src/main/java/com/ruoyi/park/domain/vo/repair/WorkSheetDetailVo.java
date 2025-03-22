package com.ruoyi.park.domain.vo.repair;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author raoce
 * @date 2025/3/4 19:21
 * @description:
 */
@Data
@ApiModel("工单详情")
public class WorkSheetDetailVo {
    @ApiModelProperty("维修状态")
    private String status;
    @ApiModelProperty("报修信息")
    private RepairInfo repairInfo;
    @ApiModelProperty("维修信息")
    private MaintainInfo maintainInfo;
}

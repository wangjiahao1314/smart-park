package com.ruoyi.park.domain.vo.repair;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author raoce
 * @date 2025/3/13 19:20
 * @description:
 */
@Data
@ApiModel("管理端查看工单详情")
public class ManageRepairDtlVo {
    @ApiModelProperty("维修状态")
    private String status;
    @ApiModelProperty("报修信息")
    private RepairInfo repairInfo;
    @ApiModelProperty("维修信息")
    private MaintainInfo maintainInfo;
}

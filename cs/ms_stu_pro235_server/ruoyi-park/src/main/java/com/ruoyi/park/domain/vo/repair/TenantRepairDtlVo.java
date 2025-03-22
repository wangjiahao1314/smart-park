package com.ruoyi.park.domain.vo.repair;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author raoce
 * @date 2025/3/11 18:43
 * @description:
 */
@Data
@ApiModel("租户查询报修单详情实体")
public class TenantRepairDtlVo {
    @ApiModelProperty("维修状态")
    private String status;
    @ApiModelProperty("报修信息")
    private RepairInfo repairInfo;
    @ApiModelProperty("维修信息")
    private MaintainInfo maintainInfo;
}

package com.ruoyi.park.domain.param.repair;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author raoce
 * @date 2025/3/11 20:06
 * @description:
 */
@Data
@ApiModel("租户新建报修")
public class TenantRepairAddParam {

    @NotNull(message = "联系人不能为空")
    @NotBlank(message = "联系人不能为空")
    @ApiModelProperty("联系人")
    private String contactName;
    @NotNull(message = "联系人电话不能为空")
    @NotBlank(message = "联系人电话不能为空")
    @ApiModelProperty("联系人电话")
    private String contactNumber;
    @NotNull(message = "上门时间不能为空")
    @ApiModelProperty("上门时间")
    private String repairStartTime;
    @NotNull(message = "维修区域不能为空")
    @NotBlank(message = "维修区域不能为空")
    @NotBlank
    @ApiModelProperty("维修区域")
    private String repairArea;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("维修照片")
    private String repair_img;
}

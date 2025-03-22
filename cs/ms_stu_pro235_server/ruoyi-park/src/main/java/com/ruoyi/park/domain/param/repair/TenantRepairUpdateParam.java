package com.ruoyi.park.domain.param.repair;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author raoce
 * @date 2025/3/10 19:35
 * @description: 报修单修改参数实体
 */
@ApiModel("报修单修改参数实体")
@Data
public class TenantRepairUpdateParam {
    @NotNull(message = "报修id不能为空")
    private Long repairId;
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

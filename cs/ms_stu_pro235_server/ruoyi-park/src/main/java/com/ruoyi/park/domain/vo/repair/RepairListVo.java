package com.ruoyi.park.domain.vo.repair;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author raoce
 * @date 2025/3/2 19:44
 * @description:
 */
@Data
@ApiModel(value = "查询工单响应实体")
public class RepairListVo {
    @ApiModelProperty("维修单号")
    private String repairNo;
    @ApiModelProperty("报修区域")
    private String repairArea;
    @ApiModelProperty("联系人")
    private String contactName;
    @ApiModelProperty("联系人电话")
    private String contactNumber;
    @ApiModelProperty("维修状态")
    private char status;
    @ApiModelProperty("报修时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:SS")
    private Date repairStartTime;

}

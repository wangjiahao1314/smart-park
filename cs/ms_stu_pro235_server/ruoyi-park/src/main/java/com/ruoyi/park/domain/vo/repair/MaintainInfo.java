package com.ruoyi.park.domain.vo.repair;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author raoce
 * @date 2025/3/4 19:38
 * @description:
 */
@ApiModel("维修信息")
@Data
public class MaintainInfo {
    @ApiModelProperty("维修员")
    private String workerName;
    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:SS")
    private Date repairStartTime;
    @ApiModelProperty("结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:SS")
    private Date repairEndTime;
    @ApiModelProperty("联系电话")
    private String workerNumber;
}

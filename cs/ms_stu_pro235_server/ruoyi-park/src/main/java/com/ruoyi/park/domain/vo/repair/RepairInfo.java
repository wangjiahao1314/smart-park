package com.ruoyi.park.domain.vo.repair;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author raoce
 * @date 2025/3/4 19:22
 * @description:
 */
@Data
@ApiModel("报修信息")
public class RepairInfo {
    @ApiModelProperty("租户id")
    private Long tenantId;
    @ApiModelProperty("租户名称")
    private String tenantName;
    @ApiModelProperty("房间id")
    private Long roomId;
    @ApiModelProperty("房间名称")
    private String roomName;
    @ApiModelProperty("所属园区")
    private String zoneName;
    @ApiModelProperty("所属楼宇")
    private String buildingName;
    @ApiModelProperty("所属楼层")
    private String floorName;
    @ApiModelProperty("联系人呢")
    private String contactName;
    @ApiModelProperty("联系电话")
    private String contactNumber;
    @ApiModelProperty("维修申请照片")
    private String repairImg;
    @ApiModelProperty("维修单号")
    private String repairNo;
    @ApiModelProperty("报修区域")
    private String repairArea;
    private String repairRemark;
    @ApiModelProperty("维修时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:SS")
    private Date repairStartTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:SS")
    private Date repairEndTime;
    @ApiModelProperty("报修时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:SS")
    private Date allocationTime;
    @ApiModelProperty("描述")
    private String description;


}

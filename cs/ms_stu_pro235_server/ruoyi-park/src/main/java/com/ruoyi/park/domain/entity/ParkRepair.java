package com.ruoyi.park.domain.entity;


import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ParkRepair {

  @TableId(type = IdType.AUTO)
  private Long repairId;
  @ApiModelProperty("客户Id")
  private Long tenantId;

  private Long roomId;

  private Long workerId;

  private String contactName;

  private String contactNumber;

  private String repairImg;

  private String repairNo;

  private String repairArea;

  private String status;

  private String repairRemark;

  private Date repairStartTime;

  private Date repairEndTime;
  private String description;
  private Date allocationTime;

  private Date processingTime;

  private Date completeCancelTime;

  @TableField(fill = FieldFill.INSERT)
  private Date createTime;

  @TableField(fill = FieldFill.INSERT)
  private String createBy;

  @TableField(fill = FieldFill.UPDATE)
  private Date updateTime;

  @TableField(fill = FieldFill.UPDATE)
  private String updateBy;
  @TableLogic(value = "0",delval = "2")
  private String delFlag;



}

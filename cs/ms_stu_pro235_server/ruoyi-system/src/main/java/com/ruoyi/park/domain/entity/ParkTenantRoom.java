package com.ruoyi.park.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParkTenantRoom {

  private Long tenantId;

  private Long roomId;

  private Long contractId;

  private Date rentStartTime;

  private Date rentEndTime;

  private String approvalStatus;

  private String approvalOpinion;

  private String houseType;

  @TableLogic(value = "0", delval = "2")
  private String delFlag;

  @TableField(fill = FieldFill.INSERT)
  private Date createTime;

  @TableField(fill = FieldFill.INSERT)
  private String createBy;

  @TableField(fill = FieldFill.UPDATE)
  private Date updateTime;

  @TableField(fill = FieldFill.UPDATE)
  private String updateBy;


}

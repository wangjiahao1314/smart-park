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
public class ParkBuilding {

  @TableId(type = IdType.AUTO)
  private Long buildingId;

  private Long zoneId;

  private String buildingName;

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

  private String remark;


}

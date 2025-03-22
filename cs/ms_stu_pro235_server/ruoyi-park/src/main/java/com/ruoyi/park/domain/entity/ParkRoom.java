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
public class ParkRoom {

  @TableId(type = IdType.AUTO)
  private Long roomId;

  private Long zoneId;

  private Long buildingId;

  private Long floorId;

  private String roomName;

  private String roomImg;

  private Double squareMeasure;

  private String houseType;

  private Double height;

  private String decorationType;

  private Double rent;

  private String rentType;

  private Double propertyFee;

  private String propertyUnit;

  private Date shelfTime;

  private String shelfStatus;

  private String roomIntroduction;

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

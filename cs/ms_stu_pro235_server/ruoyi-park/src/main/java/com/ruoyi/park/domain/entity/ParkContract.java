package com.ruoyi.park.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ParkContract {

  @TableId(type = IdType.AUTO)
  @ApiModelProperty("合同id")
  private Long contractId;

  @ApiModelProperty("客户名称")
  private String customerName;

  @ApiModelProperty("合同编号")
  private String contractNo;
  @ApiModelProperty("合同版本")
  private int version;

  @ApiModelProperty("合同名称")
  private String contractName;

  @ApiModelProperty("合同备注")
  private String contractRemark;

  @ApiModelProperty(value = "合同状态; 0: 待签定(default); 1:执行中; 2:已终止 3:申请退租")
  private String contractType;
  /**
   * 删除标志（0代表存在 2代表删除）
   */
  @ApiModelProperty
  private String delFlag;


  @ApiModelProperty("合同生效时间")
  private Date startTime;

  @ApiModelProperty("合同截止时间")
  private Date endTime;

  @ApiModelProperty("租金单价")
  private BigDecimal rentPrice;

  @ApiModelProperty("园区id")
  private Long zoneId;

  @ApiModelProperty("楼宇id")
  private Long buildingId;

  @ApiModelProperty("楼层id")
  private Long floorId;

  @ApiModelProperty("房间id")
  private Long roomId;

  @ApiModelProperty("租户id")
  private Long tenantId;



  @ApiModelProperty("账单id")
  private Long billId;

  @ApiModelProperty("出租类型 0：固定月租；1：按天数")
  private String rentType;

  @ApiModelProperty("按月租金")
  private BigDecimal rentMonthPrice;

  @ApiModelProperty("按日租金")
  private BigDecimal rentDayPrice;

  @ApiModelProperty("物业费")
  private BigDecimal mangerPrice;

  @ApiModelProperty("物业费单位（0(元/㎡/天) 1(元/㎡/月) 2(元/天) 3(元/月))")
  private String mangerPriceType;

  @ApiModelProperty("日管理费")
  private BigDecimal dayMangerPrice;

  @ApiModelProperty("月管理费")
  private BigDecimal monthMangerPrice;

  @ApiModelProperty("月管理费类型(0:28/天；1:30/天；2:31/天)")
  private String monthMangerPriceType;

  @ApiModelProperty("支付押金的月份")
  private Integer mortgage;

  @ApiModelProperty("押金")
  private BigDecimal deposit;

  @ApiModelProperty("押金期限")
  private Integer depositPeriod;


  @TableField(fill = FieldFill.INSERT)
  private Date createTime;

  @TableField(fill = FieldFill.INSERT)
  private String createBy;

  @TableField(fill = FieldFill.UPDATE)
  private Date updateTime;

  @TableField(fill = FieldFill.UPDATE)
  private String updateBy;





}

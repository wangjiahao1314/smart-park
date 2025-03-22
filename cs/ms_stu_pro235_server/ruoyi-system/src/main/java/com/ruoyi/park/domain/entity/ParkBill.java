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
public class ParkBill {

  @TableId(type = IdType.AUTO)
  private Long billId;
  /**
   * 租户id
   */
  private Long tenantId;
  /**
   * 合同ID，关联合同表
   */
  private Long contractId;



  @ApiModelProperty(value = "账单编号", example = "请输入账单编号")
  private String billNo;

  @ApiModelProperty(value = "账单月份", example = "请输入账单月份")
  private String billMonth;

  private String billName;
  /**
   * 账单起始时间
   */
  private Date billStartTime;
  /**
   * 账单结束时间
   */
  private Date billEndTime;
  /**
   * 账单状态(0'未支付', 1'已支付'')
   */
  private String billStatus;
  /**
   * 账单是否预期(0未逾期,1已逾期)
   */
  private String billOvertime;

  /**
   * 删除标志 0:存在 1：删除
   */
  private String delFlag;
  /**
   * 账单描述或备注信息
   */
  private String description;
  /**
   * 应收租金
   */
  private BigDecimal rentDue;

  /**
   * 已收租金
   */
  private BigDecimal rentPaid;
  /**
   * 水费
   */
  private BigDecimal waterFee;
  /**
   * 电费
   */
  private BigDecimal electricityFee;
  /**
   * 其他费用
   */
  private BigDecimal otherFees;
  /**
   * 退费
   */
  private BigDecimal refund;
  /**
   * 物业管理费
   */
  private BigDecimal propertyManagementFee;
  /**
   * 合计应收
   */
  private BigDecimal totalDue;
  /**
   * 合计未收
   */
  private BigDecimal totalUnpaid;
  /**
   * 合计已收
   */
  private BigDecimal totalPaid;

  @TableField(fill = FieldFill.INSERT)
  private Date createTime;

  @TableField(fill = FieldFill.INSERT)
  private String createBy;

  @TableField(fill = FieldFill.UPDATE)
  private Date updateTime;

  @TableField(fill = FieldFill.UPDATE)
  private String updateBy;


}

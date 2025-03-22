package com.ruoyi.park.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * 退租信息表(ParkBackContract)实体类
 *
 * @author makejava
 * @since 2025-03-08 15:52:25
 */
@Data

public class ParkBackContract implements Serializable {
    private static final long serialVersionUID = 598018354876453910L;
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "合同编号")
    private String contractNumber;

    @ApiModelProperty(value = "退租单号")
    private String terminationNumber;

    @ApiModelProperty(value = "合同名称")
    private String contractName;

    @ApiModelProperty(value = "租户名称")
    private String tenantName;

    @ApiModelProperty(value = "退租日期")
    private Date terminationDate;

    @ApiModelProperty(value = "退租状态")
    private String terminationStatus;

    @ApiModelProperty(value = "所属园区")
    private String parkName;

    @ApiModelProperty(value = "所属楼宇")
    private String buildingName;

    @ApiModelProperty(value = "所属楼层")
    private String floor;

    @ApiModelProperty(value = "房间名称")
    private String roomName;

    @ApiModelProperty(value = "收租面积（平方米）")
    private BigDecimal rentableArea;

    @ApiModelProperty(value = "应收部分金额")
    private BigDecimal receivableAmount;

    @ApiModelProperty(value = "应退部分金额")
    private BigDecimal refundAmount;

    @ApiModelProperty(value = "总计金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "退租原因")
    private String terminationReason;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

    @ApiModelProperty(value = "存档图片路径")
    private String archiveImages;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

}


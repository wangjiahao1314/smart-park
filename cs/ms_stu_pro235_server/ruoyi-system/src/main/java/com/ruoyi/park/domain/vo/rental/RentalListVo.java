package com.ruoyi.park.domain.vo.rental;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author hezining
 * @date 2025/3/12 0:30
 * @description: 租赁申请列表VO
 */

@Data
@ApiModel(description = "租赁申请列表VO")
public class RentalListVo {

    @ApiModelProperty(value = "租户ID", example = "1234567890")
    private Long tenantId;

    @ApiModelProperty(value = "租户名称", example = "张三")
    private String nickName;

    @ApiModelProperty(value = "园区ID", example = "1")
    private Long zoneId;

    @ApiModelProperty(value = "园区名称", example = "A区")
    private String zoneName;

    @ApiModelProperty(value = "楼宇ID", example = "101")
    private Long buildingId;

    @ApiModelProperty(value = "楼宇名称", example = "一号楼")
    private String buildingName;

    @ApiModelProperty(value = "楼层ID", example = "1")
    private Long floorId;

    @ApiModelProperty(value = "楼层名称", example = "一楼")
    private String floorName;

    @ApiModelProperty(value = "房间ID", example = "101")
    private Long roomId;

    @ApiModelProperty(value = "房间名称", example = "101室")
    private String roomName;

    @ApiModelProperty(value = "租赁状态（0在租 1未租）", required = false, example = "0")
    private String rentalStatus;

    @ApiModelProperty(value = "联系电话", example = "13800001234")
    private String phonenumber;

    @ApiModelProperty(value = "租赁开始时间", example = "2023-01-01 00:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rentStartTime;

    @ApiModelProperty(value = "租赁结束时间", example = "2023-12-31 23:59:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rentEndTime;

    @ApiModelProperty(value = "审批状态（0待审批 1审批通过 2已拒绝 3已取消）", example = "0")
    private String approvalStatus;
}

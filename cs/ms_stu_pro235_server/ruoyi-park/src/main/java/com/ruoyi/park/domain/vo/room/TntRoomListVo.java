package com.ruoyi.park.domain.vo.room;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * @author hezining
 * @date 2025/3/12 21:31
 * @description: 租户端我的房间列表参数
 */

@Data
@ApiModel(description = "租户端我的房间列表VO")
public class TntRoomListVo {

    @ApiModelProperty(value = "合同ID", example = "1", required = true)
    private Long contractId;

    @ApiModelProperty(value = "园区ID", example = "1", required = true)
    private Long zoneId;

    @ApiModelProperty(value = "园区名称", example = "Zone A", required = true)
    private String zoneName;

    @ApiModelProperty(value = "楼宇ID", example = "101", required = true)
    private Long buildingId;

    @ApiModelProperty(value = "楼宇名称", example = "Building 1", required = true)
    private String buildingName;

    @ApiModelProperty(value = "楼层ID", example = "201", required = true)
    private Long floorId;

    @ApiModelProperty(value = "楼层名称", example = "Floor 1", required = true)
    private String floorName;

    @ApiModelProperty(value = "房间ID", example = "301", required = true)
    private Long roomId;

    @ApiModelProperty(value = "房间名称", example = "Room 101", required = true)
    private String roomName;

    @ApiModelProperty(value = "面积（平方米）", example = "80.5", required = true)
    @DecimalMin(value = "0.0", inclusive = false, message = "面积必须大于0")
    private Double squareMeasure;

    @ApiModelProperty(value = "租金", example = "5000.0", required = true)
    @DecimalMin(value = "0.0", inclusive = false, message = "租金必须大于0")
    private Double rent;

    @ApiModelProperty(value = "租金类型（0(元/㎡/天) 1(元/㎡/月) 2(元/天) 3(元/月)）", example = "1", required = true)
    private String rentType;

    @ApiModelProperty(value = "申请时间（租赁申请创建时间）", example = "2023-01-01 00:00:00", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "审批状态（0待审批 1审批通过 2已拒绝 3已取消）", example = "1", required = true)
    private String approvalStatus;

    @ApiModelProperty(value = "审批意见", example = "同意租赁", required = false)
    private String approvalOpinion;

    @ApiModelProperty(value = "租赁开始时间", example = "2023-01-01 00:00:00", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rentStartTime;

    @ApiModelProperty(value = "租赁结束时间", example = "2023-12-31 23:59:59", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rentEndTime;

    @ApiModelProperty(value = "房屋状态（0已取消 1在租 2退租 默认为空）", example = "1", required = false)
    private String houseType;
}

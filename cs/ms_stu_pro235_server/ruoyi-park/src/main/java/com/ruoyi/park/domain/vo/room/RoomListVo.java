package com.ruoyi.park.domain.vo.room;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author hezining
 * @date 2025/3/16 10:13
 * @description: 房间列表VO
 */

@Data
public class RoomListVo {

    @ApiModelProperty(value = "房间ID", required = true, example = "1")
    @NotNull(message = "房间ID不能为空")
    private Long roomId;

    @ApiModelProperty(value = "房间名称", required = true, example = "A101")
    @NotBlank(message = "房间名称不能为空")
    private String roomName;

    @ApiModelProperty(value = "园区ID", required = true, example = "1")
    @NotNull(message = "园区ID不能为空")
    private Long zoneId;

    @ApiModelProperty(value = "园区名称", required = true, example = "广场")
    @NotBlank(message = "园区名称不能为空")
    private String zoneName;

    @ApiModelProperty(value = "楼宇ID", required = true, example = "1")
    @NotNull(message = "楼宇ID不能为空")
    private Long buildingId;

    @ApiModelProperty(value = "楼宇名称", required = true, example = "1号楼")
    @NotBlank(message = "楼宇名称不能为空")
    private String buildingName;

    @ApiModelProperty(value = "楼层ID", required = true, example = "1")
    @NotNull(message = "楼层ID不能为空")
    private Long floorId;

    @ApiModelProperty(value = "楼层名称", required = true, example = "A202")
    @NotBlank(message = "楼层名称不能为空")
    private String floorName;

    @ApiModelProperty(value = "租金", required = true, example = "2000.00")
    @NotNull(message = "租金不能为空")
    @DecimalMin(value = "0.0", inclusive = false, message = "租金必须大于0")
    private Double rent;

    @ApiModelProperty(value = "租金类型（0(元/㎡/天) 1(元/㎡/月) 2(元/天) 3(元/月)）", required = true, example = "0")
    @NotNull(message = "租金类型不能为空")
    @Pattern(regexp = "^[0-3]$", message = "租金类型必须是0, 1, 2, 或 3")
    private String rentType;

    @ApiModelProperty(value = "房间面积（平方米）", required = true, example = "80.00")
    @NotNull(message = "房间面积（平方米）不能为空")
    @DecimalMin(value = "0.0", inclusive = false, message = "面积必须大于0")
    private Double squareMeasure;

    @ApiModelProperty(value = "租赁状态（0在租 1未租）", required = false, example = "0")
    @Pattern(regexp = "^[01]$", message = "租赁状态只能是0或1")
    private String rentalStatus;

    @ApiModelProperty(value = "上架状态（0上架 1下架）", required = true)
    @NotNull(message = "上架状态不能为空")
    @Pattern(regexp = "^[0-1]$", message = "上架状态必须是 0 或 1")
    private String shelfStatus;

    @ApiModelProperty(value = "上架时间", required = true)
    private Date shelfTime;

}

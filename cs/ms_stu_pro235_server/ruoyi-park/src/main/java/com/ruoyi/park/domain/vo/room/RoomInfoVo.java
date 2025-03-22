package com.ruoyi.park.domain.vo.room;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author hezining
 * @date 2025/3/9 18:40
 * @description: 房间详情信息VO
 */

@Data
@Accessors(chain = true)
public class RoomInfoVo {

    @ApiModelProperty(value = "房间ID", required = true, example = "1")
    @NotNull(message = "房间ID不能为空")
    private Long roomId;

    @ApiModelProperty(value = "园区ID", required = true, example = "1")
    @NotNull(message = "园区ID不能为空")
    private Long zoneId;

    @ApiModelProperty(value = "楼宇ID", required = true, example = "1")
    @NotNull(message = "楼宇ID不能为空")
    private Long buildingId;

    @ApiModelProperty(value = "楼层ID", required = true, example = "1")
    @NotNull(message = "楼层ID不能为空")
    private Long floorId;

    @ApiModelProperty(value = "房间名称", required = true, example = "A101")
    @NotBlank(message = "房间名称不能为空")
    private String roomName;

    @ApiModelProperty(value = "租金", required = true, example = "2000.00")
    @NotNull(message = "租金不能为空")
    @DecimalMin(value = "0.0", inclusive = false, message = "租金必须大于0")
    private Double rent;

    @ApiModelProperty(value = "租金类型（0(元/㎡/天) 1(元/㎡/月) 2(元/天) 3(元/月)）", required = true, example = "0")
    @NotNull(message = "租金类型不能为空")
    @Pattern(regexp = "^[0-3]$", message = "租金类型必须是0, 1, 2, 或 3")
    private String rentType;

    @ApiModelProperty(value = "物业费", required = true, example = "50.00")
    @NotNull(message = "物业费不能为空")
    @DecimalMin(value = "0.0", inclusive = false, message = "物业费必须大于0")
    private Double propertyFee;

    @ApiModelProperty(value = "物业费单位（0(元/㎡/天) 1(元/㎡/月) 2(元/天) 3(元/月)）", required = true, example = "0")
    @NotNull(message = "物业费单位不能为空")
    @Pattern(regexp = "^[0-3]$", message = "物业费单位必须是0, 1, 2, 或 3")
    private String propertyUnit;

    @ApiModelProperty(value = "房间面积（平方米）", required = true, example = "80.00")
    @NotNull(message = "房间面积（平方米）不能为空")
    @DecimalMin(value = "0.0", inclusive = false, message = "面积必须大于0")
    private Double squareMeasure;

    @ApiModelProperty(value = "房型（0loft 1商业配套 2办公场所 3其他）", required = true, example = "0")
    @NotBlank(message = "房型不能为空")
    @Pattern(regexp = "^[0-3]$", message = "房型必须是0, 1, 2, 或 3")
    private String houseType;

    @ApiModelProperty(value = "层高（米）", required = false, example = "3.0")
    @DecimalMin(value = "0.0", inclusive = false, message = "层高必须大于0")
    private Double height;

    @ApiModelProperty(value = "装修类型（0简装 1精装 2毛坯房）", required = true, example = "0")
    @NotBlank(message = "装修类型不能为空")
    @Pattern(regexp = "^[0-2]$", message = "房型必须是0, 1, 或 2")
    private String decorationType;

    @ApiModelProperty(value = "房间图片URL", required = false)
    private String roomImg;

    @ApiModelProperty(value = "房间简介", required = false)
    private String roomIntroduction;

    @ApiModelProperty(value = "租赁状态（0在租 1未租）", required = false, example = "0")
    @Pattern(regexp = "^[01]$", message = "租赁状态只能是0或1")
    private String rentalStatus;
}

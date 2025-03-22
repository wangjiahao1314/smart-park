package com.ruoyi.park.domain.param.building;

import com.ruoyi.park.domain.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author hezining
 * @date 2025/3/7 16:19
 * @description: 分页查询楼宇参数
 */

@Data
@ApiModel(description = "楼宇列表查询参数对象")
@EqualsAndHashCode(callSuper = true) // 明确指定调用父类的 equals 和 hashCode 方法
public class BuildingListParam extends PageParam {

    @ApiModelProperty(value = "园区名称", example = "科技园区")
    @Size(max = 100, message = "园区名称长度不能超过100个字符")
    private String zoneName;

    @ApiModelProperty(value = "楼宇名称", example = "科技楼")
    @Size(max = 50, message = "楼宇名称长度不能超过50个字符")
    private String buildingName;
}

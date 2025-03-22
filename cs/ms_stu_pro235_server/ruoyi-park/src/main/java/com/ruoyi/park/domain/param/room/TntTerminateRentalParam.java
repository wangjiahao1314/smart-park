package com.ruoyi.park.domain.param.room;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author hezining
 * @date 2025/3/13 12:12
 * @description: 租户端申请退租参数
 */
@Data
@ApiModel("租户端申请退租参数")
public class TntTerminateRentalParam {

    @ApiModelProperty(value = "申请审批时间", required = true, example = "2023-10-01 12:00:00")
    @NotNull(message = "申请审批时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "房间ID", required = true, example = "12345")
    @NotNull(message = "房间ID不能为空")
    private Long roomId;
}

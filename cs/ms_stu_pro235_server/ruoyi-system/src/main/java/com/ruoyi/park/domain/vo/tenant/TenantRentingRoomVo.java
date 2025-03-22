package com.ruoyi.park.domain.vo.tenant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hezining
 * @date 2025/3/2 14:37
 * @description: 租户在租房间Vo
 */

@ApiModel(value = "租户在租房间Vo", description = "租户租房信息返回参数")
@Data
public class TenantRentingRoomVo {

    @ApiModelProperty(value = "房间ID", example = "123")
    private Long roomId;

    @ApiModelProperty(value = "房间名称", example = "A座101室")
    private String roomName;

}
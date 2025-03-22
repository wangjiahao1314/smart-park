package com.ruoyi.park.domain.param.rental;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 * @author hezining
 * @date 2025/3/11 17:13
 * @description: 租赁审批参数
 */

@Data
@ApiModel(description = "租赁审批参数")
public class RentalApprovalParam {

    @ApiModelProperty(value = "租户ID", required = true)
    @NotNull(message = "租户ID不能为空")
    private Long tenantId;

    @ApiModelProperty(value = "房间ID", required = true)
    @NotNull(message = "房间ID不能为空")
    private Long roomId;

    @ApiModelProperty(value = "创建时间（格式：yyyy-MM-dd HH:mm:ss）", required = true)
    @NotBlank(message = "创建时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "审批状态（0待审批 1审批通过 2已拒绝 3已取消）", required = true)
    @Pattern(regexp = "^(0|1|2|3)$", message = "审批状态必须是0, 1, 2, 或 3")
    @NotBlank(message = "审批状态不能为空")
    private String approvalStatus;

    @ApiModelProperty(value = "审批意见", required = false)
    @Size(max = 255, message = "审批意见长度不能超过255个字符")
    private String approvalOpinion;
}

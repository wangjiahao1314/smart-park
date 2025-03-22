package com.ruoyi.park.domain.param.repair;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.page.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author raoce
 * @date 2025/3/9 21:48
 * @description:
 */
@Data
@ApiModel("租户端分页查询报修单实体")
public class TenantRepairListParam {
    @NotNull(message = "分页参数不能为空")
    @ApiModelProperty("分页参数")
    int pageNum;
    @NotNull(message = "分页参数不能为空")
    @ApiModelProperty("分页参数")
    int pageSize;
    @ApiModelProperty("报修单号")
    String repairNo;
    @NotNull(message = "维修状态不能为空")
    @ApiModelProperty("维修状态")
    char status;
}

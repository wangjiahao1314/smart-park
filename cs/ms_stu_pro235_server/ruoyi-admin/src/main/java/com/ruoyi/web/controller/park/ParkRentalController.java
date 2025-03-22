package com.ruoyi.web.controller.park;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.park.domain.param.rental.RentalApprovalParam;
import com.ruoyi.park.domain.param.rental.RentalListParam;
import com.ruoyi.park.domain.vo.rental.RentalListVo;
import com.ruoyi.park.service.IParkTenantRoomService;
import com.ruoyi.park.service.ParkRentalRelatedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hezining
 * @date 2025/3/11 16:59
 * @description: 租赁控制层
 */

@RestController
@RequestMapping("/rental")
@Api(tags = "管理端租赁模块")
@RequiredArgsConstructor
public class ParkRentalController extends BaseController {

    private final IParkTenantRoomService tenantRoomService;

    private final ParkRentalRelatedService rentalRelatedService;

    /**
     * 租赁审批
     *
     * @param param
     * @return
     */
    @PutMapping
    @ApiOperation("租赁审批")
    public R rentalApproval(@Validated @RequestBody RentalApprovalParam param) {
        return R.toR(tenantRoomService.rentalApproval(param), "租赁审批成功", "租赁审批失败");
    }

    /**
     * 分页查询租赁申请列表
     *
     * @param param
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("分页查询租赁申请列表")
    public TableDataInfo<List<RentalListVo>> rentalApplicationList(@Validated RentalListParam param) {
        return rentalRelatedService.rentalApplicationList(param);
    }

}

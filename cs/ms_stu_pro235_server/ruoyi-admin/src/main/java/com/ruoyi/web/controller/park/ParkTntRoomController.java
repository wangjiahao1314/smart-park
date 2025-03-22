package com.ruoyi.web.controller.park;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.park.domain.param.rental.TntCancelApprovalParam;
import com.ruoyi.park.domain.param.room.TntRoomListParam;
import com.ruoyi.park.domain.param.room.TntTerminateRentalParam;
import com.ruoyi.park.domain.vo.room.TntRoomListVo;
import com.ruoyi.park.service.IParkRoomRelatedService;
import com.ruoyi.park.service.IParkTenantRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hezining
 * @date 2025/3/12 19:07
 * @description: 租户端我的房间
 */

@RestController
@RequestMapping("/tnt/room")
@Api(tags = "租户端我的房间模块")
@RequiredArgsConstructor
public class ParkTntRoomController extends BaseController {

    private final IParkRoomRelatedService roomRelatedService;

    private final IParkTenantRoomService tenantRoomService;


    /**
     * 取消申请审批
     *
     * @param param
     * @return
     */
    @PutMapping("/cancel")
    @ApiOperation("取消申请审批")
    public R cancelApproval(@Validated @RequestBody TntCancelApprovalParam param) {
        return R.toR(tenantRoomService.cancelApproval(param, getUserId()), "取消成功", "取消失败");
    }

    /**
     * 申请退租
     * @return
     */
    @PutMapping("/terminate")
    @ApiOperation("申请退租")
    public R terminateRental(@Validated @RequestBody TntTerminateRentalParam param) {
        return R.toR(roomRelatedService.terminateRental(param, getUserId()), "申请成功", "申请失败");
    }

    /**
     * 分页查询我的房间列表
     *
     * @param param
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("分页查询我的房间列表")
    public TableDataInfo<List<TntRoomListVo>> tntRoomList(@Validated TntRoomListParam param) {
        return roomRelatedService.getTntRoomList(param, getUserId());
    }

}

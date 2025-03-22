package com.ruoyi.web.controller.park;

import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.park.domain.vo.bill.ParkBillListVo;
import com.ruoyi.park.domain.vo.bill.ParkBillSearchVo;
import com.ruoyi.park.service.IParkBillService;
import com.ruoyi.park.service.impl.ParkBillServiceImpl;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bill")
@Api(tags = "账单管理模块")
public class ParkBillController extends BaseController {

    @Autowired
    private IParkBillService parkBillService;

    @ApiOperation("账单列表查询")
    @GetMapping("/list")
    public TableDataInfo<List<ParkBillListVo>> getBills(@Validated ParkBillSearchVo vo, @Validated PageQuery pageQuery) {

        PageInfo<ParkBillListVo> billsWithFilters = parkBillService.getBillsWithFilters(pageQuery.getPageNum(), pageQuery.getPageSize(), vo);

        return getDataTable(billsWithFilters.getList(), billsWithFilters.getTotal());

    }

    @ApiOperation("批量删除账单")
    @DeleteMapping("/batchDelete")
    public R batchDelete(@Validated @RequestBody List<Long> billIds) {
        int i = parkBillService.batchLogicDelete(billIds);
        if (i > 0) {
            return R.ok("删除成功");
        } else {
            return R.fail("删除失败");
        }

    }

    @ApiOperation("批量收款操作")
    @PutMapping("/batchCollectBills")
    public R batchCollectBills(@Validated @RequestBody List<Long> billIds) {
        int i = parkBillService.batchCollectBills(billIds);
        if (i > 0) {
            return R.ok("收款成功");
        } else {
            return R.fail("收款失败");
        }
    }
}

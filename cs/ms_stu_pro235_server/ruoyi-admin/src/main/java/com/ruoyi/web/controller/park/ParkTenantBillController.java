package com.ruoyi.web.controller.park;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.park.domain.vo.bill.AdminBillListVo;
import com.ruoyi.park.domain.vo.bill.AdminBillSearchVo;
import com.ruoyi.park.domain.vo.bill.ParkBillListVo;
import com.ruoyi.park.domain.vo.bill.ParkBillSearchVo;
import com.ruoyi.park.service.impl.IParkContractBillServicl;
import com.ruoyi.park.service.impl.ParkBillServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "租户账单")
@RequestMapping("/admin/tenantBill")
public class ParkTenantBillController extends BaseController {

    @Autowired
    private ParkBillServiceImpl parkBillService;

    @Autowired
    private IParkContractBillServicl parkContractBillService;

    @GetMapping("/list")
    @ApiOperation(value = "获取账单列表")
    public TableDataInfo<List<AdminBillListVo>> getBillsByTenantId(@Validated AdminBillSearchVo vo, PageQuery page) {

        Page<AdminBillListVo> adminPage = parkContractBillService.toAdminPage(vo, page);
        return getDataTable(adminPage.getRecords(), adminPage.getTotal());


    }


}

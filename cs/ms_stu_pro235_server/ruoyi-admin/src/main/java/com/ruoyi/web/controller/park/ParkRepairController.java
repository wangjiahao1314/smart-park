package com.ruoyi.web.controller.park;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;

import com.ruoyi.park.domain.entity.ParkRepair;
import com.ruoyi.park.domain.param.repair.*;
import com.ruoyi.park.domain.vo.repair.*;
import com.ruoyi.park.mapping.ParkRepairMapping;
import com.ruoyi.park.service.IParkRepairService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * @author raoce
 * @date 2025/2/27 21:45
 * @description: 报修模块
 */
@RestController
@RequestMapping("/repair")
@Api(tags = "报修模块")
@RequiredArgsConstructor
public class ParkRepairController extends BaseController {
    @Autowired
    private IParkRepairService repairService;



    /**
     * 分页查询
     * @param workerRepairParam 分页参数

     * @return
     */
    @GetMapping("/worker")
    @ApiOperation("维修端-维修单分页查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "repairNo" ,value = "工单号"),
            @ApiImplicitParam(name = "status",value = "状态") }
            )
    public TableDataInfo<List<RepairListVo>> repairList(@Validated WorkerRepairParam workerRepairParam){

        Page page = repairService.selectAllList(workerRepairParam);
        return getDataTable(ParkRepairMapping.INSTANCE.toRepairList(page.getRecords()),page.getTotal());
    }

    /**
     * 维修端-查询维修单详情
     * @param repairId 维修单id
     * @return
     */
    @GetMapping("/detail")
    @ApiOperation("维修端-维修单查询详情")
    @ApiImplicitParam(name = "repairId" ,value = "报修id")
    public AjaxResult workSheetDetail(@Validated Long repairId){
        return repairService.workSheetDetail(repairId);
    }

    /**
     * 租户端-查询我的保修
     * @param param 租户端分页查询报修单参数
     * @return+
     */
    @GetMapping("/tenant")
    @ApiOperation("租户端-报修单分页查询")
    public TableDataInfo <TenantRepairListVo>selectList(@Validated  TenantRepairListParam param){
        List list  = repairService.selectList(param);
        if (list ==null)
            return getDataTable("查询失败！");
        return getDataTable(ParkRepairMapping.INSTANCE.toList(list),(long)list.size());
    }

    /**
     * 租户端-修改报修单
     * @param tenantRepairUpdateParam 修改参数
     * @return
     */
    @PutMapping("/update")
    @ApiOperation("租户端-修改报修单")
    public AjaxResult updateRepair(@Validated @RequestBody TenantRepairUpdateParam tenantRepairUpdateParam) throws ParseException {
        return repairService.updateRepair(tenantRepairUpdateParam);
    }

    /**
     * 租户端-取消报修
     * @param repairId 报修id
     * @return
     */
    @DeleteMapping
    @ApiOperation("租户端-取消报修")
    @ApiImplicitParam(name = "repairId",value = "报修id")
    public AjaxResult cancelRepair(Long repairId){
        return repairService.cancelRepair(repairId);
    }
    @ApiOperation("租户端-新建报修")
    @PostMapping("/add")
    public AjaxResult addRepair(TenantRepairAddParam tenantRepairAddParam) throws ParseException {
        return repairService.tenantAddRepair(tenantRepairAddParam);
    }
    /**
     * 租户端-查询工单详情
     * @param repairId 报修单id
     * @return
     */
    @GetMapping("/tenantDtl")
    @ApiOperation("租户端-查询工单详情")
    @ApiImplicitParam(name = "repairId" ,value = "报修id")
    AjaxResult tenantRepairDtl(Long repairId){
        return AjaxResult.success(repairService.tenantRepairDtl(repairId));
    }


    /**
     * 管理端-分页查询工单
     * @param param
     * @return
     */
    @ApiOperation("管理端-分页查询工单")
    @GetMapping("/manageRepair")
    public TableDataInfo<ManageRepairVo> manageRepairList(@Validated ManageRepairParam param){
        List list = repairService.manageRepairList(param);
        return getDataTable(ParkRepairMapping.INSTANCE.toRepairList(list),(long) list.size());
    }

    /**
     * 管理端-查询工单详情
     * repairId 报修单id
     * @return
     */
    @ApiOperation("管理端-查询工单详情")
    @GetMapping("/manageRepairDtl")
    @ApiImplicitParam(name = "repairId",value = "报修Id")
    public AjaxResult <ManageRepairDtlVo> manageRepairDtl(Long repairId){
        return AjaxResult.success(repairService.manageRepairDtl(repairId));
    }
    @ApiOperation("指派返回维修工列表")
    @GetMapping("/workerList")
    public AjaxResult workerList(){
        return AjaxResult.success(repairService.workerList());
    }


    /**
     * 管理端-指派维修工
     * @param assignWorkerParam 参数
     * @return
     */
    @PutMapping("/assign")
    @ApiOperation("指派维修工")
    public AjaxResult assignWorker(AssignWorkerParam assignWorkerParam){
        return repairService.assignWorker(assignWorkerParam);
    }
}

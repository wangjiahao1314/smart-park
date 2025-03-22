package com.ruoyi.park.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.park.domain.entity.ParkRepair;
import com.ruoyi.park.domain.param.repair.*;
import com.ruoyi.park.domain.vo.repair.ManageRepairDtlVo;
import com.ruoyi.park.domain.vo.repair.TenantRepairDtlVo;
import com.ruoyi.park.domain.vo.repair.WorkSheetDetailVo;

import java.text.ParseException;
import java.util.List;

/**
 * @author raoce
 * @date 2025/3/2 19:08
 * @description: 工单管理服务层
 */
public interface IParkRepairService extends IService<ParkRepair> {
    /**
     * 维修端-查询工单列表
     * @param workerRepairParam 分页信息

     * @return
     */
    Page<ParkRepair> selectAllList(WorkerRepairParam workerRepairParam);

    /**
     * 维修端-查询工单详情
     * @return
     */
    AjaxResult workSheetDetail(Long repairId);


    /**
     * 租户段-查询报修列表
     * @param param 分页参数
     * @return
     */
    List selectList(TenantRepairListParam param);

    /**
     * 租户端-修改报修单
     * @param tenantRepairUpdateParam 修改参数
     * @return
     */
    AjaxResult updateRepair(TenantRepairUpdateParam tenantRepairUpdateParam) throws ParseException;

    /**
     * 租户端-取消报修
     * @param repairId
     * @return
     */
    AjaxResult cancelRepair(Long repairId);

    /**
     * 租户端-查询工单详情
     * @param repairId
     * @return
     */
    TenantRepairDtlVo tenantRepairDtl(Long repairId);

    /**
     * 租户端-新建报修
     * @param tenantRepairAddParam 新建信息
     * @return
     */
    AjaxResult tenantAddRepair(TenantRepairAddParam tenantRepairAddParam) throws ParseException;

    /**
     * 管理段-查询报修列表
     * @param manageRepairParam 分页参数
     * @return
     */
    List manageRepairList(ManageRepairParam manageRepairParam);

    /**
     * 管理端-查询工单详情
     * @param repairId
     * @return
     */
    ManageRepairDtlVo manageRepairDtl(Long repairId);

    /**
     * 返回维修工列表
     * @return
     */
    List workerList();
    /**
     * 管理端-指派维修工
     * @param assignWorkerParam 参数
     * @return
     */
    AjaxResult assignWorker(AssignWorkerParam assignWorkerParam );
}

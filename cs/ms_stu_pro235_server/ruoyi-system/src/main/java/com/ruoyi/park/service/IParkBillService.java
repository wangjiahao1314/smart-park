package com.ruoyi.park.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.park.domain.entity.ParkBill;
import com.ruoyi.park.domain.vo.bill.AdminBillListVo;
import com.ruoyi.park.domain.vo.bill.AdminBillSearchVo;
import com.ruoyi.park.domain.vo.bill.ParkBillListVo;
import com.ruoyi.park.domain.vo.bill.ParkBillSearchVo;

import java.util.List;

/**
 * @author hezining
 * @date 2025/3/2 20:15
 * @description: 账单服务层接口
 */
public interface IParkBillService extends IService<ParkBill> {

    public PageInfo<ParkBillListVo> getBillsWithFilters(
            int pageNum,
            int pageSize,
            ParkBillSearchVo searchVO
    );
    public int batchLogicDelete(List<Long> billIds);

    public int batchCollectBills(List<Long> billIds);



}
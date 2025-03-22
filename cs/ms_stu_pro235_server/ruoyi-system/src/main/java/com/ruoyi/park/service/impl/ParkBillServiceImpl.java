package com.ruoyi.park.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.ruoyi.park.domain.entity.ParkBill;
import com.ruoyi.park.domain.vo.bill.ParkBillListVo;
import com.ruoyi.park.domain.vo.bill.ParkBillSearchVo;
import com.ruoyi.park.mapper.ParkBillMapper;
import com.ruoyi.park.service.IParkBillService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


/**
 * @author hezining
 * @date 2025/3/2 20:15
 * @description: 账单服务层实现类
 */

@Service
public class ParkBillServiceImpl extends ServiceImpl<ParkBillMapper, ParkBill>
        implements IParkBillService {
    @Autowired
    private ParkBillMapper parkBillMapper;


    public PageInfo<ParkBillListVo> getBillsWithFilters(
            int pageNum,
            int pageSize,
            ParkBillSearchVo searchVO
    ) {
        PageHelper.startPage(pageNum, pageSize);
        List<ParkBillListVo> list = parkBillMapper.selectBillsWithContractDetails(searchVO);
        return new PageInfo<>(list);
    }

    /**
     * 批量删除
     *
     * @param billIds
     * @return
     */
    @Override
    @Transactional
    public int batchLogicDelete(List<Long> billIds) {
        return parkBillMapper.batchLogicDelete(billIds);
    }


    /**
     * 批量收款操作
     *
     * @param billIds 账单ID列表
     * @return 更新的记录数
     */
    @Override
    @Transactional
    public int batchCollectBills(List<Long> billIds) {
        return parkBillMapper.batchCollectBills(billIds);
    }


}

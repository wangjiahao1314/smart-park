package com.ruoyi.park.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.park.domain.entity.ParkBill;
import com.ruoyi.park.domain.entity.ParkContract;
import com.ruoyi.park.domain.vo.bill.AdminBillListVo;
import com.ruoyi.park.domain.vo.bill.AdminBillSearchVo;
import com.ruoyi.park.mapper.ParkBillMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class IParkContractBillServicl {


    @Autowired
    private ParkBillMapper parkBillMapper;
    @Autowired
    private ParkContractServiceImpl contractService;
    public Page<AdminBillListVo> toAdminPage(AdminBillSearchVo vo, PageQuery page) {
        // 创建一个LambdaQueryWrapper对象，用于构建查询条件
        LambdaQueryWrapper<ParkBill> queryWrapper = Wrappers.lambdaQuery();


        // 动态添加查询条件
        queryWrapper
                // 如果vo中的BillMonth不为空，则添加模糊查询条件
                .like(vo.getBillMonth() != null, ParkBill::getBillMonth, vo.getBillMonth())
                // 如果vo中的ContractNo不为空，则添加模糊查询条件
                .like(vo.getContractNo() != null, ParkBill::getBillNo, vo.getContractNo())
                // 如果vo中的BillStatus不为空，则添加等于查询条件
                .eq(vo.getBillStatus() != null, ParkBill::getBillStatus, vo.getBillStatus())
                // 如果vo中的BillOvertime不为空，则添加等于查询条件
                .eq(vo.getBillOvertime() != null, ParkBill::getBillOvertime, vo.getBillOvertime());


        // 添加删除标志为0的查询条件
        queryWrapper.eq(ParkBill::getDelFlag, "0");
        // 如果vo中的SortOrder为asc，则按创建时间升序排序
        if (vo.getSortOrder() != null && vo.getSortOrder().equals("asc")) {
            queryWrapper.orderByAsc(ParkBill::getCreateTime);
        }
        // 如果vo中的SortOrder为desc，则按创建时间降序排序
        if (vo.getSortOrder() != null && vo.getSortOrder().equals("desc")) {
            queryWrapper.orderByDesc(ParkBill::getCreateTime);
        }
        // 执行查询，获取符合条件的ParkBill列表
        List<ParkBill> parkBills = parkBillMapper.selectList(queryWrapper);

        // 将查询结果转换为视图对象列表
        List<AdminBillListVo> list = parkBills.stream().map(s -> {
                    // 创建一个新的ParkBillListVo对象
                    AdminBillListVo view = new AdminBillListVo();
                    // 将ParkBill对象的属性复制到ParkBillListVo对象中
                    BeanUtils.copyProperties(s, view);
                    // 根据合同ID获取合同信息
                    ParkContract byId = contractService.getById(s.getContractId());
                    // 将合同信息复制到ParkBillListVo对象中
                    BeanUtils.copyProperties(byId, view);

                    return view;
                }
        ).collect(Collectors.toList());
        // 创建一个新的Page对象，用于分页
        Page<AdminBillListVo> page1 = new Page<>(page.getPageNum(), page.getPageSize());

        // 设置分页结果
        page1.setRecords(list);
        return page1;

    }
}

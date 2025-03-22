package com.ruoyi.park.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.ContractType;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.constant.RentalStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.park.domain.entity.ParkContract;
import com.ruoyi.park.domain.entity.ParkTenant;
import com.ruoyi.park.domain.param.rental.RentalListParam;
import com.ruoyi.park.domain.param.rental.TntCancelApprovalParam;
import com.ruoyi.park.domain.vo.rental.RentalListVo;
import com.ruoyi.park.mapper.ParkContractMapper;
import com.ruoyi.park.mapper.ParkTenantRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author hezining
 * @date 2025/3/12 18:09
 * @description: 租赁申请相关服务层
 */

@Service
@RequiredArgsConstructor
public class ParkRentalRelatedService {

    private final ParkTenantRoomMapper tenantRoomMapper;

    private final ParkContractMapper contractMapper;

    /**
     * 分页查询租赁申请列表
     *
     * @param param
     * @return
     */
    public TableDataInfo<List<RentalListVo>> rentalApplicationList(RentalListParam param) {
        // 构造查询条件
        QueryWrapper<RentalListVo> qw = new QueryWrapper<>();
        qw.eq("t1.del_flag", UserStatus.OK.getCode())
                .like(StringUtils.isNotEmpty(param.getNickName()), "t3.nick_name", param.getNickName())
                .like(StringUtils.isNotEmpty(param.getPhonenumber()), "t3.phonenumber", param.getPhonenumber())
                .eq(StringUtils.isNotEmpty(param.getApprovalStatus()), "t1.approval_status", param.getApprovalStatus());
        // 查询列表
        Page<RentalListVo> rentalPage = tenantRoomMapper.selectRentalApplicationList(new Page<>(param.getPageNum(), param.getPageSize()), qw);
        List<RentalListVo> records = rentalPage.getRecords();
        // 设置RentalListVos的租赁状态
        setRentalListVosRentalStatus(records);
        return new TableDataInfo<>(records, (int) rentalPage.getTotal(), HttpStatus.SUCCESS, "查询成功");
    }

    /**
     * 设置RentalListVos的租赁状态
     *
     * @param listVos
     * @return
     */
    private void setRentalListVosRentalStatus(List<RentalListVo> listVos) {
        // 查找房间对应合同，修改租赁状态
        for (RentalListVo vo : listVos) {

            // 查询房间对应合同的状态列表
            List<String> typeList = contractMapper.selectContractTypeByRoomId(vo.getRoomId());

            // 执行中或者申请退款，则租赁状态为在租
            if (typeList.contains(ContractType.IN_PROGRESS) || typeList.contains(ContractType.APPLY_TERMINATED)) {
                vo.setRentalStatus(RentalStatus.RENTING);
            } else {
                // 否则为未租
                vo.setRentalStatus(RentalStatus.NOT_RENTED);
            }

        }

    }

}

package com.ruoyi.park.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.park.domain.entity.ParkTenant;
import com.ruoyi.park.domain.entity.ParkTenantRoom;
import com.ruoyi.park.domain.param.rental.RentalApprovalParam;
import com.ruoyi.park.domain.param.rental.TntCancelApprovalParam;
import com.ruoyi.park.mapper.ParkTenantMapper;
import com.ruoyi.park.mapper.ParkTenantRoomMapper;
import com.ruoyi.park.service.IParkTenantRoomService;
import com.ruoyi.park.service.IParkTenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author hezining
 * @date 2025/3/11 17:25
 * @description: 租户房间（租赁审批）服务层
 */
@Service
@RequiredArgsConstructor
public class ParkTenantRoomServiceImpl extends ServiceImpl<ParkTenantRoomMapper, ParkTenantRoom>
        implements IParkTenantRoomService {

    private final ParkTenantRoomMapper tenantRoomMapper;

    private final IParkTenantService tenantService;

    /**
     * 租赁审批
     *
     * @param param
     * @return
     */
    @Override
    public Boolean rentalApproval(RentalApprovalParam param) {

        // 找到对应租赁记录
        ParkTenantRoom rentalRecord = getRentalRecord(param.getTenantId(), param.getRoomId(), param.getCreateTime());

        if (rentalRecord == null) {
            throw new ServiceException("此租赁信息不存在");
        }
        return update(rentalRecord.setApprovalStatus(param.getApprovalStatus()).setApprovalOpinion(param.getApprovalOpinion()), getRentalRecordWrapper(param.getTenantId(), param.getRoomId(), param.getCreateTime()));
    }

    /**
     * 取消申请审批
     *
     * @param param
     * @param userId
     * @return
     */
    public int cancelApproval(TntCancelApprovalParam param, Long userId) {
        // 判断是否可以取消审批
        checkCancelApproval(param.getRoomId(), param.getCreateTime(), userId);

        Long tenantId = tenantService.lambdaQuery().eq(ParkTenant::getUserId, userId)
                .eq(ParkTenant::getDelFlag, UserStatus.OK.getCode()).one().getTenantId();
        return tenantRoomMapper.cancelApproval(param, tenantId);
    }

    /**
     * 判断是否可以取消审批
     *
     * @param roomId
     * @param createTime
     * @param userId
     */
    private void checkCancelApproval(Long roomId, Date createTime, Long userId) {
        // 根据租赁主键获取租赁信息
        ParkTenantRoom rentalRecord = getRentalRecord(tenantService.lambdaQuery().eq(ParkTenant::getUserId, userId).one().getTenantId(), roomId, createTime);
        if (rentalRecord == null) {
            throw new ServiceException("该租赁信息不存在");
        }
        // 如果有对应合同信息，则无法取消
        if (rentalRecord.getContractId() != null) {
            throw new ServiceException("存在合同信息，无法取消");
        }
    }

    /**
     * 根据租赁主键获取租赁信息
     *
     * @param tenantId
     * @param roomId
     * @param createTime
     */
    private ParkTenantRoom getRentalRecord(Long tenantId, Long roomId, Date createTime) {
        LambdaQueryWrapper<ParkTenantRoom> lqw = getRentalRecordWrapper(tenantId, roomId, createTime);
        return tenantRoomMapper.selectOne(lqw);
    }

    /**
     * 获取租赁记录wrapper
     *
     * @param tenantId
     * @param roomId
     * @param createTime
     * @return
     */
    private LambdaQueryWrapper<ParkTenantRoom> getRentalRecordWrapper(Long tenantId, Long roomId, Date createTime) {
        LambdaQueryWrapper<ParkTenantRoom> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ParkTenantRoom::getTenantId, tenantId)
                .eq(ParkTenantRoom::getRoomId, roomId)
                .eq(ParkTenantRoom::getCreateTime, createTime);
        return lqw;
    }


}

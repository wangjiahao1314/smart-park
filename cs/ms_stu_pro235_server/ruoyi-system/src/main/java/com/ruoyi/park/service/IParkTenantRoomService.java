package com.ruoyi.park.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.park.domain.entity.ParkTenantRoom;
import com.ruoyi.park.domain.param.rental.RentalApprovalParam;
import com.ruoyi.park.domain.param.rental.TntCancelApprovalParam;

/**
 * @author hezining
 * @date 2025/3/11 17:24
 * @description: 租户房间（租赁审批）服务层
 */
public interface IParkTenantRoomService extends IService<ParkTenantRoom> {

    /**
     * 租赁审批
     *
     * @param param
     * @return
     */
    public Boolean rentalApproval(RentalApprovalParam param);

    /**
     * 取消申请审批
     *
     * @param param
     * @param userId
     * @return
     */
    public int cancelApproval(TntCancelApprovalParam param, Long userId);

}

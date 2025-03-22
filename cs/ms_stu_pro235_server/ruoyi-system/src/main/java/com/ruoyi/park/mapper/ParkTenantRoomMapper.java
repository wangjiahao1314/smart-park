package com.ruoyi.park.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.ApprovalStatus;
import com.ruoyi.common.constant.HouseType;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.park.domain.entity.ParkTenantRoom;
import com.ruoyi.park.domain.param.rental.TntCancelApprovalParam;
import com.ruoyi.park.domain.vo.rental.RentalListVo;
import com.ruoyi.park.domain.vo.tenant.TenantRentingRoomVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hezining
 * @date 2025/3/2 11:17
 * @description: 租户房间持久层接口
 */
public interface ParkTenantRoomMapper extends BaseMapper<ParkTenantRoom> {


    /**
     * 查询租户在租房间信息
     *
     * @param tenantId
     * @return
     */
    List<TenantRentingRoomVo> selectRentingRoom(Long tenantId);

    /**
     * 查询租户申请房间信息
     *
     * @param tenantIds
     * @return
     */
    List<TenantRentingRoomVo> selectRoomsByTenantIds(@Param("tenantIds") List<Long> tenantIds);

    /**
     * 通过房间ID查询对应合同ID
     *
     * @param roomIds
     * @return
     */
    default List<Long> selectContractIdsByRoomIds(Long[] roomIds) {
        LambdaQueryWrapper<ParkTenantRoom> lqw = new LambdaQueryWrapper<>();
        lqw.in(ParkTenantRoom::getRoomId, roomIds);
        lqw.ne(ParkTenantRoom::getContractId, null);
        return selectList(lqw).stream().map(ParkTenantRoom::getContractId).collect(Collectors.toList());
    }

    /**
     * 新增租户房间表的合同ID
     *
     * @param contractId
     * @param tenantId
     * @param roomId
     * @return
     */
    default int insertContractInfo(Long contractId, Long tenantId, Long roomId) {

        System.out.println("contractId: " + contractId + "tenantId" + tenantId + "roomId" + roomId);
        // 查询最新的租房信息
        LambdaQueryWrapper<ParkTenantRoom> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ParkTenantRoom::getTenantId, tenantId)
                .eq(ParkTenantRoom::getRoomId, roomId)
                .eq(ParkTenantRoom::getDelFlag, UserStatus.OK.getCode())
                .orderByDesc(ParkTenantRoom::getCreateTime)
                .last("LIMIT 1");
        ParkTenantRoom tenantRoom = selectOne(lqw);

        // 插入合同ID
        LambdaQueryWrapper<ParkTenantRoom> updLqw = new LambdaQueryWrapper<>();
        updLqw.eq(ParkTenantRoom::getTenantId, tenantRoom.getTenantId())
                .eq(ParkTenantRoom::getRoomId, tenantRoom.getRoomId())
                .eq(ParkTenantRoom::getCreateTime, tenantRoom.getCreateTime());
        return update(tenantRoom.setContractId(contractId), updLqw);
    }

    /**
     * 分页查询租赁申请列表
     *
     * @param page
     * @param wrapper
     * @return
     */
    Page<RentalListVo> selectRentalApplicationList(@Param("page") Page<RentalListVo> page, @Param(Constants.WRAPPER) Wrapper<RentalListVo> wrapper);

    default int cancelApproval(TntCancelApprovalParam param, Long tenantId) {
        LambdaQueryWrapper<ParkTenantRoom> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ParkTenantRoom::getTenantId, tenantId)
                .eq(ParkTenantRoom::getCreateTime, param.getCreateTime())
                .eq(ParkTenantRoom::getRoomId, param.getRoomId());
        ParkTenantRoom tenantRoom = selectOne(lqw);
        // 取消审批
        tenantRoom.setApprovalStatus(ApprovalStatus.CANCELLED);
        tenantRoom.setHouseType(HouseType.HOUSE_STATUS_CANCELLED);
        return update(tenantRoom, lqw);
    }
}

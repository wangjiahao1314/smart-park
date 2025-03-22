package com.ruoyi.park.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.ContractType;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.constant.RentalStatus;
import com.ruoyi.common.constant.ShelfStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.park.domain.entity.*;
import com.ruoyi.park.domain.param.room.*;
import com.ruoyi.park.domain.vo.contract.LatestContractInfo;
import com.ruoyi.park.domain.vo.room.*;
import com.ruoyi.park.mapper.*;
import com.ruoyi.park.mapping.ParkBuildingMapping;
import com.ruoyi.park.mapping.ParkFloorMapping;
import com.ruoyi.park.mapping.ParkRoomMapping;
import com.ruoyi.park.service.IParkBuildingService;
import com.ruoyi.park.service.IParkRoomRelatedService;
import com.ruoyi.park.service.IParkRoomService;
import com.ruoyi.park.service.IParkTenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author hezining
 * @date 2025/3/9 11:02
 * @description: 房间相关的服务层实现类
 */
@Service
@RequiredArgsConstructor
public class ParkRoomRelatedServiceImpl implements IParkRoomRelatedService {

    private final IParkRoomService roomService;

    private final ParkRoomMapper roomMapper;

    private final ParkTenantRoomMapper tenantRoomMapper;

    private final ParkContractMapper contractMapper;

    private final ParkZoneMapper zoneMapper;

    private final IParkBuildingService buildingService;

    private final ParkFloorMapper floorMapper;

    private final IParkTenantService tenantService;

    /**
     * 批量删除房间
     *
     * @param roomIds
     * @return
     */
    @Override
    public Boolean removeRoom(Long[] roomIds) {
        //检验房间是否可删除
        checkRoomRemovable(roomIds);

        return roomService.removeBatchByIds(Arrays.asList(roomIds));
    }

    /**
     * 分页查询房间信息（列表）
     *
     * @param param
     * @return
     */
    @Override
    public TableDataInfo<RoomListVo> getPageRoomList(RoomListParam param) {

        // 创建分页条件
        Page<RoomListVo> roomPage = new Page<>(param.getPageNum(), param.getPageSize());
        QueryWrapper<RoomListVo> qw = new QueryWrapper<>();
        qw.like(StringUtils.isNotEmpty(param.getRoomName()), "r.room_name", param.getRoomName())
                .eq(param.getZoneId() != null, "r.zone_id", param.getZoneId())
                .like(StringUtils.isNotEmpty(param.getBuildingName()), "b.building_name", param.getBuildingName())
                .eq("r.del_flag", UserStatus.OK.getCode())
                .eq("b.del_flag", UserStatus.OK.getCode());
        // 查询房间分页列表
        Page<RoomListVo> page = roomMapper.pageSelectRoomList(roomPage, qw);

        // 得到房间记录，并设置租赁状态，转换为VO返回
        return new TableDataInfo(
                toRoomVoListAndSetRentalStatus(page.getRecords(), param.getRentalStatus()), (int) page.getTotal(),
                HttpStatus.SUCCESS, "查询成功");
    }

    /**
     * 查询房间房态列表
     *
     * @param param
     * @return
     */
    @Override
    public RoomStatusInfoVo roomStatusList(RoomStatusListParam param) {
        RoomStatusInfoVo infoVo = new RoomStatusInfoVo();

        // 查询园区信息,并设置园区信息
        ParkZone zone = zoneMapper.selectById(param.getZoneId());
        if (zone == null) {
            throw new RuntimeException("未找到对应的园区信息");
        }
        infoVo.setZoneName(zone.getZoneName()).setZoneId(zone.getZoneId());

        // 设置园区对应楼宇信息
        List<ParkBuilding> buildings = buildingService.lambdaQuery()
                .eq(ParkBuilding::getZoneId, zone.getZoneId())
                .like(StringUtils.isNotEmpty(param.getBuildingName()), ParkBuilding::getBuildingName, param.getBuildingName())
                .list();
        infoVo.setBuildingList(ParkBuildingMapping.INSTANCE.toVoList(buildings));

        // 设置楼宇和楼层对应的房间信息
        for (RoomStatusBuildingVo buildingVo : infoVo.getBuildingList()) {
            // 获取buildingId对应的楼层列表
            List<ParkFloor> floors = floorMapper.selectList(new LambdaQueryWrapper<ParkFloor>()
                    .eq(ParkFloor::getBuildingId, buildingVo.getBuildingId()));
            List<RoomStatusFloorVo> floorVoList = ParkFloorMapping.INSTANCE.toVoList(floors);

            for (RoomStatusFloorVo floorVo : floorVoList) {
                // 获取floorId对应的房间列表
                List<ParkRoom> rooms = roomService.lambdaQuery()
                        .eq(ParkRoom::getFloorId, floorVo.getFloorId())
                        .like(StringUtils.isNotEmpty(param.getRoomName()), ParkRoom::getRoomName, param.getRoomName())
                        .list();

                // 转换房间实体为VO对象
                List<RoomStatusRoomVo> roomVoList = ParkRoomMapping.INSTANCE.toVoList(rooms);

                // 设置房间租赁状态和空闲时间
                setRentalStatusAndIdleDays(roomVoList);

                // 根据租赁状态过滤房间列表
                if (StringUtils.isNotEmpty(param.getRentalStatus())) {
                    roomVoList.removeIf(room -> !room.getRentalStatus().equals(param.getRentalStatus()));
                }

                floorVo.setRoomList(roomVoList);
            }
            buildingVo.setFloorList(floorVoList);
        }

        return infoVo;
    }

    /**
     * 编辑上架状态
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean editShelfTime(EditShelfStatusParam param) {
        ParkRoom room = new ParkRoom().setRoomId(param.getRoomId()).setShelfStatus(param.getShelfStatus());
        if (ShelfStatus.SHELF.equals(param.getShelfStatus())) {
            room.setShelfTime(new Date());
        }
        List<String> typeList = contractMapper.selectContractTypeByRoomId(param.getRoomId());
        // 状态修改为上架逻辑, 合同状态待签订，在执行，或者申请退租时无法上架
        if (ShelfStatus.SHELF.equals(param.getShelfStatus()) && (typeList.contains(ContractType.WAITING_SIGNATURE) || typeList.contains(ContractType.IN_PROGRESS) || typeList.contains(ContractType.APPLY_TERMINATED))) {
            throw new ServiceException("该房间出租中，无法上架");
        }
        return roomService.updateById(room);
    }

    /**
     * 分页获取租户端我的房间列表
     *
     * @param param
     * @return
     */
    @Override
    public TableDataInfo<List<TntRoomListVo>> getTntRoomList(TntRoomListParam param, Long userId) {
        QueryWrapper<TntRoomListVo> qw = new QueryWrapper<>();
        qw.eq("t1.del_flag", UserStatus.OK.getCode())
                .eq("t3.user_id", userId)
                .like(StringUtils.isNotEmpty(param.getZoneName()), "t7.zone_name", param.getZoneName())
                .like(StringUtils.isNotEmpty(param.getBuildingName()), "t6.building_name", param.getBuildingName())
                .like(StringUtils.isNotEmpty(param.getRoomName()), "t4.room_name", param.getRoomName());

        Page<TntRoomListVo> page = roomMapper.getTntRoomList(new Page<TntRoomListVo>(param.getPageNum(), param.getPageSize()), qw);
        return new TableDataInfo<>(page.getRecords(), (int) page.getTotal(), HttpStatus.SUCCESS, "查询成功");
    }

    /**
     * 申请退租
     *
     * @param param
     * @param userId
     * @return
     */
    @Override
    public int terminateRental(TntTerminateRentalParam param, Long userId) {
        Long tenantId = tenantService.lambdaQuery().eq(ParkTenant::getUserId, userId).one().getTenantId();

        ParkTenantRoom rentalRecord = tenantRoomMapper.selectOne(getRentalRecordWrapper(tenantId, param.getRoomId(), param.getCreateTime()));
        if (rentalRecord.getContractId() == null) {
            throw new ServiceException("该条审批不存在合同，无法退租");
        }

        // 修改合同为申请退租
        ParkContract contract = contractMapper.selectById(rentalRecord.getContractId());
        if (ContractType.IN_PROGRESS.equals(contract.getContractType())) {
            contract.setContractType(ContractType.APPLY_TERMINATED);
            return contractMapper.updateById(contract);
        } else {
            throw new ServiceException("合同未执行或已申请退租");
        }
    }

    /**
     * 查询房间记录信息
     *
     * @param roomId
     * @return
     */
    @Override
    public List<RoomRecordsVo> getRoomRecords(Long roomId) {
        return roomMapper.getRoomRecords(roomId);
    }

    private LambdaQueryWrapper<ParkTenantRoom> getRentalRecordWrapper(Long tenantId, Long roomId, Date createTime) {
        LambdaQueryWrapper<ParkTenantRoom> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ParkTenantRoom::getTenantId, tenantId)
                .eq(ParkTenantRoom::getRoomId, roomId)
                .eq(ParkTenantRoom::getCreateTime, createTime);
        return lqw;
    }

    /**
     * 检验房间是否可删除
     *
     * @param roomIds
     */
    private void checkRoomRemovable(Long[] roomIds) {
        // 检验传入的roomId是否都存在
        if (!roomService.lambdaQuery().in(ParkRoom::getRoomId, roomIds).count().equals((long) roomIds.length)) {
            throw new ServiceException("删除房间不存在");
        }

        // 房间存在对应合同信息无法删除
        List<Long> contractList = tenantRoomMapper.selectContractIdsByRoomIds(roomIds);
        System.out.println(contractList);
        if (!contractList.isEmpty()) {
            throw new ServiceException("存在合同信息无法删除");
        }
    }

    /**
     * 将房间列表设置租赁状态，并映射成RoomInfoVo列表返回
     *
     * @param roomList
     * @return
     */
    private List<RoomListVo> toRoomVoListAndSetRentalStatus(List<RoomListVo> roomList, String rentalStatus) {
        List<RoomListVo> roomVos = new ArrayList<>();
        // 查找房间对应合同，修改租赁状态
        for (RoomListVo room : roomList) {
            RoomListVo roomListVo = ParkRoomMapping.INSTANCE.toListVo(room);

            // 查询房间对应合同的状态列表
            List<String> typeList = contractMapper.selectContractTypeByRoomId(room.getRoomId());

            // 执行中或者申请退款，则租赁状态为在租
            if (typeList.contains(ContractType.IN_PROGRESS) || typeList.contains(ContractType.APPLY_TERMINATED)) {
                roomListVo.setRentalStatus(RentalStatus.RENTING);
            } else {
                // 否则为未租
                roomListVo.setRentalStatus(RentalStatus.NOT_RENTED);
            }

            // 判断是否有rentalStatus的条件
            if (StringUtils.isEmpty(rentalStatus)) {
                roomVos.add(roomListVo);
            } else if (rentalStatus.equals(roomListVo.getRentalStatus())) {
                roomVos.add(roomListVo);
            }
        }

        return roomVos;
    }

    /**
     * 设置房间的房态VO的租赁状态和空闲时间
     *
     * @param roomVos
     * @return
     */
    private void setRentalStatusAndIdleDays(List<RoomStatusRoomVo> roomVos) {
        // 查找房间对应合同，修改租赁状态
        for (RoomStatusRoomVo roomVo : roomVos) {

            // 查询房间对应合同的状态列表
            List<String> typeList = contractMapper.selectContractTypeByRoomId(roomVo.getRoomId());

            // 执行中或者申请退款，则租赁状态为在租
            if (typeList.contains(ContractType.IN_PROGRESS) || typeList.contains(ContractType.APPLY_TERMINATED)) {
                roomVo.setRentalStatus(RentalStatus.RENTING);
                roomVo.setIdleDays(0);
            } else {
                // 否则为未租
                roomVo.setRentalStatus(RentalStatus.NOT_RENTED);
                // 再设置空闲时间

                // 相差天数
                Integer differDays;
                // 相差毫秒数
                long milliSeconds;
                // 当前Date
                Date currentTime = new Date();

                // 如果有合同记录，且最新的为已终止，则通过退租时间
                LatestContractInfo contractInfo = contractMapper.selectLatestContractInfo(roomVo.getRoomId());
                System.out.println("roomId: " + roomVo.getRoomId() + "   contractType: " + contractInfo.getContractType());
                if (ContractType.TERMINATED.equals(contractInfo.getContractType())) {
                    milliSeconds = Math.abs(currentTime.getTime() - contractInfo.getUpdateTime().getTime());
                    differDays = (int) TimeUnit.DAYS.convert(milliSeconds, TimeUnit.MILLISECONDS);


                } else {
                    // 如果合同最新记录不符合，因为当前状态为未租，所以只有可能是该房间未被租过，
                    // 则计算房间创建时间和当前时间的差
                    milliSeconds = Math.abs(currentTime.getTime() - roomMapper.selectById(roomVo.getRoomId()).getCreateTime().getTime());
                    differDays = (int) TimeUnit.DAYS.convert(milliSeconds, TimeUnit.MILLISECONDS);
                }

                roomVo.setIdleDays(differDays);
            }
        }

    }
}

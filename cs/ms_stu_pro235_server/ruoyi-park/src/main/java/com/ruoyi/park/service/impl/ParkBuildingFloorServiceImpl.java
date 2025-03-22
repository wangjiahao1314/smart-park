package com.ruoyi.park.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.park.domain.entity.ParkBuilding;
import com.ruoyi.park.domain.entity.ParkFloor;
import com.ruoyi.park.domain.param.floor.AddFloorParam;
import com.ruoyi.park.domain.param.floor.EditFloorParam;
import com.ruoyi.park.mapper.ParkBuildingMapper;
import com.ruoyi.park.mapper.ParkRoomMapper;
import com.ruoyi.park.mapping.ParkFloorMapping;
import com.ruoyi.park.service.IParkBuildingFloorService;
import com.ruoyi.park.service.IParkBuildingService;
import com.ruoyi.park.service.IParkFloorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hezining
 * @date 2025/3/8 11:28
 * @description: 楼宇和楼层服务层
 */

@Service
@RequiredArgsConstructor
public class ParkBuildingFloorServiceImpl implements IParkBuildingFloorService {

    private final IParkBuildingService buildingService;
    private final ParkBuildingMapper buildingMapper;
    private final IParkFloorService floorService;
    private final ParkRoomMapper roomMapper;


    /**
     * 新增楼层
     *
     * @param param
     * @return
     */
    @Override
    public Boolean addFloor(AddFloorParam param) {
        // 校验楼层信息合法性
        checkFloorInfo(param.getBuildingId(), null, param.getFloorName(), param.getFloorNum());

        // 新增楼层
        return floorService.save(ParkFloorMapping.INSTANCE.to(param));

    }


    /**
     * 批量删除楼宇
     *
     * @param buildingIds
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeBuilding(Long[] buildingIds) {
        // 判断是否可以删除
        checkBuildingRemovable(buildingIds);

        // 删除楼宇对应的楼层信息
        Long[] floorIds = floorService.lambdaQuery()
                .in(ParkFloor::getBuildingId, buildingIds)
                .list().stream()
                .map(ParkFloor::getFloorId).toArray(Long[]::new);

        // 存在楼层信息则也删除
        if (floorIds != null && floorIds.length > 0) {
            floorService.removeFloor(floorIds);
        }

        // 删除楼宇信息
        return buildingMapper.removeBuilding(buildingIds);
    }

    /**
     * 编辑楼层信息
     *
     * @param param
     * @return
     */
    @Override
    public Boolean editFloor(EditFloorParam param) {
        // 校验楼层信息合法性
        checkFloorInfo(null, param.getFloorId(), param.getFloorName(), param.getFloorNum());

        // 编辑楼层信息
        return floorService.updateById(ParkFloorMapping.INSTANCE.to(param));
    }

    /**
     * 判断楼宇是否可以删除
     *
     * @param buildingIds
     * @return
     */
    private void checkBuildingRemovable(Long[] buildingIds) {
        // 查询删除的楼宇ID是否都存在
        if (!buildingService.lambdaQuery().in(ParkBuilding::getBuildingId, buildingIds).count().equals(Long.valueOf(buildingIds.length))) {
            throw new ServiceException("无法删除不存在楼宇");
        }

        // 通过楼宇ID判断是否存在对应房间
        if (roomMapper.existRoomByBuildingIds(buildingIds)) {
            throw new ServiceException("房间存在对应楼宇信息无法删除");
        }

    }

    /**
     * 校验楼层信息合法性
     *
     * @param buildingId 楼宇ID
     * @param floorId    楼层ID
     * @param floorName  楼层名称
     * @param floorNum   楼层
     */
    private void checkFloorInfo(Long buildingId, Long floorId, String floorName, String floorNum) {
        // 判断楼宇信息
        if (buildingId != null && (!buildingService.lambdaQuery().eq(ParkBuilding::getBuildingId, buildingId).exists())) {
            throw new ServiceException("楼宇信息不存在");
        }

        // 校验楼层信息
        if (floorService.lambdaQuery()
                .eq(ParkFloor::getFloorNum, floorNum)
                .eq(ParkFloor::getFloorName, floorName)
                .ne(floorId != null, ParkFloor::getFloorId, floorId)
                .eq(ParkFloor::getBuildingId, buildingId)
                .exists()) {
            throw new ServiceException("楼层信息重复");
        }

    }
}

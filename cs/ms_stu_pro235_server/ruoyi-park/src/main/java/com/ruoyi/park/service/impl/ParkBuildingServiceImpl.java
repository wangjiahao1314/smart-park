package com.ruoyi.park.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.park.domain.entity.ParkBuilding;
import com.ruoyi.park.domain.entity.ParkFloor;
import com.ruoyi.park.domain.entity.ParkZone;
import com.ruoyi.park.domain.param.building.AddBuildingParam;
import com.ruoyi.park.domain.param.building.BuildingListParam;
import com.ruoyi.park.domain.param.building.EditBuildingParam;
import com.ruoyi.park.domain.vo.building.BuildingInfoVo;
import com.ruoyi.park.domain.vo.building.BuildingListVo;
import com.ruoyi.park.domain.vo.building.BuildingSelectVo;
import com.ruoyi.park.mapper.ParkBuildingMapper;
import com.ruoyi.park.mapper.ParkRoomMapper;
import com.ruoyi.park.mapper.ParkZoneMapper;
import com.ruoyi.park.mapping.ParkBuildingMapping;
import com.ruoyi.park.service.IParkBuildingService;
import com.ruoyi.park.service.IParkFloorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author hezining
 * @date 2025/3/5 20:33
 * @description: 楼宇服务层实现类
 */

@Service
@RequiredArgsConstructor
public class ParkBuildingServiceImpl extends ServiceImpl<ParkBuildingMapper, ParkBuilding>
        implements IParkBuildingService {


    private final ParkBuildingMapper buildingMapper;


    private final ParkZoneMapper zoneMapper;

    /**
     * 新增楼宇信息
     *
     * @param param
     * @return
     */
    @Override
    public int addBuilding(AddBuildingParam param) {
        // 检测楼宇信息是否合法
        checkBuildingInfo(param.getBuildingName(), null, param.getZoneId());

        return buildingMapper.addBuilding(param);
    }



    /**
     * 编辑楼宇信息
     *
     * @param param
     * @return
     */
    @Override
    public int editBuilding(EditBuildingParam param) {
        // 校验楼宇信息是否合法
        checkBuildingInfo(param.getBuildingName(), param.getBuildingId(), param.getZoneId());

        return buildingMapper.updateById(ParkBuildingMapping.INSTANCE.toBuilding(param));

    }

    /**
     * 根据楼宇ID获取楼宇详情
     *
     * @param buildingId
     * @return
     */
    @Override
    public BuildingInfoVo getBuildingInfo(Long buildingId) {
        return ParkBuildingMapping.INSTANCE.toVo(buildingMapper.selectById(buildingId));

    }

    /**
     * 分页查询楼宇列表
     *
     * @param param
     * @return
     */
    @Override
    public List<BuildingListVo> selectBuildingList(BuildingListParam param) {
        return buildingMapper.selectBuildingList(param);
    }

    /**
     * 下拉框查询楼宇
     *
     * @param zoneId
     * @return
     */
    @Override
    public List<BuildingSelectVo> buildingSelect(Long zoneId) {
        return ParkBuildingMapping.INSTANCE.toSelVoList(
                lambdaQuery().eq(zoneId != null, ParkBuilding::getZoneId, zoneId).list()
        );
    }

    /**
     * 检测楼宇信息是否合法
     *
     * @param buildingName 楼宇名称
     * @param buildingId   楼宇ID
     * @param zoneId       园区ID
     * @return
     */
    private void checkBuildingInfo(String buildingName, Long buildingId, Long zoneId) {
        // 楼宇ID校验
        if (buildingId != null && buildingMapper.selectById(buildingId) == null) {
            throw new ServiceException("楼宇信息有误");
        }

        // 判断园区信息是否存在
        if (zoneMapper.selectById(zoneId) == null) {
            throw new ServiceException("园区不存在");
        }
        // 判断楼宇名称是否重复
        if (lambdaQuery()
                .eq(ParkBuilding::getZoneId, zoneId)
                .ne(buildingId != null, ParkBuilding::getBuildingId, buildingId)
                .eq(ParkBuilding::getBuildingName, buildingName)
                .exists()) {
            throw new ServiceException("楼宇名称已存在");
        }


    }



}

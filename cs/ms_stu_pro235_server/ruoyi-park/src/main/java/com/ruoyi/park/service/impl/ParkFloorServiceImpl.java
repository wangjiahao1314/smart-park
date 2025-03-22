package com.ruoyi.park.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.park.domain.entity.ParkFloor;
import com.ruoyi.park.domain.param.floor.EditFloorParam;
import com.ruoyi.park.domain.vo.floor.FloorSelectVo;
import com.ruoyi.park.mapper.ParkFloorMapper;
import com.ruoyi.park.mapper.ParkRoomMapper;
import com.ruoyi.park.mapping.ParkFloorMapping;
import com.ruoyi.park.service.IParkFloorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author hezining
 * @date 2025/3/8 10:13
 * @description: 楼层服务层实现类
 */

@Service
@RequiredArgsConstructor
public class ParkFloorServiceImpl extends ServiceImpl<ParkFloorMapper, ParkFloor>
        implements IParkFloorService {

    private final ParkFloorMapper floorMapper;

    private final ParkRoomMapper roomMapper;


    /**
     * 批量删除楼层
     *
     * @param floorIds
     * @return
     */
    @Override
    public Boolean removeFloor(Long[] floorIds) {
        // 判断楼层是否可删除
        checkFloorRemovable(floorIds);
        // 批量删除楼宇
        return removeBatchByIds(Arrays.asList(floorIds));
    }

    /**
     * 查询楼层列表
     *
     * @param buildingId
     * @return
     */
    @Override
    public List<FloorSelectVo> floorSelect(Long buildingId) {
        return ParkFloorMapping.INSTANCE.toSelectVo(floorMapper.floorSelect(buildingId));
    }


    /**
     * 判断楼层是否可删除
     *
     * @param floorIds
     */
    private void checkFloorRemovable(Long[] floorIds) {
        // 查询删除的楼层ID是否都存在
        if (!lambdaQuery().in(ParkFloor::getFloorId, floorIds).count().equals(Long.valueOf(floorIds.length))) {
            throw new ServiceException("无法删除不存在楼层");
        }

        // 通过楼层ID判断是否存在对应房间
        if (roomMapper.existRoomByFloorIds(floorIds)) {
            throw new ServiceException("房间存在对应楼层信息无法删除");
        }

    }


}

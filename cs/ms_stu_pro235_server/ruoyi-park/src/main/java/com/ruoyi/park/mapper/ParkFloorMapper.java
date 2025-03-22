package com.ruoyi.park.mapper;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.park.domain.entity.ParkFloor;
import java.util.List;

public interface ParkFloorMapper extends BaseMapper<ParkFloor> {

    /**
     * 根据楼宇ID查询楼层列表
     *
     * @param buildingId 楼宇ID
     * @return 符合条件的楼层列表
     */
    default List<ParkFloor> floorSelect(Long buildingId) {
        LambdaQueryWrapper<ParkFloor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(buildingId != null, ParkFloor::getBuildingId, buildingId);
        return selectList(lqw);
    }
}

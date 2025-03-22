package com.ruoyi.park.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.park.domain.entity.ParkFloor;
import com.ruoyi.park.domain.param.floor.AddFloorParam;
import com.ruoyi.park.domain.param.floor.EditFloorParam;
import com.ruoyi.park.domain.vo.floor.FloorSelectVo;

import java.util.List;

/**
 * @author hezining
 * @date 2025/3/8 10:12
 * @description: 楼层服务层接口
 */
public interface IParkFloorService extends IService<ParkFloor> {


    /**
     * 批量删除楼层
     *
     * @param floorIds
     * @return
     */
    public Boolean removeFloor(Long[] floorIds);

    /**
     * 查询楼层列表
     *
     * @param buildingId
     * @return
     */
    List<FloorSelectVo> floorSelect(Long buildingId);
}

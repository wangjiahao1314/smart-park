package com.ruoyi.park.service;

import com.ruoyi.park.domain.param.floor.AddFloorParam;
import com.ruoyi.park.domain.param.floor.EditFloorParam;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hezining
 * @date 2025/3/8 12:17
 * @description: 楼宇和楼层服务层接口
 */
public interface IParkBuildingFloorService {
    /**
     * 新增楼层
     *
     * @param param
     * @return
     */
    public Boolean addFloor(AddFloorParam param);

    /**
     * 批量删除楼宇
     *
     * @param buildingIds
     * @return
     */
    public int removeBuilding(Long[] buildingIds);

    /**
     * 编辑楼层信息
     *
     * @param param
     * @return
     */
    public Boolean editFloor(EditFloorParam param);
}

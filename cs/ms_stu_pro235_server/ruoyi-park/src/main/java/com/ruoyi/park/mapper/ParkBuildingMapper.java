package com.ruoyi.park.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.park.domain.entity.ParkBuilding;
import com.ruoyi.park.domain.param.building.AddBuildingParam;
import com.ruoyi.park.domain.param.building.BuildingListParam;
import com.ruoyi.park.domain.vo.building.BuildingListVo;
import com.ruoyi.park.mapping.ParkBuildingMapping;
import org.apache.ibatis.annotations.Param;

import java.util.Arrays;
import java.util.List;

/**
 * @author hezining
 * @date 2025/3/5 20:33
 * @description: 楼宇持久层
 */
public interface ParkBuildingMapper extends BaseMapper<ParkBuilding> {

    /**
     * 分页查询楼宇园区列表
     * @param param
     * @return
     */
    List<BuildingListVo> selectBuildingList(BuildingListParam param);

    /**
     * 新增楼宇信息
     *
     * @param param
     * @return
     */
    default int addBuilding(AddBuildingParam param) {

        return insert(ParkBuildingMapping.INSTANCE.toBuilding(param));
    }

    /**
     * 批量删除楼宇
     *
     * @param buildingIds
     * @return
     */
    default int removeBuilding(Long[] buildingIds) {
        return deleteBatchIds(Arrays.asList(buildingIds));
    }
}

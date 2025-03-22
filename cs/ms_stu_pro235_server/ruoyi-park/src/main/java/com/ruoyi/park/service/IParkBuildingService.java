package com.ruoyi.park.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.park.domain.entity.ParkBuilding;
import com.ruoyi.park.domain.param.building.AddBuildingParam;
import com.ruoyi.park.domain.param.building.BuildingListParam;
import com.ruoyi.park.domain.param.building.EditBuildingParam;
import com.ruoyi.park.domain.vo.building.BuildingInfoVo;
import com.ruoyi.park.domain.vo.building.BuildingListVo;
import com.ruoyi.park.domain.vo.building.BuildingSelectVo;

import java.util.List;

/**
 * @author hezining
 * @date 2025/3/5 20:31
 * @description: 楼宇服务层接口
 */
public interface IParkBuildingService extends IService<ParkBuilding> {

    /**
     * 新增楼宇信息
     *
     * @param param
     * @return
     */
    int addBuilding(AddBuildingParam param);


    /**
     * 编辑楼宇信息
     *
     * @param param
     * @return
     */
    int editBuilding(EditBuildingParam param);

    /**
     * 根据楼宇ID获取楼宇详情
     *
     * @param buildingId
     * @return
     */
    BuildingInfoVo getBuildingInfo(Long buildingId);

    /**
     * 分页查询楼宇列表
     *
     * @param param
     * @return
     */
    List<BuildingListVo> selectBuildingList(BuildingListParam param);

    /**
     * 下拉框查询楼宇
     * @param zoneId
     * @return
     */
    List<BuildingSelectVo> buildingSelect(Long zoneId);
}

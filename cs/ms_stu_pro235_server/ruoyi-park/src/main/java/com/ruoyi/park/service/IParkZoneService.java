package com.ruoyi.park.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.park.domain.entity.ParkZone;
import com.ruoyi.park.domain.param.zone.AddZoneParam;
import com.ruoyi.park.domain.param.zone.EditZoneParam;
import com.ruoyi.park.domain.param.zone.ZoneListParam;
import com.ruoyi.park.domain.vo.zone.ZoneInfoVo;
import com.ruoyi.park.domain.vo.zone.ZoneListVo;

import java.util.List;

/**
 * @author hezining
 * @date 2025/3/3 20:23
 * @description: 园区服务层接口
 */

public interface IParkZoneService extends IService<ParkZone> {

    /**
     * 新增园区
     *
     * @param param
     * @return
     */
    public Boolean addZone(AddZoneParam param);


    /**
     * 修改园区
     *
     * @param param
     * @return
     */
    public Boolean editZone(EditZoneParam param);


    /**
     * 批量删除园区
     *
     * @param zoneIds
     * @return
     */
    public Boolean removeZone(Long[] zoneIds);

    /**
     * 根据园区Id获取园区详情
     *
     * @param zoneId
     * @return
     */
    public ZoneInfoVo getZoneInfo(Long zoneId);

    /**
     * 分页获取园区列表
     * @param param
     * @return
     */
    public Page<ParkZone> selectZoneList(ZoneListParam param);
}

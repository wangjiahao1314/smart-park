package com.ruoyi.park.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.park.domain.entity.ParkBuilding;
import com.ruoyi.park.domain.entity.ParkZone;
import com.ruoyi.park.domain.param.zone.AddZoneParam;
import com.ruoyi.park.domain.param.zone.EditZoneParam;
import com.ruoyi.park.domain.param.zone.ZoneListParam;
import com.ruoyi.park.domain.vo.zone.ZoneInfoVo;
import com.ruoyi.park.mapper.ParkZoneMapper;
import com.ruoyi.park.mapping.ParkZoneMapping;
import com.ruoyi.park.service.IParkBuildingService;
import com.ruoyi.park.service.IParkZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author hezining
 * @date 2025/3/3 20:23
 * @description: 园区服务层实现类
 */

@Service
@RequiredArgsConstructor
public class ParkZoneServiceImpl extends ServiceImpl<ParkZoneMapper, ParkZone>
        implements IParkZoneService {

    private final ParkZoneMapper zoneMapper;

    private final IParkBuildingService buildingService;

    /**
     * 新增园区
     *
     * @param param
     * @return
     */
    @Override
    public Boolean addZone(AddZoneParam param) {
        // 判断园区名称是否重复
        if (isRepeatName(param.getZoneName(), null)) {
            throw new ServiceException("园区名称已存在");
        }

        // 新增
        return save(ParkZoneMapping.INSTANCE.toZone(param));
    }

    /**
     * 修改园区
     *
     * @param param
     * @return
     */
    @Override
    public Boolean editZone(EditZoneParam param) {
        // 校验园区名称是否重复
        if (isRepeatName(param.getZoneName(), param.getZoneId())) {
            throw new ServiceException("园区名称已存在");
        }

        return updateById(ParkZoneMapping.INSTANCE.toZone(param));
    }

    /**
     * 批量删除园区
     *
     * @param zoneIds
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeZone(Long[] zoneIds) {
        // 检查园区是否可以删除
        checkZoneRemovable(zoneIds);

        // 批量更新zone删除标志
        return lambdaUpdate()
                .set(ParkZone::getDelFlag, UserStatus.DELETED.getCode())
                .in(ParkZone::getZoneId, zoneIds)
                .eq(ParkZone::getDelFlag, UserStatus.OK.getCode())
                .update();
    }

    /**
     * 根据园区Id获取园区详情
     *
     * @param zoneId
     * @return
     */
    @Override
    public ZoneInfoVo getZoneInfo(Long zoneId) {
        // 查询zoneId对应的zone对象
        ParkZone zone = lambdaQuery().eq(ParkZone::getZoneId, zoneId)
                .eq(ParkZone::getDelFlag, UserStatus.OK.getCode())
                .one();

        if (zone == null) {
            throw new ServiceException("园区不存在");
        }

        // 复制到vo
        ZoneInfoVo zoneInfoVo = new ZoneInfoVo();
        BeanUtils.copyBeanProp(zoneInfoVo, zone);
        return zoneInfoVo;
    }

    /**
     * 分页获取园区列表
     *
     * @param param
     * @return
     */
    @Override
    public Page<ParkZone> selectZoneList(ZoneListParam param) {

        // 设置查询条件
        LambdaQueryWrapper<ParkZone> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ParkZone::getDelFlag, UserStatus.OK.getCode())
                .like(param.getZoneName() != null && param.getZoneName() != "", ParkZone::getZoneName, param.getZoneName())
                .orderByDesc(ParkZone::getCreateTime);

        // 分页查询
        Page<ParkZone> zonePage = zoneMapper.selectPage(new Page<>(param.getPageNum(), param.getPageSize()), lqw);
        return zonePage;
    }


    /**
     * 判断园区名称是否重复
     *
     * @param zoneName
     * @param zoneId   园区ID(可选)
     * @return 存在true, 不存在false
     */
    private Boolean isRepeatName(String zoneName, Long zoneId) {
        return lambdaQuery()
                .eq(ParkZone::getDelFlag, UserStatus.OK.getCode()) // 状态为正常
                .eq(ParkZone::getZoneName, zoneName)               // 名称相同
                .ne(zoneId != null, ParkZone::getZoneId, zoneId) // 排除指定 zoneId
                .exists();
    }

    /**
     * 检查园区是否可以删除
     *
     * @param zoneIds
     */
    private void checkZoneRemovable(Long[] zoneIds) {

        // 判断是否有删除的园区存在
        if (zoneIds.length != lambdaQuery().in(ParkZone::getZoneId, zoneIds)
                .eq(ParkZone::getDelFlag, UserStatus.OK.getCode())
                .count()) {
            throw new ServiceException("无法删除不存在园区");
        }

        // 检查园区对应楼宇信息是否存在
        if (buildingService.lambdaQuery()
                .eq(ParkBuilding::getDelFlag, UserStatus.OK.getCode())
                .in(ParkBuilding::getZoneId, zoneIds)
                .exists()) {
            throw new ServiceException("无法删除存在对应楼宇信息的园区");
        }
    }
}

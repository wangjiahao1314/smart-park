package com.ruoyi.park.mapping;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.park.domain.entity.ParkTenant;
import com.ruoyi.park.domain.param.tenant.EditTenantInfoParam;
import com.ruoyi.park.domain.vo.tenant.TenantInfoVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author hezining
 * @date 2025/3/1 0:39
 * @description: 租户信息映射接口，用于定义租户相关数据的转换逻辑
 *               该接口主要用于将不同的数据结构（如实体类、VO、DTO 等）进行相互转换
 */

@Mapper
public interface ParkTenantMapping {
    public ParkTenantMapping INSTANCE = Mappers.getMapper(ParkTenantMapping.class);

    /**
     * 将租户和对应用户转为租户信息Vo
     * @param tenant 租户
     * @param user 租户对应用户
     * @return TenantInfoVo
     */
    TenantInfoVo toInfoVo(ParkTenant tenant, SysUser user);

    /**
     * 将租户修改参数转为租户
     * @param param
     * @return ParkTenant
     */
    ParkTenant toTenant(EditTenantInfoParam param);

    /**
     * 将租户修改参数转为用户
     * @param param
     * @return SysUser
     */
    SysUser toUser(EditTenantInfoParam param);

}

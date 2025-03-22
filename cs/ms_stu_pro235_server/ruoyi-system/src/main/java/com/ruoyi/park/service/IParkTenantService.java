package com.ruoyi.park.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.park.domain.entity.ParkTenant;
import com.ruoyi.park.domain.param.tenant.AddTenantParam;
import com.ruoyi.park.domain.param.tenant.EditTenantInfoParam;
import com.ruoyi.park.domain.param.tenant.TenantListParam;

import com.ruoyi.park.domain.vo.tenant.TenantInfoVo;
import com.ruoyi.park.domain.vo.tenant.TenantListVo;


/**
 * @author hezining
 * @date 2025/2/25 22:43
 * @description: 租户服务层接口
 */
public interface IParkTenantService extends IService<ParkTenant> {

    /**
     * 新增租户
     *
     * @param param
     * @return
     */
    public Boolean addTenant(AddTenantParam param);


    /**
     * 校验idCardNumber是否唯一
     *
     * @param idCardNumber 身份证号码
     * @return
     */

    public Boolean checkIdCardNumberUnique(String idCardNumber);


    /**
     * 根据租户ID获取租户详情
     *
     * @param tenantId
     * @return
     */
    public TenantInfoVo getTenantInfo(Long tenantId);

    /**
     * 编辑租户信息
     *
     * @param param
     * @return
     */
    public int editTenantInfo(EditTenantInfoParam param);

    /**
     * 批量删除租户
     *
     * @param tenantIds 租户ID数组
     * @return 操作结果
     */
    public int removeTenant(Long[] tenantIds);

    /**
     * 分页获取租户列表
     *
     * @param param
     * @return
     */
    public TableDataInfo<TenantListVo> selectTenantList(TenantListParam param);


}

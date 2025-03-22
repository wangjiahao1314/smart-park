package com.ruoyi.park.service;

import com.ruoyi.common.core.domain.AjaxResult;

import com.ruoyi.park.domain.vo.tenant.TenantIndividualCenterVo;
import com.ruoyi.park.domain.vo.worker.IndividualCenterVo;
import com.ruoyi.park.domain.param.worker.UpdatePasswordParam;

/**
 * @author raoce
 * @date 2025/3/8 23:30
 * @description: 个人中心你服务层
 */
public interface IParkIndividualCenterService {
    /**
     * 维修端个人中心
     * @return
     */
    public IndividualCenterVo individualCenter();

    /**
     * 租户端个人中心
     * @return
     */
    public TenantIndividualCenterVo tenantIndividualCenter();

    /**
     * 修改密码
     * @param updatePasswordParam
     * @return
     */
    public AjaxResult updatePassword(UpdatePasswordParam updatePasswordParam);


}

package com.ruoyi.park.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.park.domain.entity.ParkTenant;
import com.ruoyi.park.domain.vo.tenant.TenantListVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hezining
 * @date 2025/2/25 22:45
 * @description: 租户持久层接口
 */
@Component
public interface ParkTenantMapper extends BaseMapper<ParkTenant> {

    List<TenantListVo> selectTenantList(String nickName);
}

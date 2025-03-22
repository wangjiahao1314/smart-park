package com.ruoyi.park.mapping;

import com.ruoyi.park.domain.entity.ParkRepair;
import com.ruoyi.park.domain.vo.repair.ManageRepairDtlVo;
import com.ruoyi.park.domain.vo.repair.ManageRepairVo;
import com.ruoyi.park.domain.vo.repair.RepairListVo;
import com.ruoyi.park.domain.vo.repair.TenantRepairListVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author raoce
 * @date 2025/3/2 19:46
 * @description:
 */
@Mapper
public interface ParkRepairMapping {
    ParkRepairMapping INSTANCE = Mappers.getMapper(ParkRepairMapping.class);
    //转维修工分页查询报修单
    List<RepairListVo> toRepairList(List<ParkRepair> parkRepair);
    //转租户分页查询报修单
    List<TenantRepairListVo> toList(List <ParkRepair>list);
    //转管理员分页查询报修单
    List<ManageRepairVo> toManageRepair(List <ParkRepair>list);
    //转管理员查询工单详情实体
    ManageRepairDtlVo toManageRepairDtl(ParkRepair parkRepair);
    //新建报修单注入已被取消的保修单信息
    ParkRepair toRepair(ParkRepair parkRepair);
}

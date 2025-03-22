package com.ruoyi.park.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.park.domain.entity.ParkBill;
import com.ruoyi.park.domain.vo.bill.ParkBillListVo;
import com.ruoyi.park.domain.vo.bill.ParkBillSearchVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hezining
 * @date 2025/3/2 20:16
 * @description: 账单持久层接口
 */

public interface ParkBillMapper extends BaseMapper<ParkBill> {



    List<ParkBillListVo> selectBillsWithContractDetails(@Param("search") ParkBillSearchVo search);

    /**
     * 批量逻辑删除方法
     *
     * @param billIds 需要逻辑删除的账单ID列表
     * @return int 返回受影响的行数
     */
    int batchLogicDelete(@Param("billIds") List<Long> billIds);



    int batchCollectBills(@Param("billIds") List<Long> billIds);

}



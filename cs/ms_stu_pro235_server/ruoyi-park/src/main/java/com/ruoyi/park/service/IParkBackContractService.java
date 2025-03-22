package com.ruoyi.park.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.park.domain.entity.ParkBackContract;
import com.ruoyi.park.domain.vo.backcontract.ParkBackContractVo;

public interface IParkBackContractService extends IService<ParkBackContract> {

    int edit (ParkBackContractVo vo);
}

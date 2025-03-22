package com.ruoyi.park.mapping;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.park.domain.entity.ParkContract;
import com.ruoyi.park.domain.vo.contract.ParkContractAddVo;
import com.ruoyi.park.domain.vo.contract.ParkContractDtlVo;
import com.ruoyi.park.domain.vo.contract.ParkContractListVo;
import com.ruoyi.park.domain.vo.contract.ParkContractUpdateVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ContractMapping {
    ContractMapping  INSTANCE = Mappers.getMapper(ContractMapping.class);
ParkContract toParkContract(ParkContractAddVo contractVo);



ParkContract toParkContract(ParkContractUpdateVo contractVo);

Page<ParkContractListVo> toPage(Page<ParkContract> vo);

    ParkContractDtlVo toParkContractDtlVo(ParkContract contract);

}

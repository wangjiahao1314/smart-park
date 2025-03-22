package com.ruoyi.park.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.park.domain.entity.ParkContract;
import com.ruoyi.park.domain.vo.contract.*;

import java.util.List;


public interface IParkContractService extends IService<ParkContract> {


    int add(ParkContractAddVo vo);

    int update(ParkContractUpdateVo vo);

    int delete(Long id);


Page<ParkContractListVo> toPage(PageQuery page, ParkContractGetVo vo);

ParkContractDtlVo getDtlById(Long id);
}

package com.ruoyi.park.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.park.domain.entity.ParkBackContract;
import com.ruoyi.park.domain.entity.ParkContract;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ParkBackContractMapper extends BaseMapper<ParkBackContract> {

}

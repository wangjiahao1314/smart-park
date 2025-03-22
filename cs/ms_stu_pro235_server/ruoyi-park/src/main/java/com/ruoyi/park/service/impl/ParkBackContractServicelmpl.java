package com.ruoyi.park.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.park.domain.entity.ParkBackContract;
import com.ruoyi.park.domain.entity.ParkContract;
import com.ruoyi.park.domain.vo.backcontract.ParkBackContractVo;
import com.ruoyi.park.mapper.ParkBackContractMapper;
import com.ruoyi.park.mapper.ParkContractMapper;
import com.ruoyi.park.service.IParkBackContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service
public class ParkBackContractServicelmpl extends ServiceImpl<ParkBackContractMapper, ParkBackContract> implements IParkBackContractService {


    @Autowired
    private ParkBackContractMapper parkBackContractMapper;

    @Autowired
    private ParkContractMapper parkContractMapper;

    public static String RentReturnNumber() {

        // 创建SimpleDateFormat对象，用于格式化当前时间
        // 获取当前时间，并按照指定格式转换为字符串
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());

        // 生成一个四位数的随机数
        Random random = new Random();
        int randomNum = random.nextInt(9000) + 1000; // 1000 到 9999 之间的随机数

        // 组合生成退租编号
        String rentReturnNumber = "RR" + timestamp + randomNum;

        return rentReturnNumber;
    }


    @Override
    public int edit(ParkBackContractVo vo) {

        // 创建一个 ParkBackContract 对象
        ParkBackContract parkBackContract = new ParkBackContract();
        // 将 vo 对象的属性复制到 parkBackContract 对象中
        BeanUtils.copyBeanProp(vo, parkBackContract);
    // 设置 parkBackContract 的终止编号为 RentReturnNumber() 方法返回的值
        parkBackContract.setTerminationNumber(RentReturnNumber());
        // 根据 vo 对象的合同编号查询对应的 ParkContract 对象
        ParkContract parkContract = parkContractMapper.selectOne(Wrappers.<ParkContract>lambdaQuery().eq(ParkContract::getContractNo, vo.getContractNo()));
        // 设置 parkContract 的合同类型为 "3"
        parkContract.setContractType("3");
        // 更新 parkContract 对象到数据库中
        parkContractMapper.updateById(parkContract);

        // 将 parkBackContract 对象插入到数据库中，并获取插入的行数
        int insert = parkBackContractMapper.insert(parkBackContract);
        // 返回插入的行数
        return insert;

    }
}

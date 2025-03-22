package com.ruoyi.park.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.park.domain.entity.*;
import com.ruoyi.park.domain.vo.contract.*;
import com.ruoyi.park.mapper.*;
import com.ruoyi.park.mapping.ContractMapping;
import com.ruoyi.park.service.IParkBuildingService;
import com.ruoyi.park.service.IParkContractService;
import com.ruoyi.park.service.IParkZoneService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class ParkContractServiceImpl extends ServiceImpl<ParkContractMapper, ParkContract> implements IParkContractService {

    @Autowired
    private ParkContractMapper parkContractMapper;

    @Autowired
    private ParkTenantMapper parkTenantMapper;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ParkRoomServiceImpl parkRoomService;

    @Autowired
    private IParkBuildingService parkBuildingService;
    @Autowired
    private IParkZoneService parkZoneService;
    @Autowired
    private ParkFloorMapper parkFloorMapper;

    @Autowired
    private ParkRoomMapper parkRoomMapper;

    @Autowired
    private ParkTenantRoomMapper tenantRoomMapper;

    @Override
    @Transactional
    public int add(ParkContractAddVo vo) {


        // 根据合同编号查询合同
        ParkContract one = parkContractMapper.selectOne(Wrappers.<ParkContract>lambdaQuery().eq(ParkContract::getContractNo, vo.getContractNo()));
        // 如果合同已存在，抛出异常
        if (one != null) {
            throw new RuntimeException("合同编号已存在");

        }

        //vo转实体类
        ParkContract parkContract = ContractMapping.INSTANCE.toParkContract(vo);
        parkContract.setVersion(1);

        // 设置租户ID和创建时间
        parkContract.setUpdateBy(SecurityUtils.getUsername());
        parkContract.setCreateTime(DateUtils.getNowDate());


        // 插入合同

        int insert = parkContractMapper.insert(parkContract);
        if (insert == 0) {
            throw new RuntimeException("合同插入失败");
        }
        ParkRoom parkRoom = new ParkRoom();
        parkRoom.setRoomId(vo.getRoomId());
        parkRoom.setShelfStatus("1");
        // 更新房间状态为已租出
        boolean b = parkRoomService.updateById(parkRoom);
        if (!b) {
            throw new RuntimeException("房间状态更新失败");
        }


        // 插入对应租赁表信息

        return tenantRoomMapper.insertContractInfo(parkContract.getContractId(), parkContract.getTenantId(), parkContract.getRoomId());
    }

    @Override
    public int update(ParkContractUpdateVo vo) {

        // 根据合同编号查询合同
        ParkContract one = parkContractMapper.selectOne(Wrappers.<ParkContract>lambdaQuery().eq(ParkContract::getContractNo, vo.getContractNo()).ne(ParkContract::getContractId, vo.getContractId()));
        // 如果合同已存在，抛出异常
        if (one != null) {
            throw new RuntimeException("合同编号已存在");

        }
        ParkContract parkContract = ContractMapping.INSTANCE.toParkContract(vo);
        int version = parkContract.getVersion();
        parkContract.setVersion(version + 1);
        parkContract.setUpdateTime(DateUtils.getNowDate());
        parkContract.setUpdateBy(SecurityUtils.getUsername());
        return parkContractMapper.updateById(parkContract);


    }


    @Override
    public int delete(Long id) {

        // 根据id查询合同
        ParkContract parkContract = parkContractMapper.selectById(id);
        // 如果合同不存在，抛出异常
        if (parkContract == null) {
            throw new RuntimeException("合同不存在");
        }
        parkContract.setDelFlag("2");
        // 根据id删除合同

        return parkContractMapper.updateById(parkContract);
    }

    /**
     * 合同列表
     *
     * @param page
     * @param vo
     * @return
     */
    @Override
    public Page<ParkContractListVo> toPage(PageQuery page, ParkContractGetVo vo) {
        // 创建一个LambdaQueryWrapper对象，用于构建查询条件
        LambdaQueryWrapper<ParkContract> wrapper = Wrappers.lambdaQuery();
        // 如果合同名称不为空，则添加合同名称查询条件
        if (StringUtils.isNotNull(vo.getContractName())) {
            wrapper.like(ParkContract::getContractName, vo.getContractName());

        }
        // 如果合同编号不为空，则添加合同编号查询条件
        if (StringUtils.isNotNull(vo.getContractNo())) {
            wrapper.like(ParkContract::getContractNo, vo.getContractNo());
        }
        // 如果客户名称不为空，则添加客户名称查询条件
        if (StringUtils.isNotNull(vo.getCustomerName())) {
            wrapper.like(ParkContract::getCustomerName, vo.getCustomerName());
        }
        // 如果合同类型不为空，则添加合同类型查询条件
        if (StringUtils.isNotNull(vo.getContractType())) {
            wrapper.like(ParkContract::getContractType, vo.getContractType());
        }
        // 添加排序条件
        if ("desc".equals(vo.getSort())) {

            wrapper.orderByDesc(ParkContract::getCreateTime);
        } else {
            wrapper.orderByAsc(ParkContract::getCreateTime);
        }
        wrapper.eq(ParkContract::getDelFlag, "0");

        // 根据查询条件分页查询合同信息
        List<ParkContract> list = parkContractMapper.selectList(wrapper);

        List<ParkContractListVo> list1 = list.stream().map(s -> {


            ParkContractListVo view = new ParkContractListVo();

            BeanUtils.copyProperties(s, view);
            Long tenantId = s.getTenantId();
            ParkTenant parkTenant = parkTenantMapper.selectById(tenantId);
            Long userId = parkTenant.getUserId();
            SysUser sysUser = userService.selectUserById(userId);
            view.setPhonenumber(sysUser.getPhonenumber());
            view.setCustomerName(sysUser.getUserName());

            return view;

        }).collect(Collectors.toList());

        Page<ParkContractListVo> page2 = new Page<>(page.getPageNum(), page.getPageSize());


        page2.setRecords(list1);


        // 将查询结果转换为ParkContractListVo对象并返回

        return page2;
    }

    @Override
    public ParkContractDtlVo getDtlById(Long id) {
        // 根据id查询合同信息
        ParkContract parkContract = parkContractMapper.selectById(id);
        // 将合同信息转换为Vo对象
        ParkContractDtlVo parkContractDtlVo = ContractMapping.INSTANCE.toParkContractDtlVo(parkContract);
        // 获取租户id
        Long tenantId = parkContractDtlVo.getTenantId();
        // 根据租户id查询租户信息
        ParkTenant parkTenant = parkTenantMapper.selectById(tenantId);
        // 创建其他信息对象
        CustomerContractOthers Others = new CustomerContractOthers();
        // 将租户信息复制到其他信息对象中
        BeanUtils.copyProperties(parkTenant, Others);
        // 根据租户id查询用户信息
        SysUser sysUser = userService.selectUserById(parkTenant.getUserId());
        // 将用户信息复制到其他信息对象中
        BeanUtils.copyProperties(sysUser, Others);
        // 将其他信息对象设置到Vo对象中
        parkContractDtlVo.setOthers(Others);

        CustomerContractRooms room = new CustomerContractRooms();
        ParkRoom parkRoom = parkRoomMapper.selectById(parkContract.getRoomId());

        BeanUtils.copyProperties(parkRoom, room);
        ParkFloor parkFloor = parkFloorMapper.selectById(parkRoom.getFloorId());
        ParkBuilding byId = parkBuildingService.getById(parkFloor.getBuildingId());
        ParkZone byId1 = parkZoneService.getById(byId.getZoneId());

        room.setBuildingName(byId1.getZoneName());
        room.setFloorName(parkFloor.getFloorName());
        room.setRoomName(parkRoom.getRoomName());


        parkContractDtlVo.setRooms(room);


        // 返回Vo对象
        return parkContractDtlVo;
    }


}

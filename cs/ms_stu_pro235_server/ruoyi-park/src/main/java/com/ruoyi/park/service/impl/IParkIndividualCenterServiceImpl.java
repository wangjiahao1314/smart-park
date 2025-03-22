package com.ruoyi.park.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;

import com.ruoyi.park.domain.entity.ParkTenant;
import com.ruoyi.park.domain.entity.ParkWorker;

import com.ruoyi.park.domain.vo.tenant.TenantIndividualCenterVo;
import com.ruoyi.park.domain.vo.worker.IndividualCenterVo;
import com.ruoyi.park.domain.param.worker.UpdatePasswordParam;
import com.ruoyi.park.mapper.ParkTenantMapper;
import com.ruoyi.park.mapper.ParkWorkerMapper;
import com.ruoyi.park.service.IParkIndividualCenterService;
import com.ruoyi.system.mapper.SysUserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author raoce
 * @date 2025/3/8 23:32
 * @description: 个人中心服务层
 */
@Service
public class IParkIndividualCenterServiceImpl implements IParkIndividualCenterService {

    @Autowired
    private ParkWorkerMapper parkWorkerMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ParkTenantMapper parkTenantMapper;
    /**
     * 维修工查看个人中心
     * @return
     */
    @Override
    public IndividualCenterVo individualCenter() {
        IndividualCenterVo individualCenterVo = new IndividualCenterVo();
        //1.创建个人信息
        QueryWrapper workerWrapper = Wrappers.query();
        workerWrapper.eq("user_id", SecurityUtils.getUserId());
        ParkWorker parkWorker = parkWorkerMapper.selectOne(workerWrapper);
        SysUser user = sysUserMapper.selectUserById(SecurityUtils.getUserId());
        //2.注入信息
        individualCenterVo.setNickName(user.getNickName());
        individualCenterVo.setUserName(user.getUserName());
        individualCenterVo.setIdCardNumber(parkWorker.getIdCardNumber());
        individualCenterVo.setWorkerNumber(parkWorker.getWorkerNumber());
        individualCenterVo.setEmail(user.getEmail());
        individualCenterVo.setUserType(user.getUserType());
        individualCenterVo.setCreateTime(user.getCreateTime());
        individualCenterVo.setHomeArea(parkWorker.getHomeArea());
        individualCenterVo.setAddressDetail(parkWorker.getAddressDetails());


        return individualCenterVo;
    }

    /**
     * 租户查看个人中心
     * @return
     */
    public TenantIndividualCenterVo tenantIndividualCenter(){
        TenantIndividualCenterVo individualCenterVo = new TenantIndividualCenterVo();
        //1.创建个人信息
        QueryWrapper wrapper = Wrappers.query();
        QueryWrapper tenant = Wrappers.query();
        tenant.eq("user_id",SecurityUtils.getUserId());
        ParkTenant parkTenant = parkTenantMapper.selectOne(tenant);
        SysUser user =sysUserMapper.selectUserById(SecurityUtils.getUserId());
        individualCenterVo.setNickName(user.getNickName());
        individualCenterVo.setUserName(user.getUserName());
        individualCenterVo.setIdCardNumber(parkTenant.getIdCardNumber());
        individualCenterVo.setTenantNumber(user.getPhonenumber());
        individualCenterVo.setEmail(user.getEmail());
        individualCenterVo.setUserType(user.getUserType());
        individualCenterVo.setCreateTime(user.getCreateTime());
        individualCenterVo.setHomeArea(parkTenant.getHomeArea());
        individualCenterVo.setAddressDetail(parkTenant.getAddressDetails());

        return  individualCenterVo;
    }



    /**
     * 修改密码
     * @param updatePasswordParam 修改密码参数
     * @return
     */
    @Override
    public AjaxResult updatePassword(UpdatePasswordParam updatePasswordParam) {
        //获取用户信息
        SysUser sysUser =sysUserMapper.selectUserById(SecurityUtils.getUserId());
        //1.查看输入的旧密码是否为原来的密码
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(!bCryptPasswordEncoder.matches(updatePasswordParam.getPastPassword(),sysUser.getPassword()))
            return AjaxResult.error("旧密码输入不一致");
        //2.验证确定密码和新密码是否一致
        if(!updatePasswordParam.getConfirmedPassword().equals(updatePasswordParam.getNewPassword()))
            return AjaxResult.error("新密码和确定密码输入不一致");
        //3.进行更改
        String pwd = bCryptPasswordEncoder.encode(updatePasswordParam.getConfirmedPassword());
        sysUser.setPassword(pwd);
        return AjaxResult.success(sysUserMapper.updateUser(sysUser));
    }



}

package com.ruoyi.park.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.constant.BillStatus;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.constant.TenantStatus;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.park.domain.entity.ParkBill;
import com.ruoyi.park.domain.entity.ParkTenant;
import com.ruoyi.park.domain.param.tenant.AddTenantParam;
import com.ruoyi.park.domain.param.tenant.EditTenantInfoParam;
import com.ruoyi.park.domain.param.tenant.TenantListParam;
import com.ruoyi.park.domain.vo.tenant.TenantInfoVo;
import com.ruoyi.park.domain.vo.tenant.TenantListVo;
import com.ruoyi.park.domain.vo.tenant.TenantRentingRoomVo;


import com.ruoyi.park.mapper.ParkTenantMapper;
import com.ruoyi.park.mapper.ParkTenantRoomMapper;
import com.ruoyi.park.mapping.ParkTenantMapping;
import com.ruoyi.park.service.IParkBillService;
import com.ruoyi.park.service.IParkTenantService;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.mapper.SysRoleMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.ruoyi.common.utils.PageUtils.startPage;


/**
 * @author hezining
 * @date 2025/2/25 22:46
 * @description: 租户服务层实现类
 */

@RequiredArgsConstructor
@Service
public class ParkTenantServiceImpl extends ServiceImpl<ParkTenantMapper, ParkTenant>
        implements IParkTenantService {

    private final ParkTenantMapper tenantMapper;

    private final ParkTenantRoomMapper tenantRoomMapper;

    private final IParkBillService billService;

    private final SysUserMapper userMapper;

    private final SysRoleMapper roleMapper;

    private final SysUserRoleMapper userRoleMapper;


    /**
     * 新增租户
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addTenant(AddTenantParam param) {
        // 插入用户和角色相关信息
        SysUser user = insertUserAndRoleInfo(param);

        // 插入 park_tenant 表
        ParkTenant tenant = new ParkTenant(param.getIdCardNumber(), param.getHomeArea(), param.getAddressDetails(), user.getUserId());
        verifyUpdateOrInsert(tenantMapper.insert(tenant));

        return true;

    }

    /**
     * 校验idCardNumber是否唯一
     *
     * @param idCardNumber 身份证号码
     * @return
     */
    @Override
    public Boolean checkIdCardNumberUnique(String idCardNumber) {
        if (StringUtils.isNotEmpty(idCardNumber)) {
            return lambdaQuery()
                    .eq(idCardNumber != null, ParkTenant::getIdCardNumber, idCardNumber)
                    .eq(ParkTenant::getDelFlag, UserStatus.OK.getCode())
                    .exists();
        }
        return false;
    }

    /**
     * 根据租户ID获取租户详情
     *
     * @param tenantId
     * @return
     */
    @Override
    public TenantInfoVo getTenantInfo(Long tenantId) {

        // 获取租户信息
        ParkTenant tenant = getTenantById(tenantId);

        // 获取租户对应用户信息

        SysUser user = userMapper.selectUserById(tenant.getUserId());

        // 手机号和身份证号码脱敏处理
        tenant.setIdCardNumber(maskIdCard(tenant.getIdCardNumber()));
        user.setPhonenumber(maskPhone(user.getPhonenumber()));

        return ParkTenantMapping.INSTANCE.toInfoVo(tenant, user);
    }

    /**
     * 编辑租户信息
     *
     * @param param 编辑租户参数
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int editTenantInfo(EditTenantInfoParam param) {

        // 检查除租户本身外是否还存在email
        checkEmail(param.getEmail(), param.getTenantId());

        // 获取当前编辑租户信息
        ParkTenant tenant = getTenantById(param.getTenantId());

        // 更改用户表信息
        SysUser user = ParkTenantMapping.INSTANCE.toUser(param);
        user.setUserId(tenant.getUserId());
        userMapper.updateUser(user);

        // 更改租户表信息
        tenant = ParkTenantMapping.INSTANCE.toTenant(param);

        return tenantMapper.updateById(tenant);

    }

    /**
     * 批量删除租户
     *
     * @param tenantIds 租户ID数组
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeTenant(Long[] tenantIds) {

        // 判断租户是否可以删除
        areTenantsRemovable(tenantIds);

        // 获取租户列表
        List<ParkTenant> list = listByIds(Arrays.asList(tenantIds));


        if (list.isEmpty()) {
            throw new ServiceException("无可删除用户");
        }
        // 批量删除租户信息
        updateBatchById(list.stream().peek(tenant -> tenant.setDelFlag(UserStatus.DELETED.getCode())).collect(Collectors.toList()));

        // 从租户列表获取用户ID列表
        Long[] userIds = list.stream().map(ParkTenant::getUserId).toArray(Long[]::new);

        // 删除用户与角色关联
        userRoleMapper.deleteUserRole(userIds);

        // 删除租户对应的用户信息
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 分页获取租户列表
     *
     * @param param
     * @return List<TenantListVo> 租户Vo集合
     */
    @Override
    public TableDataInfo<TenantListVo> selectTenantList(TenantListParam param) {
        startPage();
        // 查询租户列表
        List<TenantListVo> list = tenantMapper.selectTenantList(param.getNickName());
        PageInfo<TenantListVo> pageInfo = new PageInfo<>(list);

        // 手机号和身份证号码加密处理
        list = list.stream().peek(tenant -> {
            tenant.setPhonenumber(maskPhone(tenant.getPhonenumber()));
            tenant.setIdCardNumber(maskIdCard(tenant.getIdCardNumber()));
        }).collect(Collectors.toList());

        // 查询各租户在租房间,并设置租户的状态
        List<TenantRentingRoomVo> roomList;
        for (TenantListVo vo : list) {
            roomList = tenantRoomMapper.selectRentingRoom(vo.getTenantId());
            vo.setRentingRooms(roomList);
            if (!roomList.isEmpty()) {
                vo.setTenantStatus(TenantStatus.RENTING);
            } else {
                vo.setTenantStatus(TenantStatus.NOT_RENTED);
            }
        }
        return new TableDataInfo(list, (int) pageInfo.getTotal(), HttpStatus.SUCCESS, "查询成功");
    }


    /**
     * 校验插入、更新、删除的正确性
     *
     * @param status Boolean 或者 Integer的更新或插入返回值
     * @param <T>
     */
    private <T> void verifyUpdateOrInsert(T status) {
        if (status == null) {
            throw new IllegalArgumentException("操作结果为空");
        }

        Class<?> clazz = status.getClass();

        if (clazz == Boolean.class || clazz == boolean.class) {
            if (!(Boolean) status) {
                throw new IllegalArgumentException("操作失败");
            }
        } else if (clazz == Integer.class || clazz == int.class) {
            if ((Integer) status <= 0) {
                throw new IllegalArgumentException("操作影响的行数为 0");
            }
        } else {
            throw new IllegalArgumentException("不支持的操作结果类型");
        }
    }

    /**
     * 插入用户和角色相关信息
     *
     * @param param 新增租户参数
     * @return SysUser
     */
    private SysUser insertUserAndRoleInfo(AddTenantParam param) {
        SysUser user = new SysUser(param.getUserName(), param.getNickName(), param.getEmail(), param.getPhonenumber(), SecurityUtils.encryptPassword(param.getPassword()));
        // 插入 sys_user 表
        userMapper.insertUser(user);

        // 插入租户对应角色
        Long l = roleMapper.selectRoleIdByRoleKey(Constants.TENANT);
        if (l == null) {
            throw new ServiceException("查询租户角色失败，请联系管理员");
        }
        userRoleMapper.batchUserRole(Arrays.asList(new SysUserRole(user.getUserId(), l)));

        return user;
    }

    /**
     * 身份证号码脱敏
     *
     * @param idCard 身份证号码
     * @return
     */
    private String maskIdCard(String idCard) {
        if (idCard == null || idCard.length() < 8) {
            return idCard;
        }
        int length = idCard.length();
        return idCard.substring(0, 6) + "******" + idCard.substring(length - 4);
    }

    /**
     * 电话号码脱敏
     *
     * @param phonenumber 电话号码
     * @return
     */
    private String maskPhone(String phonenumber) {
        if (phonenumber == null || phonenumber.length() < 7) {
            return phonenumber;
        }
        return phonenumber.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 根据租户ID获取租户信息
     *
     * @param tenantId
     * @return
     */
    private ParkTenant getTenantById(Long tenantId) {
        // 获取租户信息
        ParkTenant tenant = lambdaQuery().eq(ParkTenant::getTenantId, tenantId)
                .eq(ParkTenant::getDelFlag, UserStatus.OK.getCode())
                .one();
        if (tenant == null || tenant.getUserId() == null) {
            throw new ServiceException("获取租户信息有误");
        }

        return tenant;
    }

    /**
     * 判断租户是否可以删除
     *
     * @param tenantIds
     */
    private void areTenantsRemovable(Long[] tenantIds) {

        // 存在租房信息的用户无法删除
        if (!tenantRoomMapper.selectRoomsByTenantIds(Arrays.asList(tenantIds)).isEmpty()) {
            throw new ServiceException("用户存在租房信息，无法删除");
        }

        // 未收款或者未付款无法删除
        LambdaQueryWrapper<ParkBill> lqw = new LambdaQueryWrapper<>();
        lqw.and(wrapper -> wrapper
                        .eq(ParkBill::getBillStatus, BillStatus.PAYMENT)
                        .or()
                        .eq(ParkBill::getBillStatus, BillStatus.NON_PAYMENT))
                .in(ParkBill::getTenantId, tenantIds)
                .eq(ParkBill::getDelFlag, UserStatus.OK.getCode());
        if (billService.count(lqw) > 0) {
            throw new ServiceException("租户账单未处理，无法删除");
        }

    }

    /**
     * 检查除租户本身外是否还存在email
     *
     * @param email
     */
    private void checkEmail(String email, Long tenantId) {
        //没有email直接跳过
        if (StringUtils.isNotEmpty(email)) {
            // 判断邮箱是否存在于其他租户
            ParkTenant tenant = lambdaQuery()
                    .eq(ParkTenant::getDelFlag, UserStatus.OK.getCode())
                    .eq(ParkTenant::getTenantId, tenantId)
                    .one();
            if (tenant == null) {
                throw new ServiceException("编辑用户不存在");
            }
            if (!userMapper.checkEmailUnique(email).getUserId().equals(tenant.getUserId())) {
                throw new ServiceException("邮箱账号已存在，请重新修改");
            }
        }
    }
}

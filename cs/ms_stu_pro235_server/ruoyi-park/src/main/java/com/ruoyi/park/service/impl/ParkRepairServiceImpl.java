package com.ruoyi.park.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.park.EncryptPhoneNumber;
import com.ruoyi.park.domain.entity.*;
import com.ruoyi.park.domain.param.repair.*;
import com.ruoyi.park.domain.vo.repair.*;
import com.ruoyi.park.mapper.*;
import com.ruoyi.park.mapping.ParkRepairMapping;
import com.ruoyi.park.service.IParkRepairService;
import com.ruoyi.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author raoce
 * @date 2025/3/2 19:09
 * @description:
 */
@Service
public class ParkRepairServiceImpl extends ServiceImpl<ParkRepairMapper, ParkRepair> implements IParkRepairService {
    @Autowired
    private ParkRepairMapper parkRepairMapper;
    @Autowired
    private ParkZoneMapper parkZoneMapper;
    @Autowired
    private ParkTenantMapper parkTenantMapper;
    @Autowired
    private ParkRoomMapper parkRoomMapper;
    @Autowired
    private ParkBuildingMapper parkBuildingMapper;
    @Autowired
    private ParkFloorMapper parkFloorMapper;
    @Autowired
    private ParkWorkerMapper parkWorkerMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ParkTenantRoomMapper parkTenantRoomMapper;
    /**
     * 维修端-工单查询
     * @param workerRepairParam 分页信息
     * @return
     */
    @Override
    public Page<ParkRepair> selectAllList( WorkerRepairParam workerRepairParam) {
        QueryWrapper wrapper = Wrappers.query();
        wrapper.eq("status",workerRepairParam.getStatus());
        if(StringUtils.isNotEmpty(workerRepairParam.getRepairNo()))
            wrapper.like("repair_no",workerRepairParam.getRepairNo());
        QueryWrapper workerWrapper = Wrappers.query();
        workerWrapper.eq("user_id", SecurityUtils.getUserId());
        wrapper.eq("worker_id", parkWorkerMapper.selectOne(workerWrapper).getWorkerId());
        Page page = new Page(workerRepairParam.getPageNum(),workerRepairParam.getPageSize());
        return parkRepairMapper.selectPage(page,wrapper);

    }

    /**
     * 维修端-查询工单详情
     * @param repairId 工单id
     * @return
     */

    @Override
    public AjaxResult workSheetDetail(Long repairId) {
         WorkSheetDetailVo workSheetDetailVo = new WorkSheetDetailVo();
         //1.查询维修状态
         ParkRepair parkRepair = parkRepairMapper.selectById(repairId);
         if(parkRepair==null)
             return AjaxResult.error("查询失败！工单Id不存在");
         workSheetDetailVo.setStatus(parkRepair.getStatus());
         //2.查询报修信息
         RepairInfo repairInfo = new RepairInfo();
         ParkTenant parkTenant = parkTenantMapper.selectById(parkRepair.getTenantId());
        if(parkTenant==null)
            return AjaxResult.error("查询失败！租户Id不存在");
         SysUser sysUser = sysUserMapper.selectUserById(parkTenant.getUserId());
        if(parkTenant==null)
            return AjaxResult.error("查询失败！用户Id不存在");
         repairInfo.setTenantName(sysUser.getNickName());
         repairInfo.setContactName(parkRepair.getContactName());
         repairInfo.setContactNumber(parkRepair.getContactNumber());
         repairInfo.setRepairStartTime(parkRepair.getRepairStartTime());
         repairInfo.setRepairImg(parkRepair.getRepairImg());
         repairInfo.setRepairNo(parkRepair.getRepairNo());
         repairInfo.setAllocationTime(parkRepair.getAllocationTime());
         repairInfo.setRepairArea(parkRepair.getRepairArea());
         repairInfo.setDescription(parkRepair.getDescription());
         ParkRoom parkRoom = parkRoomMapper.selectById(parkRepair.getRoomId());
        if(parkRoom==null)
            return AjaxResult.error("查询失败！房间Id不存在");
         repairInfo.setZoneName(parkZoneMapper.selectById(parkRoom.getZoneId()).getZoneName());
         repairInfo.setBuildingName(parkBuildingMapper.selectById(parkRoom.getBuildingId()).getBuildingName());
         repairInfo.setFloorName(parkFloorMapper.selectById(parkRoom.getFloorId()).getFloorName());
         repairInfo.setRoomName(parkRoom.getRoomName());
         workSheetDetailVo.setRepairInfo(repairInfo);
         //3.查询维修信息
         MaintainInfo maintainInfo = new MaintainInfo();
         maintainInfo.setRepairStartTime(parkRepair.getRepairStartTime());
         maintainInfo.setRepairEndTime(parkRepair.getRepairEndTime());
         SysUser worker = sysUserMapper.selectUserById(parkWorkerMapper.selectById(parkRepair.getWorkerId()).getUserId());
        if(worker==null)
            return AjaxResult.error("查询失败！用户Id不存在");
         maintainInfo.setWorkerName(worker.getNickName());
         maintainInfo.setWorkerNumber(worker.getPhonenumber());
         workSheetDetailVo.setMaintainInfo(maintainInfo);
        return AjaxResult.success(workSheetDetailVo);
    }

    /**
     * 租户端-分页查询我的保修
     * @param param
     * @return
     */
    @Override
    public List selectList(TenantRepairListParam param) {
        QueryWrapper tenantWrapper = Wrappers.query();
        tenantWrapper.eq("user_id",SecurityUtils.getUserId());
        ParkTenant parkTenant = parkTenantMapper.selectOne(tenantWrapper);
        if(parkTenant==null)
            return null;
        QueryWrapper wrapper = Wrappers.query();
        wrapper.eq("status",param.getStatus());
        if(StringUtils.isNotEmpty(param.getRepairNo()))
            wrapper.like("repair_no",param.getRepairNo());
        //绑定用户账号
        wrapper.eq("tenant_id",parkTenant.getTenantId());
        Page page = new Page(param.getPageNum(),param.getPageSize());

        Long size = parkRepairMapper.selectPage(page,wrapper).getSize();
        List list = parkRepairMapper.selectPage(page, wrapper).getRecords();
        //对联系人处理
        if(list.size()!=0){
        for(int i= 0;i<size;i++){
            ParkRepair parkRepair = (ParkRepair) list.get(i);
            ParkWorker parkWorker = parkWorkerMapper.selectById(parkRepair.getWorkerId());
            if(parkWorker==null)
                return null;
            SysUser sysUser = sysUserMapper.selectUserById(parkTenant.getUserId());
            if(sysUser==null)
                return null;
            String name = sysUser.getNickName()+"（"+sysUser.getPhonenumber()+"）";
            ((ParkRepair) list.get(i)).setContactName(name);
        }
        }
        return list;
    }


    /**
     * 租户端-修改报修单
     * @param tenantRepairUpdateParam 修改参数
     * @return
     */
    @Override
    @Transactional
    public AjaxResult updateRepair(TenantRepairUpdateParam tenantRepairUpdateParam) throws ParseException {
        //获取对应报修单
        ParkRepair parkRepair = parkRepairMapper.selectById(tenantRepairUpdateParam.getRepairId());
        if(parkRepair==null)
            return AjaxResult.error("修改失败！报修单不存在");
        //进行修改
        LambdaUpdateWrapper<ParkRepair> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ParkRepair::getRepairId,tenantRepairUpdateParam.getRepairId());
        parkRepair.setContactName(tenantRepairUpdateParam.getContactName());
        parkRepair.setContactNumber(tenantRepairUpdateParam.getContactNumber());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(  "yyyy-MM-dd HH:mm:ss");
        parkRepair.setRepairStartTime(simpleDateFormat.parse(tenantRepairUpdateParam.getRepairStartTime()));
        parkRepair.setRepairArea(tenantRepairUpdateParam.getRepairArea());
        parkRepair.setDescription(tenantRepairUpdateParam.getDescription());
        parkRepair.setRepairImg(tenantRepairUpdateParam.getRepair_img());
        return AjaxResult.success(parkRepairMapper.update(parkRepair,wrapper));
    }

    /**
     * 租户端-取消报修
     * @param repairId 报修id
     * @return
     */
    @Override
    @Transactional
    public AjaxResult cancelRepair(Long repairId) {
            ParkRepair parkRepair = parkRepairMapper.selectById(repairId);
            if(parkRepair==null)
                return AjaxResult.error("修改失败！报修单不存在");
            parkRepair.setCreateTime(new Date());
            parkRepairMapper.updateById(parkRepair);
           return AjaxResult.success(parkRepairMapper.deleteById(repairId));
    }

    /**
     * 租户端-查询详情
     * @param repairId 报修Id
     * @return
     */
    @Override
    public TenantRepairDtlVo tenantRepairDtl(Long repairId) {
        TenantRepairDtlVo tenantRepairDtlVo = new TenantRepairDtlVo();
        //1.查询维修状态并注入
        ParkRepair parkRepair = parkRepairMapper.selectById(repairId);
        tenantRepairDtlVo.setStatus(parkRepair.getStatus());
        //2.查询报修信息
        RepairInfo repairInfo = new RepairInfo();
        ParkTenant parkTenant = parkTenantMapper.selectById(parkRepair.getTenantId());
        SysUser sysUser = sysUserMapper.selectUserById(parkTenant.getUserId());
        repairInfo.setTenantName(sysUser.getNickName());
        repairInfo.setContactName(parkRepair.getContactName());
        repairInfo.setContactNumber(parkRepair.getContactNumber());
        repairInfo.setRepairStartTime(parkRepair.getRepairStartTime());
        repairInfo.setRepairImg(parkRepair.getRepairImg());
        repairInfo.setRepairNo(parkRepair.getRepairNo());
        repairInfo.setAllocationTime(parkRepair.getAllocationTime());
        repairInfo.setRepairArea(parkRepair.getRepairArea());
        repairInfo.setDescription(parkRepair.getDescription());
        ParkRoom parkRoom = parkRoomMapper.selectById(parkRepair.getRoomId());
        repairInfo.setZoneName(parkZoneMapper.selectById(parkRoom.getZoneId()).getZoneName());
        repairInfo.setBuildingName(parkBuildingMapper.selectById(parkRoom.getBuildingId()).getBuildingName());
        repairInfo.setFloorName(parkFloorMapper.selectById(parkRoom.getFloorId()).getFloorName());
        repairInfo.setRoomName(parkRoom.getRoomName());
        tenantRepairDtlVo.setRepairInfo(repairInfo);
        //3.查询维修信息
        MaintainInfo maintainInfo = new MaintainInfo();
        maintainInfo.setRepairStartTime(parkRepair.getRepairStartTime());
        maintainInfo.setRepairEndTime(parkRepair.getRepairEndTime());
        SysUser worker = sysUserMapper.selectUserById(parkWorkerMapper.selectById(parkRepair.getWorkerId()).getUserId());
        maintainInfo.setWorkerName(worker.getNickName());
        maintainInfo.setWorkerNumber(worker.getPhonenumber());
        tenantRepairDtlVo.setMaintainInfo(maintainInfo);
        return tenantRepairDtlVo;
    }

    /**
     * 租户端-新建报修
     * @param tenantRepairAddParam 新建信息
     * @return
     */
    @Override
    @Transactional
    public AjaxResult tenantAddRepair(TenantRepairAddParam tenantRepairAddParam) throws ParseException {
        //1.新建报修单
        ParkRepair parkRepair = new ParkRepair();
        //2.注入填好的信息
        parkRepair.setContactName(tenantRepairAddParam.getContactName());
        parkRepair.setContactNumber(tenantRepairAddParam.getContactNumber());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(  "yyyy-MM-dd HH:mm:ss");
        parkRepair.setRepairStartTime(simpleDateFormat.parse(tenantRepairAddParam.getRepairStartTime()));
        parkRepair.setRepairArea(tenantRepairAddParam.getRepairArea());
        parkRepair.setDescription(tenantRepairAddParam.getDescription());
        parkRepair.setRepairImg(tenantRepairAddParam.getRepair_img());
        //3.查询租户id
        LambdaQueryWrapper <ParkTenant>wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ParkTenant::getUserId,SecurityUtils.getUserId());
        ParkTenant parkTenant = parkTenantMapper.selectOne(wrapper);
        parkRepair.setTenantId(parkTenant.getTenantId());
        //4.查询房间id
        LambdaQueryWrapper <ParkTenantRoom>roomWrapper = Wrappers.lambdaQuery();
        roomWrapper.eq(ParkTenantRoom::getTenantId,parkTenant.getTenantId());
        ParkTenantRoom parkTenantRoom = parkTenantRoomMapper.selectOne(roomWrapper);
        System.out.println(parkTenantRoom==null);
        parkRepair.setRoomId(parkTenantRoom.getRoomId());
        //5.设置其他字段
        parkRepair.setCreateBy(SecurityUtils.getUsername());
        parkRepair.setCreateTime(new Date());
        parkRepair.setRepairNo(UUID.randomUUID().toString());
        parkRepair.setStatus(Constants.REPAIR_ALLOCATION);
        return AjaxResult.success( parkRepairMapper.insert(parkRepair));
    }

    /**
     * 管理端-工单查询
     * @param param 分页信息
     * @return
     */
    @Override
    public List manageRepairList(ManageRepairParam param) {
        QueryWrapper tenantWrapper = Wrappers.query();
        tenantWrapper.eq("user_id",SecurityUtils.getUserId());
        ParkTenant parkTenant = parkTenantMapper.selectOne(tenantWrapper);
        QueryWrapper wrapper = Wrappers.query();
        wrapper.eq("status",param.getStatus());
        if(StringUtils.isNotEmpty(param.getRepairNo()))
            wrapper.like("repair_no",param.getRepairNo());
        Page page = new Page(param.getPageNum(),param.getPageSize());
        Long size = parkRepairMapper.selectPage(page,wrapper).getSize();
        List list = parkRepairMapper.selectPage(page, wrapper).getRecords();
        //对联系人处理
        if(list.size()!=0){
            for(int i= 0;i<size;i++){
                ParkRepair parkRepair = (ParkRepair) list.get(i);
                SysUser sysUser = sysUserMapper.selectUserById(parkWorkerMapper.selectById(parkRepair.getWorkerId()).getUserId());
                String name = sysUser.getNickName()+"（"+sysUser.getPhonenumber()+"）";
                ((ParkRepair) list.get(i)).setContactName(name);
            }
        }
        return list;
    }

    @Override
    public ManageRepairDtlVo manageRepairDtl(Long repairId) {
        ManageRepairDtlVo manageRepairDtlVo = new ManageRepairDtlVo();
        //1.查询维修状态并注入
        ParkRepair parkRepair = parkRepairMapper.selectById(repairId);
        manageRepairDtlVo.setStatus(parkRepair.getStatus());
        //2.查询报修信息
        RepairInfo repairInfo = new RepairInfo();
        ParkTenant parkTenant = parkTenantMapper.selectById(parkRepair.getTenantId());
        SysUser sysUser = sysUserMapper.selectUserById(parkTenant.getUserId());
        repairInfo.setTenantName(sysUser.getNickName());
        repairInfo.setContactName(parkRepair.getContactName());
        repairInfo.setContactNumber(parkRepair.getContactNumber());
        repairInfo.setRepairStartTime(parkRepair.getRepairStartTime());
        repairInfo.setRepairImg(parkRepair.getRepairImg());
        repairInfo.setRepairNo(parkRepair.getRepairNo());
        repairInfo.setAllocationTime(parkRepair.getAllocationTime());
        repairInfo.setRepairArea(parkRepair.getRepairArea());
        repairInfo.setDescription(parkRepair.getDescription());
        ParkRoom parkRoom = parkRoomMapper.selectById(parkRepair.getRoomId());
        repairInfo.setZoneName(parkZoneMapper.selectById(parkRoom.getZoneId()).getZoneName());
        repairInfo.setBuildingName(parkBuildingMapper.selectById(parkRoom.getBuildingId()).getBuildingName());
        repairInfo.setFloorName(parkFloorMapper.selectById(parkRoom.getFloorId()).getFloorName());
        repairInfo.setRoomName(parkRoom.getRoomName());
        manageRepairDtlVo.setRepairInfo(repairInfo);
        //3.查询维修信息
        MaintainInfo maintainInfo = new MaintainInfo();
        maintainInfo.setRepairStartTime(parkRepair.getRepairStartTime());
        maintainInfo.setRepairEndTime(parkRepair.getRepairEndTime());
        SysUser worker = sysUserMapper.selectUserById(parkWorkerMapper.selectById(parkRepair.getWorkerId()).getUserId());
        maintainInfo.setWorkerName(worker.getNickName());
        maintainInfo.setWorkerNumber(worker.getPhonenumber());
        manageRepairDtlVo.setMaintainInfo(maintainInfo);
        return manageRepairDtlVo;
    }

    /**
     * 返回维修工列表
     * @return
     */
    @Override
    public List workerList() {
        List <ParkWorker>list = parkWorkerMapper.selectList(new QueryWrapper<>());
        List<ParkRepair> repair = parkRepairMapper.selectList(new QueryWrapper<>());
        //筛选
        List res = new ArrayList();
       for(int i = 0 ;i<list.size();i++){
           for(int j = 0;j<repair.size();j++){
               if(list.get(i).getWorkerId()==repair.get(j).getWorkerId()){
                   list.remove(i);
               }
               SysUser sysUser = sysUserMapper.selectUserById(list.get(i).getUserId());
               //加密
               String newNumber = EncryptPhoneNumber.encryptPhoneNumber( list.get(i).getWorkerNumber());
               WorkerListVo workerListVo = new WorkerListVo();
               workerListVo.setWorkerName(sysUser.getNickName()+"（"+newNumber+"）");
               res.add(workerListVo);

           }
       }
        return res;
    }

    /**
     * 管理端-指派维修工
     * @param assignWorkerParam 参数
     * @return
     */
    @Override
    @Transactional
    public AjaxResult assignWorker(AssignWorkerParam assignWorkerParam ) {
        //判断报修单状态-是否已经被接单
        ParkRepair parkRepair = parkRepairMapper.selectById(assignWorkerParam.getRepairId());
        //取消指派
        if(Constants.REPAIR_COPE.equals(parkRepair.getStatus())){
            parkRepair.setStatus(Constants.REPAIR_CANCEL);
            parkRepair.setCompleteCancelTime(new Date());
            return AjaxResult.success( parkRepairMapper.updateById(parkRepair));
        }
        //指派
        //判断是否为已经被取消然后指派的报修单，如果是，新建一张报修单
        if(parkRepair.getStatus().equals(Constants.REPAIR_CANCEL)){
            ParkRepair newRepair = ParkRepairMapping.INSTANCE.toRepair(parkRepair);
            //设置新的工人
            newRepair.setRepairId(null);
            newRepair.setWorkerId(assignWorkerParam.getWorkerId());
            newRepair.setCreateTime(new Date());
            newRepair.setCreateBy(SecurityUtils.getUsername());
            newRepair.setCompleteCancelTime(null);
            return  AjaxResult.success(parkRepairMapper.insert(newRepair));
        }
        //直接选择维修工
        parkRepair.setWorkerId(assignWorkerParam.getWorkerId());
        parkRepair.setStatus(Constants.REPAIR_COPE);
        return AjaxResult.success(parkRepairMapper.updateById(parkRepair));
    }


}

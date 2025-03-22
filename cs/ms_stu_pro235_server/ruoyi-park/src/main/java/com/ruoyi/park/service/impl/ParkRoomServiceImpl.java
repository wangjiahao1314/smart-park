package com.ruoyi.park.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.park.domain.entity.ParkRoom;
import com.ruoyi.park.domain.param.room.AddRoomParam;
import com.ruoyi.park.domain.param.room.EditRoomParam;
import com.ruoyi.park.domain.vo.room.*;
import com.ruoyi.park.mapper.ParkFloorMapper;
import com.ruoyi.park.mapper.ParkRoomMapper;
import com.ruoyi.park.mapper.ParkTenantRoomMapper;
import com.ruoyi.park.mapper.ParkZoneMapper;
import com.ruoyi.park.mapping.ParkRoomMapping;
import com.ruoyi.park.service.IParkBuildingService;
import com.ruoyi.park.service.IParkRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author hezining
 * @date 2025/3/7 10:18
 * @description: 房间服务层实现类
 */

@Service
@RequiredArgsConstructor
public class ParkRoomServiceImpl extends ServiceImpl<ParkRoomMapper, ParkRoom>
        implements IParkRoomService {

    private final ParkZoneMapper zoneMapper;

    private final ParkFloorMapper floorMapper;

    private final IParkBuildingService buildingService;

    private final ParkRoomMapper roomMapper;

    private ParkTenantRoomMapper parkTenantRoomMapper;

    /**
     * 新增房间
     *
     * @param param
     * @return
     */
    @Override
    public Boolean addRoom(AddRoomParam param) {
        // 判断房间参数的合法性
        checkRoomInfo(param.getBuildingId(), param.getRoomName(), null);

        ParkRoom room = ParkRoomMapping.INSTANCE.to(param);
        room.setShelfStatus("0").setShelfTime(new Date());
        return save(room);
    }


    /**
     * 编辑房间信息
     *
     * @param param
     * @return
     */
    @Override
    public int editRoom(EditRoomParam param) {
        // 判断房间参数的合法性
        checkRoomInfo(param.getBuildingId(), param.getRoomName(), param.getRoomId());

        return roomMapper.updateById(ParkRoomMapping.INSTANCE.to(param));
    }

    /**
     * 根据房间ID查询房间详情
     *
     * @param roomId
     * @return
     */
    @Override
    public RoomInfoVo getRoomInfo(Long roomId) {
        return ParkRoomMapping.INSTANCE.toVo(roomMapper.selectById(roomId));
    }




    /**
     * 判断房间参数的合法性
     * (一栋楼宇的房间名称唯一)
     *
     * @param buildingId
     * @param roomName
     */
    private void checkRoomInfo(Long buildingId, String roomName, Long roomId) {
        if (roomMapper.checkRoomNameDuplicate(buildingId, roomName, roomId)) {
            throw new ServiceException("房间名称已存在");
        }
    }



}

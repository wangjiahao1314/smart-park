package com.ruoyi.web.controller.park;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.park.domain.param.room.*;
import com.ruoyi.park.domain.vo.room.RoomInfoVo;
import com.ruoyi.park.domain.vo.room.RoomListVo;
import com.ruoyi.park.domain.vo.room.RoomRecordsVo;
import com.ruoyi.park.domain.vo.room.RoomStatusInfoVo;
import com.ruoyi.park.service.IParkRoomRelatedService;
import com.ruoyi.park.service.IParkRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hezining
 * @date 2025/3/8 15:25
 * @description: 房间控制层
 */

@RestController
@RequestMapping("/room")
@Api(tags = "管理端房间模块")
@RequiredArgsConstructor
public class ParkRoomController {

    private final IParkRoomService roomService;

    private final IParkRoomRelatedService roomRelatedService;

    /**
     * 新增楼宇
     *
     * @param param
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("新增房间")
    @Log(title = "房间管理", businessType = BusinessType.INSERT)
    public R addRoom(@Validated @RequestBody AddRoomParam param) {

        return R.toR(roomService.addRoom(param), "新增房间成功", "新增房间失败");
    }

    /**
     * 批量删除房间信息
     *
     * @param roomIds
     * @return
     */
    @DeleteMapping("/{roomIds}")
    @ApiOperation("批量删除房间")
    @Log(title = "房间管理", businessType = BusinessType.DELETE)
    public R removeRoom(
            @ApiParam(value = "房间ID数组，多个ID用逗号分隔", required = true)
            @NotNull(message = "房间ID不能为空")
            @PathVariable(value = "roomIds") Long[] roomIds) {
        return R.toR(roomRelatedService.removeRoom(roomIds), "删除房间成功", "删除房间失败");
    }

    /**
     * 编辑房间信息
     *
     * @param param
     * @return
     */
    @PutMapping
    @ApiOperation("编辑房间信息")
    @Log(title = "房间管理", businessType = BusinessType.UPDATE)
    public R editRoom(@Validated @RequestBody EditRoomParam param) {
        return R.toR(roomService.editRoom(param), "编辑房间成功", "编辑房间失败");
    }


    /**
     * 编辑上架状态
     *
     * @param param
     * @return
     */
    @PutMapping("/shelf")
    @ApiOperation("编辑上架状态")
    public R editShelfStatus(EditShelfStatusParam param) {
        return R.toR(roomRelatedService.editShelfTime(param), "编辑上架状态成功", "编辑上架状态失败");
    }

    /**
     * 根据房间ID查询房间详情
     *
     * @param roomId
     * @return
     */
    @GetMapping("/{roomId}")
    @ApiOperation("根据房间ID查询房间详情")
    public R<RoomInfoVo> getRoomInfo(
            @ApiParam(value = "房间ID", required = true, example = "1")
            @NotNull(message = "房间ID不能为空")
            @PathVariable(value = "roomId") Long roomId) {
        return R.ok(roomService.getRoomInfo(roomId));
    }

    /**
     * 分页查询房间信息（列表）
     *
     * @param param
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("分页查询房间信息（列表）")
    public TableDataInfo<RoomListVo> roomList(RoomListParam param) {
        return roomRelatedService.getPageRoomList(param);
    }

    @GetMapping("record/{roomId}")
    @ApiOperation("查询房间记录信息")
    public R<List<RoomRecordsVo>> roomRecords(@PathVariable(value = "roomId") Long roomId) {
        return R.ok(roomRelatedService.getRoomRecords(roomId));
    }

    /**
     * 查询房间房态列表
     *
     * @param param
     * @return
     */
    @GetMapping("/room")
    @ApiOperation("查询房间房态列表（房态）")
    public R<RoomStatusInfoVo> roomStatusList(RoomStatusListParam param) {
        return R.ok(roomRelatedService.roomStatusList(param));
    }


}

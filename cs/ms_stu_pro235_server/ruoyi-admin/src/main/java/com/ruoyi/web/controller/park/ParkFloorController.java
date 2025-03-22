package com.ruoyi.web.controller.park;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.park.domain.param.floor.AddFloorParam;
import com.ruoyi.park.domain.param.floor.EditFloorParam;
import com.ruoyi.park.domain.vo.floor.FloorSelectVo;
import com.ruoyi.park.service.IParkBuildingFloorService;
import com.ruoyi.park.service.IParkFloorService;
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
 * @date 2025/3/7 22:02
 * @description: 楼层控制层
 */

@RestController
@RequestMapping("/floor")
@Api(tags = "管理端楼层模块")
@RequiredArgsConstructor
public class ParkFloorController extends BaseController {

    private final IParkFloorService floorService;

    private final IParkBuildingFloorService buildingFloorService;

    /**
     * 新增楼层
     *
     * @param param
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("新增楼层")
    @Log(title = "楼层管理", businessType = BusinessType.INSERT)
    public R addFloor(@Validated @RequestBody AddFloorParam param) {
        return R.toR(buildingFloorService.addFloor(param), "新增楼层成功", "新增楼层失败");
    }

    /**
     * 批量删除楼层
     *
     * @param floorIds
     * @return
     */
    @DeleteMapping("{floorIds}")
    @ApiOperation("批量删除楼层")
    @Log(title = "楼层管理", businessType = BusinessType.DELETE)
    public R removeFloor(
            @ApiParam(value = "楼层ID数组，多个ID用逗号分隔", required = true)
            @NotNull(message = "楼层ID不能为空")
            @PathVariable(value = "floorIds") Long[] floorIds) {
        return R.toR(floorService.removeFloor(floorIds), "删除楼层成功", "删除楼层失败");
    }

    /**
     * 编辑楼层信息
     *
     * @param param
     * @return
     */
    @PutMapping
    @ApiOperation("编辑楼层信息")
    @Log(title = "楼层管理", businessType = BusinessType.UPDATE)
    public R editFloor(@Validated @RequestBody EditFloorParam param) {
        return R.toR(buildingFloorService.editFloor(param), "编辑楼层信息成功", "编辑楼层信息失败");
    }

    /**
     * 查询楼层列表（列表and下拉框）
     *
     * @param buildingId
     * @return
     */
    @GetMapping("select")
    @ApiOperation("查询楼层列表（列表、下拉框）")
    public R<List<FloorSelectVo>> floorSelect(
            @ApiParam(value = "楼宇ID", required = false, example = "1")
            Long buildingId) {
        return R.ok(floorService.floorSelect(buildingId));
    }


}

package com.ruoyi.web.controller.park;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.park.domain.param.building.AddBuildingParam;
import com.ruoyi.park.domain.param.building.BuildingListParam;
import com.ruoyi.park.domain.param.building.EditBuildingParam;
import com.ruoyi.park.domain.vo.building.BuildingInfoVo;
import com.ruoyi.park.domain.vo.building.BuildingListVo;
import com.ruoyi.park.domain.vo.building.BuildingSelectVo;
import com.ruoyi.park.service.IParkBuildingService;
import com.ruoyi.park.service.impl.ParkBuildingFloorServiceImpl;
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
 * @date 2025/3/6 14:30
 * @description: 楼宇控制层
 */

@RestController
@RequestMapping("/building")
@Api(tags = "管理端楼宇模块")
@RequiredArgsConstructor
public class ParkBuildingController extends BaseController {

    private final IParkBuildingService buildingService;

    private final ParkBuildingFloorServiceImpl buildingFloorService;

    /**
     * 新增楼宇
     *
     * @param param
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("新增楼宇")
    @Log(title = "楼宇管理", businessType = BusinessType.INSERT)
    public R addBuilding(@Validated @RequestBody AddBuildingParam param) {

        return R.toR(buildingService.addBuilding(param), "新增楼宇成功", "新增楼宇失败");
    }

    /**
     * 批量删除楼宇
     *
     * @param buildingIds
     * @return
     */
    @DeleteMapping("{buildingIds}")
    @ApiOperation("批量删除楼宇")
    @Log(title = "楼宇管理", businessType = BusinessType.DELETE)
    public R removeBuilding(
            @ApiParam(value = "楼宇ID数组，多个ID用逗号分隔", required = true)
            @NotNull(message = "楼宇ID不能为空")
            @PathVariable(value = "buildingIds") Long[] buildingIds) {
        return R.toR(buildingFloorService.removeBuilding(buildingIds), "删除楼宇成功", "删除楼宇失败");
    }

    /**
     * 编辑楼宇信息
     *
     * @param param
     * @return
     */
    @PutMapping
    @ApiOperation("编辑楼宇信息")
    @Log(title = "楼宇管理", businessType = BusinessType.UPDATE)
    public R editBuilding(@Validated @RequestBody EditBuildingParam param) {
        return R.toR(buildingService.editBuilding(param), "编辑楼宇成功", "编辑楼宇失败");
    }

    /**
     * 根据楼宇ID获取楼宇详情
     *
     * @param buildingId
     * @return
     */
    @GetMapping("/{buildingId}")
    @ApiOperation("根据楼宇ID获取楼宇详情")
    public R<BuildingInfoVo> getBuildingInfo(
            @ApiParam(value = "楼宇ID", required = true, example = "1")
            @NotNull(message = "楼宇ID不能为空")
            @PathVariable(value = "buildingId") Long buildingId) {
        return R.ok(buildingService.getBuildingInfo(buildingId));
    }

    /**
     * 分页查询楼宇列表
     *
     * @param param
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("分页查询楼宇列表")
    public TableDataInfo<BuildingListVo> buildingList(@Validated BuildingListParam param) {
        startPage();
        return getDataTable(buildingService.selectBuildingList(param));
    }

    /**
     * 下拉框查询楼宇
     *
     * @param zoneId
     * @return
     */
    @GetMapping("/select")
    @ApiOperation("下拉框查询楼宇")
    public R<List<BuildingSelectVo>> buildingSelect(
            @ApiParam(value = "园区ID", required = false, example = "1")
            Long zoneId) {
        return R.ok(buildingService.buildingSelect(zoneId));
    }


}

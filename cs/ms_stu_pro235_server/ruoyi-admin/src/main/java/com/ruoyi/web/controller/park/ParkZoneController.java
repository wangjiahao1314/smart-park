package com.ruoyi.web.controller.park;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.park.domain.entity.ParkZone;
import com.ruoyi.park.domain.param.zone.AddZoneParam;
import com.ruoyi.park.domain.param.zone.EditZoneParam;
import com.ruoyi.park.domain.param.zone.ZoneListParam;
import com.ruoyi.park.domain.vo.zone.ZoneListVo;
import com.ruoyi.park.domain.vo.zone.ZoneSelectVo;
import com.ruoyi.park.mapping.ParkZoneMapping;
import com.ruoyi.park.service.IParkZoneService;
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
 * @date 2025/3/3 20:19
 * @description: 园区控制层
 */

@RestController
@RequestMapping("/zone")
@Api(tags = "管理端园区模块")
@RequiredArgsConstructor
public class ParkZoneController extends BaseController {

    private final IParkZoneService zoneService;


    /**
     * 新增园区
     *
     * @param param
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("新增园区")
    @Log(title = "园区管理", businessType = BusinessType.INSERT)
    public AjaxResult addZone(@Validated @RequestBody AddZoneParam param) {

        return toAjax(zoneService.addZone(param));
    }

    /**
     * 园区下拉框信息
     *
     * @return
     */
    @GetMapping("/select")
    @ApiOperation("园区下拉框信息")
    public R<List<ZoneSelectVo>> zoneSelect() {

        return R.ok(ParkZoneMapping.INSTANCE.toSelectVo(zoneService.lambdaQuery().eq(ParkZone::getDelFlag, UserStatus.OK.getCode()).list()));
    }

    /**
     * 修改园区
     *
     * @param param
     * @return
     */
    @PutMapping
    @ApiOperation("修改园区信息")
    @Log(title = "园区管理", businessType = BusinessType.UPDATE)
    public R editZone(@Validated @RequestBody EditZoneParam param) {
        return R.toR(zoneService.editZone(param), "修改园区成功", "修改园区失败");
    }

    /**
     * 批量删除园区
     *
     * @param zoneIds 园区ID
     * @return
     */
    @DeleteMapping("/{zoneIds}")
    @ApiOperation("批量删除园区")
    @Log(title = "园区管理", businessType = BusinessType.DELETE)
    public R removeZone(@ApiParam(value = "园区ID数组，多个ID用逗号分隔", required = true)
                        @NotNull(message = "园区ID不能为空")
                        @PathVariable("zoneIds") Long[] zoneIds) {
        return R.toR(zoneService.removeZone(zoneIds), "删除成功", "删除失败");
    }

    /**
     * 根据园区ID获取园区详情
     *
     * @param zoneId
     * @return
     */
    @GetMapping("/{zoneId}")
    @ApiOperation("根据园区Id获取园区详情")
    public R getZoneInfo(
            @ApiParam(value = "园区ID", required = true, example = "1")
            @NotNull(message = "园区ID不能为空")
            @PathVariable(value = "zoneId") Long zoneId) {
        return R.ok(zoneService.getZoneInfo(zoneId));
    }

    /**
     * 分页获取园区列表
     *
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("分页获取园区列表")
    public TableDataInfo<ZoneListVo> zoneList(@Validated ZoneListParam param) {

        Page<ParkZone> page = zoneService.selectZoneList(param);
        return new TableDataInfo(ParkZoneMapping.INSTANCE.toListVo(page.getRecords()), (int) page.getTotal(), HttpStatus.SUCCESS, "查询成功");
    }
}

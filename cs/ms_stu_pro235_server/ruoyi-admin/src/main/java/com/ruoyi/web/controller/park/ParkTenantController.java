package com.ruoyi.web.controller.park;


import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.park.domain.param.tenant.AddTenantParam;
import com.ruoyi.park.domain.param.tenant.EditTenantInfoParam;
import com.ruoyi.park.domain.param.tenant.TenantListParam;
import com.ruoyi.park.domain.vo.tenant.TenantInfoVo;
import com.ruoyi.park.domain.vo.tenant.TenantListVo;
import com.ruoyi.park.service.IParkTenantService;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author hezining
 * @date 2025/2/25 21:46
 * @description: 租户控制层
 */

@RestController
@RequestMapping("/tenant")
@Api(tags = "管理端租户模块")
@RequiredArgsConstructor
public class ParkTenantController extends BaseController {

    private final IParkTenantService tenantService;

    private final ISysUserService userService;

    /**
     * 新增租户
     *
     * @param param
     * @return
     */

    @Log(title = "租户管理", businessType = BusinessType.INSERT)
    @ApiOperation("新增租户")
    @PostMapping("/add")
    public R addTenant(@Validated @RequestBody AddTenantParam param) {
        SysUser user = new SysUser(param.getUserName(), param.getEmail(), param.getPhonenumber());
        if (!userService.checkUserNameUnique(user)) {
            return R.fail("新增用户'" + user.getUserName() + "'失败，账号已存在");
        } else if (StringUtils.isNotEmpty(param.getPhonenumber()) && !userService.checkPhoneUnique(user)) {
            return R.fail("新增用户'" + param.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
            return R.fail("新增用户'user" + user.getUserName() + "'失败，邮箱账号已存在");
        } else if (StringUtils.isNotEmpty(param.getIdCardNumber()) && tenantService.checkIdCardNumberUnique(param.getIdCardNumber())) {
            return R.fail("新增用户'" + user.getUserName() + "'失败，身份证已存在");
        }


        return R.toR(tenantService.addTenant(param), "新增租户成功", "新增租户失败");
    }

    /**
     * 根据租户ID获取租户详情
     *
     * @param tenantId 租户ID
     * @return
     */
    @ApiOperation("根据租户ID获取租户详情")
    @GetMapping("/{tenantId}")
    public R<TenantInfoVo> getTenantInfo(
            @ApiParam(value = "租户ID", required = true, example = "1")
            @NotNull(message = "租户ID不能为空")
            @PathVariable(value = "tenantId") Long tenantId) {
        return R.ok(tenantService.getTenantInfo(tenantId));
    }

    /**
     * 编辑租户信息
     *
     * @param param
     * @return
     */
    @ApiOperation("编辑租户信息")
    @Log(title = "租户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R editTenantInfo(@Validated @RequestBody EditTenantInfoParam param) {
        return R.toR(tenantService.editTenantInfo(param), "编辑租户成功", "编辑租户失败");
    }

    /**
     * 批量删除租户
     *
     * @param tenantIds 租户ID数组
     * @return 操作结果
     */
    @ApiOperation(value = "批量删除租户", notes = "根据租户ID数组批量删除租户信息")
    @Log(title = "租户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tenantIds}")
    public R removeTenant(
            @ApiParam(value = "租户ID数组，多个ID用逗号分隔", required = true)
            @NotNull(message = "租户ID不能为空")
            @PathVariable("tenantIds") Long[] tenantIds) {
        return R.toR(tenantService.removeTenant(tenantIds), "删除租户成功", "删除租户失败");
    }


    /**
     * 分页获取租户列表
     *
     * @param param
     * @return TableDataInfo
     */
    @ApiOperation("分页获取租户列表")
    @GetMapping("/list")
    public TableDataInfo<TenantListVo> tenantList(@Validated TenantListParam param) {
        return tenantService.selectTenantList(param);
    }
}

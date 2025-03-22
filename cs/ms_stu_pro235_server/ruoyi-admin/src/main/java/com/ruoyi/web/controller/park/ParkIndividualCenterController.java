package com.ruoyi.web.controller.park;

import com.ruoyi.common.core.domain.AjaxResult;

import com.ruoyi.park.domain.param.worker.UpdatePasswordParam;
import com.ruoyi.park.service.IParkIndividualCenterService;
import com.ruoyi.park.service.IParkWorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author raoce
 * @date 2025/3/8 23:30
 * @description: 个人中心模块
 */
@RestController
@RequestMapping("/individualCenter")
@Api(tags = "个人中心模块")
public class ParkIndividualCenterController {
    @Autowired
    private IParkIndividualCenterService individualCenter;
    @Autowired
    private IParkWorkerService parkWorkerService;
    /**
     * 维修端查看个人中心
     * @return
     */
    @ApiOperation("维修工查看个人中心")
    @GetMapping("/workerIndividual")
    public AjaxResult individualCenter(){
        return AjaxResult.success(individualCenter.individualCenter());
    }

    /**
     * 租户端查看个人中心
     * @return
     */
    @ApiOperation("租户查看个人中心")
    @GetMapping("/tenantIndividual")
    public AjaxResult tenantIndividualCenter(){
        return AjaxResult.success(individualCenter.tenantIndividualCenter());
    }


    /**
     * 修改密码
     * @param updatePasswordParam 修改密码实体参数
     * @return
     */
    @PostMapping
    @ApiOperation("修改密码")
    @ApiImplicitParam(name = "updatePasswordQuery" ,value = "修改密码参数")
    public AjaxResult updatePassword(@Validated @RequestBody UpdatePasswordParam updatePasswordParam){
        return individualCenter.updatePassword(updatePasswordParam);
    }

}

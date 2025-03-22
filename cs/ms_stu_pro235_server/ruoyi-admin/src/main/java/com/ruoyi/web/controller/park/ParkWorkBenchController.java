package com.ruoyi.web.controller.park;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.park.service.IParkBillService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author raoce
 * @date 2025/3/13 20:14
 * @description: 工作台模块
 */
@RestController
@RequestMapping("/repair")
@Api(tags = "工作台模块")
@RequiredArgsConstructor
public class ParkWorkBenchController extends BaseController {
    @Autowired
    private IParkBillService parkBillService;


}

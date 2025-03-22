package com.ruoyi.web.controller.park;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;

import com.ruoyi.park.domain.entity.ParkContract;
import com.ruoyi.park.domain.vo.backcontract.ParkBackContractVo;
import com.ruoyi.park.domain.vo.contract.*;

import com.ruoyi.park.service.IParkBackContractService;
import com.ruoyi.park.service.IParkContractService;

import io.swagger.annotations.Api;


import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

import java.util.List;



@RestController
@RequestMapping("/contract")
@Api(tags = "合同管理模块")
public class ParkContractController extends BaseController {

    @Autowired
    private IParkContractService contractService;


    @Autowired
    private IParkBackContractService backContractService;


    @ApiOperation("合同列表")
    @GetMapping("/list")
    public TableDataInfo<List<ParkContractListVo>> list(@Validated PageQuery pageQuery, @Validated ParkContractGetVo vo) {
        Page<ParkContractListVo> page = contractService.toPage(pageQuery, vo);

        return getDataTable(page.getRecords(), page.getTotal());


    }

    @ApiOperation("合同详情")
    @GetMapping("/detail")
    public R<ParkContractDtlVo> detail(@Validated ParkContractIdVo vo) {

        ParkContractDtlVo dtlById = contractService.getDtlById(vo.getContractId());
        if (dtlById == null) {
            return R.fail("合同不存在");
        }

        return R.ok(dtlById);

    }

    @ApiOperation("合同新增")
    @PostMapping("/add")
    public R add(@Validated ParkContractAddVo vo) {


        int add = contractService.add(vo);
        if (add > 0) {
            return R.ok();
        } else {
            return R.fail("新增失败");
        }


    }

    @ApiOperation("合同修改")
    @PutMapping("/update")
    public R update(@Validated ParkContractUpdateVo vo) {
        int update = contractService.update(vo);
        if (update > 0) {
            return R.ok();

        } else {
            return R.fail("修改失败");
        }

    }

    @ApiOperation("合同删除")
    @DeleteMapping("/delete")
    public R delete(@Validated List<Long> vo) {


        int delete = contractService.getBaseMapper().deleteBatchIds(vo);
        if (delete > 0) {
            return R.ok();
        } else {
            return R.fail("删除失败");
        }


    }


    @ApiOperation("修改合同状态（批量）")
    @PutMapping("/revise")
    public R put(@Validated ParkContractByIdListVo vo) {


        List<ParkContract> parkContracts = new ArrayList<>();
        for (Long id : vo.getContractListId()) {
            ParkContract parkContract = contractService.getById(id);
            if (parkContract == null) {
                // 如果某个合同不存在，可以选择记录日志或直接返回失败
                return R.fail("合同ID为 " + id + " 的记录不存在");
            }
            parkContract.setContractType(vo.getContractType());
            parkContracts.add(parkContract);
        }

        boolean b = contractService.updateBatchById(parkContracts);
        if (b) {
            return R.ok("批量修改状态成功");
        } else {
            return R.fail("批量修改状态失败");
        }


    }

    @ApiOperation("退租编辑")
    @DeleteMapping("/back")
    public R backContract(@Validated ParkBackContractVo vo) {

        int edit = backContractService.edit(vo);
        if (edit > 0) {
            return R.ok("退租编辑成功");
        } else {
            return R.fail("退租编辑失败");
        }


    }

}
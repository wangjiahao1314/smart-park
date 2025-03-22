package com.ruoyi.park.domain.vo.contract;

import lombok.Data;

import java.util.Date;

/**
 * @author hezining
 * @date 2025/3/12 15:15
 * @description: 合同最新信息
 */

@Data
public class LatestContractInfo {

    private String contractType;

    private Date updateTime;
}

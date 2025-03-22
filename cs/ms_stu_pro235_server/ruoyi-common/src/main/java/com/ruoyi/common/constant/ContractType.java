package com.ruoyi.common.constant;

/**
 * @author hezining
 * @date 2025/3/10 0:20
 * @description: 合同类型  0: 待签定(default); 1:执行中; 2:已终止 3申请退租；
 */
public class ContractType {

    /* 0 待签定 */
    public static final String WAITING_SIGNATURE = "0";

    /* 1 执行中 */
    public static final String IN_PROGRESS = "1";

    /* 2 已终止 */
    public static final String TERMINATED = "2";

    /* 4 申请退租 */
    public static final String APPLY_TERMINATED = "3";
}

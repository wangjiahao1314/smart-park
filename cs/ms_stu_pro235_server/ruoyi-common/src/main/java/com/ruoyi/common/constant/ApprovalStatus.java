package com.ruoyi.common.constant;

/**
 * @author hezining
 * @date 2025/3/13 12:38
 * @description: 审批状态（0待审批 1审批通过 2已拒绝 3已取消）
 */

public class ApprovalStatus {

    /* 待审批 */
    public static final String PENDING_APPROVAL = "0";

    /* 审批通过 */
    public static final String APPROVED = "1";

    /* 已拒绝 */
    public static final String REJECTED = "2";

    /* 已取消 */
    public static final String CANCELLED = "3";
}

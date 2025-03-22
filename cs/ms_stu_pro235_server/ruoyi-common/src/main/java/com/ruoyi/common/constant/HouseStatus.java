package com.ruoyi.common.constant;

import lombok.Data;

/**
 * @author hezining
 * @date 2025/3/2 11:28
 * @description: 房屋状态
 */

public class HouseStatus {

    /* 已取消 */
    public static final String CANCELLED = "0";

    /* 在租 */
    public static final String RENTING = "1";

    /* 退租 */
    public static final String RETURN_RENTING = "2";
}

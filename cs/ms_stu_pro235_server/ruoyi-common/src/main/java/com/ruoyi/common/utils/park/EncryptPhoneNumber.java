package com.ruoyi.common.utils.park;

/**
 * @author raoce
 * @date 2025/3/13 21:06
 * @description:
 */
public class EncryptPhoneNumber {
    public static String encryptPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }
            return phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7);
    }
}

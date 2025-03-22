package com.ruoyi.common.core.domain;

import java.io.Serializable;

import com.ruoyi.common.constant.HttpStatus;

/**
 * 响应信息主体
 *
 * @author ruoyi
 */
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    public static final int SUCCESS = HttpStatus.SUCCESS;

    /**
     * 失败
     */
    public static final int FAIL = HttpStatus.ERROR;

    private int code;

    private String msg;

    private T data;

    /**
     * 返回成功信息
     *
     * @return 操作结果
     */
    public static <T> R<T> ok() {
        return restResult(null, SUCCESS, "操作成功");
    }

    /**
     * 返回成功信息
     *
     * @param data 数据
     * @return 操作结果
     */
    public static <T> R<T> ok(T data) {
        return restResult(data, SUCCESS, "操作成功");
    }

    /**
     * 返回成功信息
     *
     * @param data 数据
     * @param msg  消息
     * @return 操作结果
     */
    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    /**
     * 返回失败信息
     *
     * @return 操作结果
     */
    public static <T> R<T> fail() {
        return restResult(null, FAIL, "操作失败");
    }

    /**
     * 返回失败信息
     *
     * @param msg 消息
     * @return 操作结果
     */
    public static <T> R<T> fail(String msg) {
        return restResult(null, FAIL, msg);
    }

    /**
     * 返回失败信息
     *
     * @param data 数据
     * @return 操作结果
     */
    public static <T> R<T> fail(T data) {
        return restResult(data, FAIL, "操作失败");
    }

    /**
     * 返回失败信息
     *
     * @param data 数据
     * @param msg  消息
     * @return 操作结果
     */
    public static <T> R<T> fail(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    /**
     * 返回失败信息
     *
     * @param code 状态码
     * @param msg  消息
     * @return 操作结果
     */
    public static <T> R<T> fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    public static R toR(int rows, String successMsg, String failMsg) {
        return rows > 0 ? R.ok(null, successMsg) : R.fail(null, failMsg);
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    public static R toR(boolean result, String successMsg, String failMsg) {
        return result ? R.ok(null, successMsg) : R.fail(null, failMsg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 判断是否失败
     *
     * @param ret 响应结果
     * @return 是否失败
     */
    public static <T> Boolean isError(R<T> ret) {
        return !isSuccess(ret);
    }

    /**
     * 判断是否成功
     *
     * @param ret 响应结果
     * @return 是否成功
     */
    public static <T> Boolean isSuccess(R<T> ret) {
        return R.SUCCESS == ret.getCode();
    }
}

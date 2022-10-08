package com.tan00xu.vo;

import com.tan00xu.enums.StatusCodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import static com.tan00xu.enums.StatusCodeEnum.FAIL;
import static com.tan00xu.enums.StatusCodeEnum.SUCCESS;

/**
 * 结果返回类
 *
 * @author TAN00XU
 * @date 2022/09/24
 */
@Data
public class Result<T> {

    /**
     * 返回状态
     */
    @Schema(description = "状态")
    private Boolean status;
    /**
     * 返回码
     */
    @Schema(description = "返回码")
    private Integer code;
    /**
     * 返回信息
     */
    @Schema(description = "返回信息")
    private String message;

    /**
     * 返回数据
     */
    @Schema(description = "返回数据")
    private T data;


    /**
     * 成功
     *
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> ok() {
        return restResult(true, null, SUCCESS.getCode(), SUCCESS.getDesc());
    }

    /**
     * 成功
     *
     * @param data 数据
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> ok(T data) {
        return restResult(true, data, SUCCESS.getCode(), SUCCESS.getDesc());
    }

    /**
     * 成功
     *
     * @param data    数据
     * @param message 消息
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> ok(T data, String message) {
        return restResult(true, data, SUCCESS.getCode(), message);
    }

    /**
     * 失败
     *
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> fail() {
        return restResult(false, null, FAIL.getCode(), FAIL.getDesc());
    }

    /**
     * 失败
     *
     * @param statusCodeEnum 状态码枚举
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> fail(StatusCodeEnum statusCodeEnum) {
        return restResult(false, null, statusCodeEnum.getCode(), statusCodeEnum.getDesc());
    }

    /**
     * 失败
     *
     * @param message 消息
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> fail(String message) {
        return restResult(false, message);
    }

    /**
     * 失败
     *
     * @param data 数据
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> fail(T data) {
        return restResult(false, data, FAIL.getCode(), FAIL.getDesc());
    }

    /**
     * 失败
     *
     * @param data    数据
     * @param message 消息
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> fail(T data, String message) {
        return restResult(false, data, FAIL.getCode(), message);
    }

    /**
     * 失败
     *
     * @param code    返回码
     * @param message 消息
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> fail(Integer code, String message) {
        return restResult(false, null, code, message);
    }

    /**
     * 返回结果
     *
     * @param status  状态
     * @param message 消息
     * @return {@link Result}<{@link T}>
     */
    private static <T> Result<T> restResult(Boolean status, String message) {
        Result<T> apiResult = new Result<>();
        apiResult.setStatus(status);
        apiResult.setCode(status ? SUCCESS.getCode() : FAIL.getCode());
        apiResult.setMessage(message);
        return apiResult;
    }

    /**
     * 返回结果
     *
     * @param status  状态
     * @param data    数据
     * @param code    返回码
     * @param message 消息
     * @return {@link Result}<{@link T}>
     */
    private static <T> Result<T> restResult(Boolean status, T data, Integer code, String message) {
        Result<T> apiResult = new Result<>();
        apiResult.setStatus(status);
        apiResult.setData(data);
        apiResult.setCode(code);
        apiResult.setMessage(message);
        return apiResult;
    }

}

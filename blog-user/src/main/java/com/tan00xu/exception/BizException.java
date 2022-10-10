package com.tan00xu.exception;


import com.tan00xu.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.tan00xu.enums.StatusCodeEnum.FAIL;


/**
 * 业务异常类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/01 13:38:43
 */
@Getter
@AllArgsConstructor
public class BizException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code = FAIL.getCode();

    /**
     * 错误信息
     */
    private final String message;

    public BizException(String message) {
        this.message = message;
    }

    /**
     * 业务异常
     *
     * @param statusCodeEnum 状态码枚举
     */
    public BizException(StatusCodeEnum statusCodeEnum) {
        this.code = statusCodeEnum.getCode();
        this.message = statusCodeEnum.getDesc();
    }


}

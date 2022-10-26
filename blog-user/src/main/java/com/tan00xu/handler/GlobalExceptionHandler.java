package com.tan00xu.handler;

import com.tan00xu.exception.BizException;
import com.tan00xu.vo.Result;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static com.tan00xu.enums.StatusCodeEnum.SYSTEM_ERROR;
import static com.tan00xu.enums.StatusCodeEnum.VALID_ERROR;

/**
 * 全局异常处理类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/10 23:02:29
 * SpringMVC会在处理请求之前，把它作为全局异常处理器来处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     *
     * @param e 异常
     * @return 接口异常信息
     */
    @ExceptionHandler(value = BizException.class)
    public Result<?> errorHandler(BizException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数校验异常
     *
     * @param e 异常
     * @return 接口异常信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> errorHandler(MethodArgumentNotValidException e) {
        return Result.fail(
                VALID_ERROR.getCode(),
                Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage()
        );
    }

    /**
     * 处理系统异常
     *
     * @param e 异常
     * @return 接口异常信息
     */
    @ExceptionHandler(Exception.class)
    public Result<?> errorHandler(Exception e) {
        e.printStackTrace();
        return Result.fail(SYSTEM_ERROR.getCode(), SYSTEM_ERROR.getDesc());
    }
}

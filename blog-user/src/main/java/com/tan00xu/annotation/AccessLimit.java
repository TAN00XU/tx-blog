package com.tan00xu.annotation;

import java.lang.annotation.*;


/**
 * 访问限制类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/01 13:03:15
 * @description redis接口限流
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimit {

    /**
     * 单位时间（秒）
     *
     * @return int
     */
    int seconds();

    /**
     * 单位时间最大请求次数
     *
     * @return int
     */
    int maxCount();
}

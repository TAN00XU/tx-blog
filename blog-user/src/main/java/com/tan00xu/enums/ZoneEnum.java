package com.tan00xu.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 区枚举类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/11 00:08:18
 */
@Getter
@AllArgsConstructor
public enum ZoneEnum {

    /**
     * 上海
     */
    SHANGHAI("Asia/Shanghai", "中国上海");

    /**
     * 时区
     */
    private final String zone;

    /**
     * 描述
     */
    private final String desc;

}

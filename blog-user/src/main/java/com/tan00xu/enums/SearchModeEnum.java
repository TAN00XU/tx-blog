package com.tan00xu.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 搜索模式枚举类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/29 18:22:11
 */
@Getter
@AllArgsConstructor
public enum SearchModeEnum {
    /**
     * mysql
     */
    MYSQL("mysql", "mySqlSearchStrategyImpl"),
    /**
     * elasticsearch
     */
    ELASTICSEARCH("elasticsearch", "esSearchStrategyImpl");

    /**
     * 模式
     */
    private final String mode;

    /**
     * 策略
     */
    private final String strategy;

    /**
     * 获取策略
     *
     * @param mode 模式
     * @return {@link String} 搜索策略
     */
    public static String getStrategy(String mode) {
        for (SearchModeEnum value : SearchModeEnum.values()) {
            if (value.getMode().equals(mode)) {
                return value.getStrategy();
            }
        }
        return null;
    }

}

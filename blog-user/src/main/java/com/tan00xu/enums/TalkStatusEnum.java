package com.tan00xu.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 说说状态枚举类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/13 09:40:00
 */
@Getter
@AllArgsConstructor
public enum TalkStatusEnum {
    /**
     * 公开
     *
     * @status 1
     * @description 公开
     */
    PUBLIC(1, "公开"),
    /**
     * 私密
     *
     * @status 2
     * @description 私密
     */
    SECRET(2, "私密");

    /**
     * 说说状态
     */
    private final Integer status;

    /**
     * 说说状态描述
     */
    private final String desc;

}

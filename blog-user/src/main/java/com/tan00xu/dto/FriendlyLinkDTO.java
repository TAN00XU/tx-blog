package com.tan00xu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 友情链接DTO类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/13 13:51:42
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendlyLinkDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 链接名
     */
    private String linkName;

    /**
     * 链接头像
     */
    private String linkAvatar;

    /**
     * 链接地址
     */
    private String linkAddress;

    /**
     * 介绍
     */
    private String linkIntro;

}

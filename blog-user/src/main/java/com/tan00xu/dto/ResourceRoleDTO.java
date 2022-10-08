package com.tan00xu.dto;

import lombok.Data;

import java.util.List;

/**
 * 资源角色dto类
 *
 * @author yezhiqiu
 * @date 2021/07/28
 */
@Data
public class ResourceRoleDTO {

    /**
     * 资源id
     */
    private Integer id;

    /**
     * 路径
     */
    private String url;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 角色标签名列表
     */
    private List<String> roleLabelList;

}

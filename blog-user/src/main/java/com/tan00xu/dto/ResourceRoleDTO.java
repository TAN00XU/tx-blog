package com.tan00xu.dto;

import lombok.Data;

import java.util.List;


/**
 * 资源角色DTO类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/14 15:38:42
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

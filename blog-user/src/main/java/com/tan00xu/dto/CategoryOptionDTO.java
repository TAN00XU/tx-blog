package com.tan00xu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 分类选项DTO类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/26 09:57:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryOptionDTO {

    /**
     * 分类id
     */
    private Integer id;

    /**
     * 分类名
     */
    private String categoryName;

}

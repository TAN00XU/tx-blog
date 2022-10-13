package com.tan00xu.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 标签DTO类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/11 14:26:19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 标签名
     */
    private String tagName;

}

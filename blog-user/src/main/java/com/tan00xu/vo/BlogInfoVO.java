package com.tan00xu.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 博客信息vo类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/09/29 09:23:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "博客信息")
public class BlogInfoVO {

    /**
     * 关于我内容
     */
    @Schema(name = "aboutContent", title = "关于我内容", required = true, type = "String")
    private String aboutContent;

}

package com.tan00xu.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


/**
 * 页面信息类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/09/29 09:26:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "页面")
public class PageVO {

    /**
     * 页面id
     */
    @Schema(name = "id", description = "页面id", required = true, type = "Integer")
    private Integer id;

    /**
     * 页面名
     */
    @NotBlank(message = "页面名称不能为空")
    @Schema(name = "pageName", description = "页面名称", required = true, type = "String")
    private String pageName;

    /**
     * 页面标签
     */
    @NotBlank(message = "页面标签不能为空")
    @Schema(name = "pageLabel", description = "页面标签", required = true, type = "String")
    private String pageLabel;

    /**
     * 页面封面
     */
    @NotBlank(message = "页面封面不能为空")
    @Schema(name = "pageCover", description = "页面封面", required = true, type = "String")
    private String pageCover;

}

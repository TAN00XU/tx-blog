package com.tan00xu.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;


/**
 * 文章VO类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/27 10:39:38
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "文章")
public class ArticleVO {

    /**
     * 文章id
     */
    @Schema(description = "文章id")
    private Integer id;

    /**
     * 标题
     */
    @NotBlank(message = "文章标题不能为空")
    @Schema(description = "文章标题", required = true)
    private String articleTitle;

    /**
     * 内容
     */
    @NotBlank(message = "文章内容不能为空")
    @Schema(description = "文章内容", required = true)
    private String articleContent;

    /**
     * 文章封面
     */
    @Schema(description = "文章缩略图")
    private String articleCover;

    /**
     * 文章分类
     */
    @Schema(description = "文章分类")
    private String categoryName;

    /**
     * 文章标签
     */
    @Schema(description = "文章标签")
    private List<String> tagNameList;

    /**
     * 文章类型
     */
    @Schema(description = "文章类型")
    private Integer type;

    /**
     * 原文链接
     */
    @Schema(description = "原文链接")
    private String originalUrl;

    /**
     * 是否置顶
     */
    @Schema(description = "是否置顶")
    private Integer isTop;

    /**
     * 文章状态 1.公开 2.私密 3.评论可见
     */
    @Schema(description = "文章状态")
    private Integer status;

}

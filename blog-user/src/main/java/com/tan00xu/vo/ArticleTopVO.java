package com.tan00xu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


/**
 * 文章置顶信息VO类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/28 11:57:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "文章置顶")
public class ArticleTopVO {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    @Schema(description = "文章id")
    private Integer id;

    /**
     * 置顶状态
     */
    @NotNull(message = "置顶状态不能为空")
    @Schema(description = "文章置顶状态")
    private Integer isTop;

}

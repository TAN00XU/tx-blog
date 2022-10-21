package com.tan00xu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


/**
 * 分类VO类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/21 12:59:47
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "分类")
public class CategoryVO {

    /**
     * id
     */
    @Schema(description = "分类id")
    private Integer id;

    /**
     * 分类名
     */
    @NotBlank(message = "分类名不能为空")
    @Schema(description = "分类名", required = true)
    private String categoryName;

}

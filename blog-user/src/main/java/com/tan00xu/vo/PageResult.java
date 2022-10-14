package com.tan00xu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 分页结果返回类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/13 10:46:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Builder
@Schema(description = "分页对象")
public class PageResult<T> {

    /**
     * 分页列表
     */
    @Schema(name = "recordList", description = "分页列表", required = true, type = "List<T>")
    private List<T> recordList;

    /**
     * 总数
     */
    @Schema(name = "count", description = "总数", required = true, type = "Integer")
    private Integer count;

}

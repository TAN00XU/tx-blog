package com.tan00xu.controller;

import com.tan00xu.dto.CategoryDTO;
import com.tan00xu.service.CategoryService;
import com.tan00xu.vo.PageResult;
import com.tan00xu.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章分类控制器类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/14 23:03:45
 */
@Tag(name = "分类模块")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 查看分类列表
     *
     * @return {@link Result}<{@link PageResult}<{@link CategoryDTO}>>
     */
    @Operation(summary = "查看分类列表")
    @GetMapping("/web/categories")
    public Result<PageResult<CategoryDTO>> listCategories() {
        return Result.ok(categoryService.listCategories());
    }

}

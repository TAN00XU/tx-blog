package com.tan00xu.controller;

import com.tan00xu.dto.CategoryBackDTO;
import com.tan00xu.dto.CategoryDTO;
import com.tan00xu.dto.CategoryOptionDTO;
import com.tan00xu.service.CategoryService;
import com.tan00xu.vo.CategoryVO;
import com.tan00xu.vo.ConditionVO;
import com.tan00xu.vo.PageResult;
import com.tan00xu.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/categories")
    public Result<PageResult<CategoryDTO>> listCategories() {
        return Result.ok(categoryService.listCategories());
    }


    /**
     * 搜索文章分类 后台
     *
     * @param condition 条件
     * @return {@link Result}<{@link List}<{@link CategoryOptionDTO}>>
     */
    @Operation(summary = "搜索文章分类")
    @GetMapping("/admin/categories/search")
    public Result<List<CategoryOptionDTO>> listCategoriesBySearch(ConditionVO condition) {
        return Result.ok(categoryService.listCategoriesBySearch(condition));
    }

    /**
     * 查看分类列表（包括搜索） 后台
     *
     * @param condition 条件
     * @return {@link Result}<{@link PageResult}<{@link CategoryBackDTO}>>
     */
    @Operation(summary = "查看后台分类列表")
    @GetMapping("/admin/categories")
    public Result<PageResult<CategoryBackDTO>> listBackCategories(ConditionVO condition) {
        return Result.ok(categoryService.listBackCategories(condition));
    }


    /**
     * 删除分类 后台
     *
     * @param categoryIdList 类别id列表
     * @return {@link Result}<{@link ?}>
     */
    @Operation(summary = "删除分类")
    @DeleteMapping("/admin/categories")
    public Result<?> deleteCategories(@RequestBody List<Integer> categoryIdList) {
        categoryService.deleteCategory(categoryIdList);
        return Result.ok();
    }


    /**
     * 添加或修改分类 后台
     *
     * @param categoryVO 类别VO
     * @return {@link Result}<{@link ?}>
     */
    @Operation(summary = "添加或修改分类")
    @PostMapping("/admin/categories")
    public Result<?> saveOrUpdateCategory(@Validated @RequestBody CategoryVO categoryVO) {
        categoryService.saveOrUpdateCategory(categoryVO);
        return Result.ok();
    }

}

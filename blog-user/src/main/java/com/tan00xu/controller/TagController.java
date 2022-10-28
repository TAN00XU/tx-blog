package com.tan00xu.controller;

import com.tan00xu.dto.TagBackDTO;
import com.tan00xu.dto.TagDTO;
import com.tan00xu.service.TagService;
import com.tan00xu.vo.ConditionVO;
import com.tan00xu.vo.PageResult;
import com.tan00xu.vo.Result;
import com.tan00xu.vo.TagVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签控制器类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/15 11:05:03
 */
@Tag(name = "标签模块")
@RestController
public class TagController {


    @Autowired
    private TagService tagService;

    /**
     * 查询标签列表
     *
     * @return {@link Result<TagDTO>} 标签列表
     */
    @Operation(summary = "查询标签列表")
    @GetMapping("/tags")
    public Result<PageResult<TagDTO>> listTags() {
        return Result.ok(tagService.listTags());
    }


    /**
     * 搜索文章标签 后台
     *
     * @param condition 条件
     * @return {@link Result}<{@link List}<{@link TagDTO}>>
     */
    @Operation(summary = "搜索文章标签")
    @GetMapping("/admin/tags/search")
    public Result<List<TagDTO>> listTagsBySearch(ConditionVO condition) {
        return Result.ok(tagService.listTagsBySearch(condition));
    }


    /**
     * 查询标签列表 后台
     *
     * @param condition 条件
     * @return {@link Result}<{@link PageResult}<{@link TagBackDTO}>>
     */
    @Operation(summary = "查询后台标签列表")
    @GetMapping("/admin/tags")
    public Result<PageResult<TagBackDTO>> listTagBackDTO(ConditionVO condition) {
        return Result.ok(tagService.listTagBackDTO(condition));
    }


    /**
     * 删除标签 后台
     *
     * @param tagIdList 标签id列表
     * @return {@link Result}<{@link ?}>
     */
    @Operation(summary = "删除标签")
    @DeleteMapping("/admin/tags")
    public Result<?> deleteTags(@RequestBody List<Integer> tagIdList) {
        tagService.deleteTag(tagIdList);
        return Result.ok();
    }

    /**
     * 添加或修改标签 后台
     *
     * @param tagVO 标签VO
     * @return {@link Result}<{@link ?}>
     */
    @Operation(summary = "添加或修改标签")
    @PostMapping("/admin/tags")
    public Result<?> saveOrUpdateTag(@Validated @RequestBody TagVO tagVO) {
        tagService.saveOrUpdateTag(tagVO);
        return Result.ok();
    }
}

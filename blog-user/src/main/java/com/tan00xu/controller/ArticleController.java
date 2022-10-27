package com.tan00xu.controller;

import com.tan00xu.dto.ArchiveDTO;
import com.tan00xu.dto.ArticleBackDTO;
import com.tan00xu.dto.ArticleDTO;
import com.tan00xu.dto.ArticleHomeDTO;
import com.tan00xu.enums.FilePathEnum;
import com.tan00xu.service.ArticleService;
import com.tan00xu.strategy.context.UploadStrategyContext;
import com.tan00xu.util.CmdOutputInformationUtils;
import com.tan00xu.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文章控制器类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/11 14:14:38
 */
@Tag(name = "文章模块")
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    /**
     * 查看首页文章
     *
     * @return {@link Result}<{@link List}<{@link ArticleHomeDTO}>>
     */
    @Operation(summary = "查看首页文章")
    @GetMapping("/web/articles")
    public Result<List<ArticleHomeDTO>> listArticles() {
        return Result.ok(articleService.listArticles());
    }


    /**
     * 查看文章归档
     *
     * @return {@link Result}<{@link PageResult}<{@link ArchiveDTO}>>
     */
    @Operation(summary = "查看文章归档")
    @GetMapping("/web/articles/archives")
    public Result<PageResult<ArchiveDTO>> listArchives() {
        return Result.ok(articleService.listArchives());
    }


    /**
     * 通过id获取文章
     *
     * @param articleId 文章id
     * @return {@link Result}<{@link ArticleDTO}>
     */
    @Operation(summary = "根据id查看文章")
    @Parameter(name = "articleId", description = "文章id", required = true)
    @GetMapping("/web/articles/{articleId}")
    public Result<ArticleDTO> getArticleById(@PathVariable("articleId") Integer articleId) {
        return Result.ok(articleService.getArticleById(articleId));
    }

    /**
     * 点赞文章
     *
     * @param articleId 文章id
     * @return {@link Result<>}
     */
    @Operation(summary = "点赞文章")
    @Parameter(name = "articleId", description = "文章id", required = true)
    @PostMapping("/articles/{articleId}/like")
    public Result<?> saveArticleLike(@PathVariable("articleId") Integer articleId) {
        articleService.saveArticleLike(articleId);
        return Result.ok();
    }

    /**
     * 上传文章图片 后台
     *
     * @param file 文件
     * @return {@link Result<String>} 文章图片地址
     */
    @Operation(summary = "上传文章图片")
    @Parameter(name = "file", description = "文章图片", required = true)
    @PostMapping("/admin/articles/images")
    public Result<String> saveArticleImages(MultipartFile file) {
        CmdOutputInformationUtils.error("\n\n\n文件上传收到\n\n\n");
        return Result.ok(
                uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.ARTICLE.getPath())
        );
    }


    /**
     * 添加或修改文章 后台
     *
     * @param articleVO 文章VO
     * @return {@link Result}<{@link ?}>
     */
    @Operation(summary = "添加或修改文章")
    @PostMapping("/admin/articles")
    public Result<?> saveOrUpdateArticle(@Validated @RequestBody ArticleVO articleVO) {
        articleService.saveOrUpdateArticle(articleVO);
        return Result.ok();
    }


    /**
     * 查询文章列表 后台
     *
     * @param conditionVO 条件VO
     * @return 文章列表 {@link Result}<{@link PageResult}<{@link ArticleBackDTO}>>
     */
    @Operation(summary = "后台查看文章列表")
    @GetMapping("/admin/articles")
    public Result<PageResult<ArticleBackDTO>> listArticleBack(ConditionVO conditionVO) {
        CmdOutputInformationUtils.error("查询文章列表 后台=>" + conditionVO);
        return Result.ok(articleService.listArticleBack(conditionVO));
    }

    /**
     * 根据id查询文章 后台
     *
     * @param articleId 文章id
     * @return 文章 {@link Result}<{@link ArticleVO}>
     */
    @Operation(summary = "根据id查询后台文章")
    @Parameter(name = "articleId", description = "文章id", required = true)
    @GetMapping("/admin/articles/{articleId}")
    public Result<ArticleVO> getArticleBackById(@PathVariable("articleId") Integer articleId) {
        return Result.ok(articleService.getArticleBackById(articleId));
    }

    /**
     * 逻辑删除或恢复文章 后台
     *
     * @param logicDeleteVO 逻辑删除VO
     * @return {@link Result}<{@link ?}>
     */
    @Operation(summary = "恢复或删除文章")
    @PutMapping("/admin/articles")
    public Result<?> updateArticleDelete(@Validated @RequestBody LogicDeleteVO logicDeleteVO) {
        articleService.updateArticleDelete(logicDeleteVO);
        return Result.ok();
    }

    /**
     * 物理删除文章 后台
     *
     * @param articleIdList 文章id列表
     * @return {@link Result}<{@link ?}>
     */
    @Operation(summary = "物理删除文章")
    @DeleteMapping("/admin/articles")
    public Result<?> deleteArticles(@RequestBody List<Integer> articleIdList) {
        articleService.deleteArticles(articleIdList);
        return Result.ok();
    }


}

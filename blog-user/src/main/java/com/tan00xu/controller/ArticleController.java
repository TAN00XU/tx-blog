package com.tan00xu.controller;

import com.tan00xu.dto.ArticleHomeDTO;
import com.tan00xu.service.ArticleService;
import com.tan00xu.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 查看首页文章
     *
     * @return {@link Result<ArticleHomeDTO>} 首页文章列表
     */
    @Operation(summary = "查看首页文章")
    @GetMapping("/articles")
    public Result<List<ArticleHomeDTO>> listArticles() {
        return Result.ok(articleService.listArticles());
    }
}

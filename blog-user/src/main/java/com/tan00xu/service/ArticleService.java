package com.tan00xu.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tan00xu.dto.ArchiveDTO;
import com.tan00xu.dto.ArticleDTO;
import com.tan00xu.dto.ArticleHomeDTO;
import com.tan00xu.entity.Article;
import com.tan00xu.vo.PageResult;

import java.util.List;

/**
 * 文章服务类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/11 14:29:51
 */
public interface ArticleService extends IService<Article> {


    /**
     * 查询首页文章
     *
     * @return 文章列表
     */
    List<ArticleHomeDTO> listArticles();

    /**
     * 查询文章归档
     *
     * @return 文章归档
     */
    PageResult<ArchiveDTO> listArchives();

    /**
     * 根据id查看文章
     *
     * @param articleId 文章id
     * @return {@link ArticleDTO} 文章信息
     */
    ArticleDTO getArticleById(Integer articleId);

    /**
     * 点赞文章
     *
     * @param articleId 文章id
     */
    void saveArticleLike(Integer articleId);
}

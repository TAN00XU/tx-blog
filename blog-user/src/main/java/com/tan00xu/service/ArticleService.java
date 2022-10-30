package com.tan00xu.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tan00xu.dto.*;
import com.tan00xu.entity.Article;
import com.tan00xu.vo.*;

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
     * 搜索文章
     *
     * @param condition 条件
     * @return 文章列表
     */
    List<ArticleSearchDTO> listArticlesBySearch(ConditionVO condition);

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

    /**
     * 添加或修改文章 后台
     *
     * @param articleVO 文章VO
     */
    void saveOrUpdateArticle(ArticleVO articleVO);


    /**
     * 查询文章列表 后台
     *
     * @param condition 条件
     * @return {@link PageResult}<{@link ArticleBackDTO}>
     */
    PageResult<ArticleBackDTO> listArticleBack(ConditionVO condition);

    /**
     * 根据id查询文章 后台
     *
     * @param articleId 文章id
     * @return 文章 {@link ArticleVO}
     */
    ArticleVO getArticleBackById(Integer articleId);


    /**
     * 更新文章置顶状态 后台
     *
     * @param articleTopVO 文章置顶信息
     */
    void updateArticleTop(ArticleTopVO articleTopVO);


    /**
     * 逻辑删除或恢复文章 后台
     *
     * @param logicDeleteVO 逻辑删除对象
     */
    void updateArticleDelete(LogicDeleteVO logicDeleteVO);


    /**
     * 物理删除文章 后台
     *
     * @param articleIdList 文章id集合
     */
    void deleteArticles(List<Integer> articleIdList);

}

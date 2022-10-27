package com.tan00xu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tan00xu.dto.ArticleBackDTO;
import com.tan00xu.dto.ArticleDTO;
import com.tan00xu.dto.ArticleHomeDTO;
import com.tan00xu.dto.ArticleRecommendDTO;
import com.tan00xu.entity.Article;
import com.tan00xu.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 文章Dao类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/11 14:35:40
 */
@Mapper
public interface ArticleDao extends BaseMapper<Article> {

    /**
     * 查询首页文章
     *
     * @param current 页码
     * @param size    大小
     * @return 文章列表
     */
    List<ArticleHomeDTO> listArticles(@Param("current") Long current, @Param("size") Long size);

    /**
     * 查询文章总量 后台
     *
     * @param condition 条件
     * @return 文章总量
     */
    Integer countArticleBack(@Param("condition") ConditionVO condition);


    /**
     * 查询文章列表 后台
     *
     * @param current   当前
     * @param size      条数
     * @param condition 条件
     * @return {@link List}<{@link ArticleBackDTO}>
     */
    List<ArticleBackDTO> listArticleBack(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVO condition);


    /**
     * 根据id查询文章
     *
     * @param articleId 文章id
     * @return 文章信息
     */
    ArticleDTO getArticleById(@Param("articleId") Integer articleId);

    /**
     * 查询文章的推荐文章
     *
     * @param articleId 文章id
     * @return 文章列表
     */
    List<ArticleRecommendDTO> listRecommendArticles(@Param("articleId") Integer articleId);


}

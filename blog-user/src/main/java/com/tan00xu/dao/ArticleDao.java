package com.tan00xu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tan00xu.dto.ArticleHomeDTO;
import com.tan00xu.entity.Article;
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
}

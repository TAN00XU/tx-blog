package com.tan00xu.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tan00xu.dto.ArticleHomeDTO;
import com.tan00xu.entity.Article;

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

}
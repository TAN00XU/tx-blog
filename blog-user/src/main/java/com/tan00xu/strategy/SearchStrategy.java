package com.tan00xu.strategy;

import com.tan00xu.dto.ArticleSearchDTO;

import java.util.List;


/**
 * 搜索策略类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/29 17:37:22
 */
public interface SearchStrategy {


    /**
     * 搜索文章
     *
     * @param keywords 关键字
     * @return {@link List}<{@link ArticleSearchDTO}>
     */
    List<ArticleSearchDTO> searchArticle(String keywords);

}

package com.tan00xu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tan00xu.dao.ArticleDao;
import com.tan00xu.dto.ArticleHomeDTO;
import com.tan00xu.entity.Article;
import com.tan00xu.service.ArticleService;
import com.tan00xu.util.PagingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 饮梦 TAN00XU
 * @date 2022/10/11 14:32
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public List<ArticleHomeDTO> listArticles() {
        return articleDao.listArticles(PagingUtils.getLimitCurrent(), PagingUtils.getSize());
    }
}

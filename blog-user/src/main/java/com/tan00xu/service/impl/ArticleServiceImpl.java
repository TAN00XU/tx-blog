package com.tan00xu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tan00xu.dao.ArticleDao;
import com.tan00xu.dto.ArchiveDTO;
import com.tan00xu.dto.ArticleHomeDTO;
import com.tan00xu.entity.Article;
import com.tan00xu.service.ArticleService;
import com.tan00xu.util.BeanCopyUtils;
import com.tan00xu.util.PagingUtils;
import com.tan00xu.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.tan00xu.constant.CommonConst.FALSE;
import static com.tan00xu.enums.TalkStatusEnum.PUBLIC;

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

    @Override
    public PageResult<ArchiveDTO> listArchives() {
        Page<Article> page = new Page<>(PagingUtils.getCurrent(), PagingUtils.getSize());
        // 获取分页数据
        Page<Article> articlePage = articleDao.selectPage(
                page,
                new LambdaQueryWrapper<Article>()
                        .select(Article::getId, Article::getArticleTitle, Article::getCreateTime)
                        .orderByDesc(Article::getCreateTime)
                        .eq(Article::getIsDelete, FALSE)
                        .eq(Article::getStatus, PUBLIC.getStatus()));
        List<ArchiveDTO> archiveDTOList = BeanCopyUtils.copyList(articlePage.getRecords(), ArchiveDTO.class);
        return new PageResult<>(archiveDTOList, (int) articlePage.getTotal());
    }
}

package com.tan00xu.service.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tan00xu.dao.ArticleDao;
import com.tan00xu.dto.*;
import com.tan00xu.entity.Article;
import com.tan00xu.exception.BizException;
import com.tan00xu.service.ArticleService;
import com.tan00xu.service.RedisService;
import com.tan00xu.util.BeanCopyUtils;
import com.tan00xu.util.CommonUtils;
import com.tan00xu.util.PagingUtils;
import com.tan00xu.util.UserUtils;
import com.tan00xu.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.tan00xu.constant.CommonConst.ARTICLE_SET;
import static com.tan00xu.constant.CommonConst.FALSE;
import static com.tan00xu.constant.RedisPrefixConst.*;
import static com.tan00xu.enums.TalkStatusEnum.PUBLIC;

/**
 * @author 饮梦 TAN00XU
 * @date 2022/10/11 14:32
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private HttpSession session;

    @Autowired
    private RedisService redisService;

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

    @Override
    public ArticleDTO getArticleById(Integer articleId) {
        // 查询推荐文章
        CompletableFuture<List<ArticleRecommendDTO>> recommendArticleList
                = CompletableFuture.supplyAsync(() -> articleDao.listRecommendArticles(articleId));
        // 查询最新文章
        CompletableFuture<List<ArticleRecommendDTO>> newestArticleList
                = CompletableFuture.supplyAsync(() -> {
                    List<Article> articleList = articleDao.selectList(
                            new LambdaQueryWrapper<Article>()
                                    .select(Article::getId, Article::getArticleTitle, Article::getArticleCover, Article::getCreateTime)
                                    .eq(Article::getIsDelete, FALSE)
                                    .eq(Article::getStatus, PUBLIC.getStatus())
                                    .orderByDesc(Article::getId)
                                    .last("limit 5")
                    );
                    return BeanCopyUtils.copyList(articleList, ArticleRecommendDTO.class);
                }
        );
        // 查询id对应文章
        ArticleDTO article = articleDao.getArticleById(articleId);
        //空 抛出异常
        if (Objects.isNull(article)) {
            throw new BizException("文章不存在");
        }
        // 更新文章浏览量
        updateArticleViewsCount(articleId);
        // 查询上一篇下一篇文章
        Article lastArticle = articleDao.selectOne(
                new LambdaQueryWrapper<Article>()
                        .select(Article::getId, Article::getArticleTitle, Article::getArticleCover)
                        .eq(Article::getIsDelete, FALSE)
                        .eq(Article::getStatus, PUBLIC.getStatus())
                        .lt(Article::getId, articleId)
                        .orderByDesc(Article::getId)
                        .last("limit 1"));
        Article nextArticle = articleDao.selectOne(
                new LambdaQueryWrapper<Article>()
                        .select(Article::getId, Article::getArticleTitle, Article::getArticleCover)
                        .eq(Article::getIsDelete, FALSE)
                        .eq(Article::getStatus, PUBLIC.getStatus())
                        .gt(Article::getId, articleId)
                        .orderByAsc(Article::getId)
                        .last("limit 1"));
        article.setLastArticle(BeanCopyUtils.copyObject(lastArticle, ArticlePaginationDTO.class));
        article.setNextArticle(BeanCopyUtils.copyObject(nextArticle, ArticlePaginationDTO.class));
        // 浏览量
        Double score = redisService.zScore(ARTICLE_VIEWS_COUNT, articleId);
        if (Objects.nonNull(score)) {
            article.setViewsCount(score.intValue());
        }
        //点赞量
        article.setLikeCount((Integer) redisService.hGet(ARTICLE_LIKE_COUNT, articleId.toString()));
        // 封装文章信息
        try {
            article.setRecommendArticleList(recommendArticleList.get());
            article.setNewestArticleList(newestArticleList.get());
        } catch (Exception e) {
            log.error(StrUtil.format("堆栈信息:{}", ExceptionUtil.stacktraceToString(e)));
        }
        return article;
    }

    /**
     * 更新文章浏览量
     *
     * @param articleId 文章id
     */
    public void updateArticleViewsCount(Integer articleId) {
        // 判断是否第一次访问，增加浏览量
        Set<Integer> articleSet = CommonUtils.castSet(
                Optional.ofNullable(session.getAttribute(ARTICLE_SET))
                        .orElseGet(HashSet::new),
                Integer.class
        );
        if (!articleSet.contains(articleId)) {
            articleSet.add(articleId);
            session.setAttribute(ARTICLE_SET, articleSet);
            // 浏览量+1
            redisService.zIncr(ARTICLE_VIEWS_COUNT, articleId, 1D);
        }
    }

    @Override
    public void saveArticleLike(Integer articleId) {
        // 判断是否点赞
        String articleLikeKey = ARTICLE_USER_LIKE + UserUtils.getLoginUser().getUserInfoId();
        if (redisService.sIsMember(articleLikeKey, articleId)) {
            // 点过赞则删除文章id
            redisService.sRemove(articleLikeKey, articleId);
            // 文章点赞量-1
            redisService.hDecr(ARTICLE_LIKE_COUNT, articleId.toString(), 1L);
        } else {
            // 未点赞则增加文章id
            redisService.sAdd(articleLikeKey, articleId);
            // 文章点赞量+1
            redisService.hIncr(ARTICLE_LIKE_COUNT, articleId.toString(), 1L);
        }
    }
}

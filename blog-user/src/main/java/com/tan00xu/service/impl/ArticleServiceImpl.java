package com.tan00xu.service.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tan00xu.dao.ArticleDao;
import com.tan00xu.dao.ArticleTagDao;
import com.tan00xu.dao.CategoryDao;
import com.tan00xu.dao.TagDao;
import com.tan00xu.dto.*;
import com.tan00xu.entity.Article;
import com.tan00xu.entity.ArticleTag;
import com.tan00xu.entity.Category;
import com.tan00xu.entity.Tag;
import com.tan00xu.exception.BizException;
import com.tan00xu.service.*;
import com.tan00xu.strategy.context.SearchStrategyContext;
import com.tan00xu.util.BeanCopyUtils;
import com.tan00xu.util.CommonUtils;
import com.tan00xu.util.PagingUtils;
import com.tan00xu.util.UserUtils;
import com.tan00xu.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.tan00xu.constant.CommonConst.ARTICLE_SET;
import static com.tan00xu.constant.CommonConst.FALSE;
import static com.tan00xu.constant.RedisPrefixConst.*;
import static com.tan00xu.enums.ArticleStatusEnum.DRAFT;
import static com.tan00xu.enums.TalkStatusEnum.PUBLIC;

/**
 * 文章服务实现类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/23 13:11:05
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ArticleTagDao articleTagDao;
    @Autowired
    private TagDao tagDao;

    @Autowired
    private HttpSession session;

    @Autowired
    private RedisService redisService;

    @Autowired
    private BlogInfoService blogInfoService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private SearchStrategyContext searchStrategyContext;

    @Override
    public List<ArticleHomeDTO> listArticles() {
        return articleDao.listArticles(PagingUtils.getLimitCurrent(), PagingUtils.getSize());
    }

    @Override
    public List<ArticleSearchDTO> listArticlesBySearch(ConditionVO condition) {
        return searchStrategyContext.executeSearchStrategy(condition.getKeywords());
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


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateArticle(ArticleVO articleVO) {
        // 查询博客配置信息
        CompletableFuture<WebsiteConfigVO> webConfig = CompletableFuture.supplyAsync(() -> blogInfoService.getWebsiteConfig());
        // 保存文章分类
        Category category = saveArticleCategory(articleVO);
        // 保存或修改文章
        Article article = BeanCopyUtils.copyObject(articleVO, Article.class);
        if (Objects.nonNull(category)) {
            article.setCategoryId(category.getId());
        }
        // 若无封面则设定默认文章封面
        if (StrUtil.isBlank(article.getArticleCover())) {
            try {
                article.setArticleCover(webConfig.get().getArticleCover());
            } catch (Exception e) {
                throw new BizException("设定默认文章封面失败");
            }
        }
        article.setUserId(UserUtils.getLoginUser().getUserInfoId());
        this.saveOrUpdate(article);
        // 保存文章标签
        saveArticleTag(articleVO, article.getId());
    }

    @Override
    public PageResult<ArticleBackDTO> listArticleBack(ConditionVO condition) {
        // 查询文章总量
        Integer count = articleDao.countArticleBack(condition);
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询后台文章
        List<ArticleBackDTO> articleBackDTOList = articleDao.listArticleBack(PagingUtils.getLimitCurrent(), PagingUtils.getSize(), condition);
        // 查询文章点赞量和浏览量
        Map<Object, Double> viewsCountMap = redisService.zAllScore(ARTICLE_VIEWS_COUNT);
        Map<String, Object> likeCountMap = redisService.hGetAll(ARTICLE_LIKE_COUNT);
        // 封装点赞量和浏览量
        articleBackDTOList.forEach(item -> {
            Double viewsCount = viewsCountMap.get(item.getId());
            if (Objects.nonNull(viewsCount)) {
                item.setViewsCount(viewsCount.intValue());
            }
            item.setLikeCount((Integer) likeCountMap.get(item.getId().toString()));
        });
        return new PageResult<>(articleBackDTOList, count);
    }

    @Override
    public ArticleVO getArticleBackById(Integer articleId) {
        // 查询文章信息
        Article article = articleDao.selectById(articleId);
        // 查询文章分类
        Category category = categoryDao.selectById(article.getCategoryId());
        String categoryName = null;
        if (Objects.nonNull(category)) {
            categoryName = category.getCategoryName();
        }
        // 查询文章标签
        List<String> tagNameList = tagDao.listTagNameByArticleId(articleId);
        // 封装数据
        ArticleVO articleVO = BeanCopyUtils.copyObject(article, ArticleVO.class);
        articleVO.setCategoryName(categoryName);
        articleVO.setTagNameList(tagNameList);
        return articleVO;
    }

    @Override
    public void updateArticleTop(ArticleTopVO articleTopVO) {
        // 修改文章置顶状态
        Article article = Article.builder()
                .id(articleTopVO.getId())
                .isTop(articleTopVO.getIsTop())
                .build();
        articleDao.updateById(article);
    }

    @Override
    public void updateArticleDelete(LogicDeleteVO logicDeleteVO) {
        // 修改文章逻辑删除状态
        List<Article> articleList = logicDeleteVO.getIdList().stream()
                .map(id -> Article.builder()
                        .id(id)
                        .isTop(FALSE)
                        .isDelete(logicDeleteVO.getIsDelete())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(articleList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteArticles(List<Integer> articleIdList) {
        // 删除文章标签关联
        articleTagDao.delete(
                new LambdaQueryWrapper<ArticleTag>()
                        .in(ArticleTag::getArticleId, articleIdList)
        );
        // 删除文章
        articleDao.deleteBatchIds(articleIdList);
    }

    /**
     * 保存文章分类
     *
     * @param articleVO 文章VO
     * @return 文章分类 {@link Category}
     */
    private Category saveArticleCategory(ArticleVO articleVO) {
        // 判断分类是否存在
        Category category = categoryDao.selectOne(
                new LambdaQueryWrapper<Category>()
                        .eq(Category::getCategoryName, articleVO.getCategoryName())
        );
        if (Objects.isNull(category) && !articleVO.getStatus().equals(DRAFT.getStatus())) {
            category = Category.builder()
                    .categoryName(articleVO.getCategoryName())
                    .build();
            categoryDao.insert(category);
        }
        return category;
    }

    /**
     * 保存文章标签
     *
     * @param articleVO 文章信息
     */
    private void saveArticleTag(ArticleVO articleVO, Integer articleId) {
        // 编辑文章则删除文章所有标签
        if (Objects.nonNull(articleVO.getId())) {
            articleTagDao.delete(
                    new LambdaQueryWrapper<ArticleTag>()
                            .eq(ArticleTag::getArticleId, articleVO.getId())
            );
        }
        // 添加文章标签
        List<String> tagNameList = articleVO.getTagNameList();
        if (CollectionUtils.isNotEmpty(tagNameList)) {
            // 查询已存在的标签
            List<Tag> existTagList = tagService.list(
                    new LambdaQueryWrapper<Tag>()
                            .in(Tag::getTagName, tagNameList)
            );
            List<String> existTagNameList = existTagList.stream().map(Tag::getTagName).collect(Collectors.toList());
            List<Integer> existTagIdList = existTagList.stream().map(Tag::getId).collect(Collectors.toList());
            // 对比新增不存在的标签
            tagNameList.removeAll(existTagNameList);
            // 集合中还有标签则添加
            if (CollectionUtils.isNotEmpty(tagNameList)) {
                List<Tag> tagList = tagNameList.stream()
                        .map(item -> Tag.builder().tagName(item).build())
                        .collect(Collectors.toList());
                tagService.saveBatch(tagList);
                List<Integer> tagIdList = tagList.stream().map(Tag::getId).collect(Collectors.toList());
                existTagIdList.addAll(tagIdList);
            }
            // 提取标签id绑定文章
            List<ArticleTag> articleTagList = existTagIdList.stream()
                    .map(item -> ArticleTag.builder()
                            .articleId(articleId)
                            .tagId(item)
                            .build())
                    .collect(Collectors.toList());
            articleTagService.saveBatch(articleTagList);
        }
    }


}

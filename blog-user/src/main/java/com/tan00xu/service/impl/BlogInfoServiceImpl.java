package com.tan00xu.service.impl;


import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tan00xu.dao.ArticleDao;
import com.tan00xu.dao.CategoryDao;
import com.tan00xu.dao.TagDao;
import com.tan00xu.dao.WebsiteConfigDao;
import com.tan00xu.dto.BlogHomeInfoDTO;
import com.tan00xu.entity.Article;
import com.tan00xu.entity.WebsiteConfig;
import com.tan00xu.service.BlogInfoService;
import com.tan00xu.service.PageService;
import com.tan00xu.service.RedisService;
import com.tan00xu.util.IpUtils;
import com.tan00xu.vo.PageVO;
import com.tan00xu.vo.WebsiteConfigVO;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.tan00xu.constant.CommonConst.*;
import static com.tan00xu.constant.RedisPrefixConst.*;
import static com.tan00xu.enums.ArticleStatusEnum.PUBLIC;


/**
 * 博客信息服务实现类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/09/29 11:57:55
 */
@Service
public class BlogInfoServiceImpl implements BlogInfoService {
    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private RedisService redisService;

    @Autowired
    private PageService pageService;

    @Autowired
    private WebsiteConfigDao websiteConfigDao;
    @Resource
    private HttpServletRequest request;

    @Override
    public BlogHomeInfoDTO getBlogHomeInfo() {
        // 查询文章数量
        Long articleCount = articleDao.selectCount(
                new LambdaQueryWrapper<Article>()
                        .eq(Article::getStatus, PUBLIC.getStatus())
                        .eq(Article::getIsDelete, FALSE)
        );
        // 查询分类数量
        Long categoryCount = categoryDao.selectCount(null);
        // 查询标签数量
        Long tagCount = tagDao.selectCount(null);
        // 查询访问量
        Object count = redisService.get(BLOG_VIEWS_COUNT);
        String viewsCount = Optional.ofNullable(count).orElse(0).toString();
        // 查询网站配置
        WebsiteConfigVO websiteConfig = this.getWebsiteConfig();
        // 查询页面图片
        List<PageVO> pageVOList = pageService.listPages();
        // 封装数据
        return BlogHomeInfoDTO.builder()
                .articleCount(Math.toIntExact(articleCount))
                .categoryCount(Math.toIntExact(categoryCount))
                .tagCount(Math.toIntExact(tagCount))
                .viewsCount(viewsCount)
                .websiteConfig(websiteConfig)
                .pageList(pageVOList)
                .build();
    }

    @Override
    public WebsiteConfigVO getWebsiteConfig() {
        WebsiteConfigVO websiteConfigVO;
        // 获取缓存数据
        Object websiteConfig = redisService.get(WEBSITE_CONFIG);
        if (Objects.nonNull(websiteConfig)) {
            websiteConfigVO = JSON.parseObject(websiteConfig.toString(), WebsiteConfigVO.class);
        } else {
            // 从数据库中加载
            String config = websiteConfigDao.selectById(DEFAULT_CONFIG_ID).getConfig();
            websiteConfigVO = JSON.parseObject(config, WebsiteConfigVO.class);
            redisService.set(WEBSITE_CONFIG, config);
        }
        return websiteConfigVO;
    }

    @Override
    public void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO) {
        // 修改网站配置
        WebsiteConfig websiteConfig = WebsiteConfig.builder()
                .id(1)
                .config(com.alibaba.fastjson2.JSON.toJSONString(websiteConfigVO))
                .build();
        websiteConfigDao.updateById(websiteConfig);
        // 删除缓存
        redisService.del(WEBSITE_CONFIG);
    }

    @Override
    public void report() {
        // 获取ip
        String ipAddress = IpUtils.getIpAddress(request);
        // 获取访问设备
        UserAgent userAgent = IpUtils.getUserAgent(request);

        //获取浏览器对象
        Browser browser = userAgent.getBrowser();
        //获取浏览器对象
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        // 生成唯一用户标识 ip地址+浏览器名+操作系统名
        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
        //md5加密
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());
        // 判断是否访问
        if (!redisService.sIsMember(UNIQUE_VISITOR, md5)) {
            // 统计游客地域分布
            String ipSource = IpUtils.getIpSource(ipAddress);
            if (StringUtils.isNotBlank(ipSource)) {
                ipSource = getProvince(ipSource);
                redisService.hIncr(VISITOR_AREA, ipSource, 1L);
            } else {
                redisService.hIncr(VISITOR_AREA, UNKNOWN, 1L);
            }
            // 访问量+1
            redisService.incr(BLOG_VIEWS_COUNT, 1);
            // 保存唯一标识
            redisService.sAdd(UNIQUE_VISITOR, md5);
        }
    }

    /**
     * 获得省份名称
     *
     * @param name 名字
     * @return {@link String}
     */
    public String getProvince(String name) {
        String provinceName = "";
        if (StringUtils.isNotBlank(name)) {
            int index;
            if ((index = name.indexOf(PROVINCE)) > 0 ||
                    (index = name.indexOf(DISTRICT)) > 0 ||
                    (index = name.indexOf(CITY)) > 0) {
                provinceName = name.substring(0, index + 1);
            }
        }
        return provinceName;
    }

    @Override
    public String getAbout() {
        Object value = redisService.get(ABOUT);
        return Objects.nonNull(value) ? value.toString() : "";
    }
}

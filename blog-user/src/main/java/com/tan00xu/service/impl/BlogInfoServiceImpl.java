package com.tan00xu.service.impl;


import com.alibaba.fastjson2.JSON;
import com.tan00xu.dao.WebsiteConfigDao;
import com.tan00xu.service.BlogInfoService;
import com.tan00xu.service.RedisService;
import com.tan00xu.vo.Result;
import com.tan00xu.vo.WebsiteConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.tan00xu.constant.CommonConst.DEFAULT_CONFIG_ID;
import static com.tan00xu.constant.RedisPrefixConst.WEBSITE_CONFIG;


/**
 * 博客信息服务实现类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/09/29 11:57:55
 */
@Service
public class BlogInfoServiceImpl implements BlogInfoService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private WebsiteConfigDao websiteConfigDao;

    @Override
    public Result<WebsiteConfigVO> getWebsiteConfig() {
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
        return Result.ok(websiteConfigVO);
    }
}

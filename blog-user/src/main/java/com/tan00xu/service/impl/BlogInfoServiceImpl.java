package com.tan00xu.service.impl;


import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tan00xu.dao.WebsiteConfigDao;
import com.tan00xu.service.BlogInfoService;
import com.tan00xu.service.RedisService;
import com.tan00xu.util.IpUtils;
import com.tan00xu.vo.WebsiteConfigVO;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.tan00xu.constant.CommonConst.*;
import static com.tan00xu.constant.RedisPrefixConst.*;


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

    @Resource
    private HttpServletRequest request;

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
//                ipSource = ipSource.substring(0, 2)
//                        .replaceAll(PROVINCE, "")
//                        .replaceAll(CITY, "");
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
            int index = 0;
            if ((index = name.indexOf(PROVINCE)) > 0 ||
                    (index = name.indexOf(DISTRICT)) > 0 ||
                    (index = name.indexOf(CITY)) > 0) {
                provinceName = name.substring(0, index + 1);
            }
        }
        return provinceName;
    }


}

package com.tan00xu.service;


import com.tan00xu.dto.BlogHomeInfoDTO;
import com.tan00xu.vo.WebsiteConfigVO;

/**
 * 博客信息服务类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/09/29 09:23:05
 */
public interface BlogInfoService {


    /**
     * 获取博客首页信息
     *
     * @return 博客首页信息
     */
    BlogHomeInfoDTO getBlogHomeInfo();


    /**
     * 获取网站配置
     *
     * @return {@link WebsiteConfigVO} 网站配置
     */
    WebsiteConfigVO getWebsiteConfig();


    /**
     * 更新网站配置 后台
     *
     * @param websiteConfigVO 网站配置VO
     */
    void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO);

    /**
     * 上传访客信息
     *
     * @description 本数据直接存在redis中
     */
    void report();

    /**
     * 获取关于我内容
     *
     * @return 关于我内容
     * @description 本数据直接存在redis中
     */
    String getAbout();

}

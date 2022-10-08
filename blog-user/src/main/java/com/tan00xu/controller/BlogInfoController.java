package com.tan00xu.controller;

import com.tan00xu.service.BlogInfoService;
import com.tan00xu.vo.Result;
import com.tan00xu.vo.WebsiteConfigVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 博客信息控制器类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/09/30 14:41:34
 */
@Tag(name = "博客信息")
@RestController
@RequestMapping("/web")
public class BlogInfoController {
    @Autowired
    private BlogInfoService blogInfoService;


    /**
     * 获取网站的配置
     *
     * @return {@link Result<WebsiteConfigVO>} 网站配置
     */
    @Operation(summary = "获取网站配置信息")
    @GetMapping("/website/config")
    public Result<WebsiteConfigVO> getWebsiteConfig() {
        return blogInfoService.getWebsiteConfig();
    }

}


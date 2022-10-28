package com.tan00xu.controller;

import com.tan00xu.dto.BlogHomeInfoDTO;
import com.tan00xu.enums.FilePathEnum;
import com.tan00xu.service.BlogInfoService;
import com.tan00xu.strategy.context.UploadStrategyContext;
import com.tan00xu.vo.Result;
import com.tan00xu.vo.WebsiteConfigVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * 博客信息控制器类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/09/30 14:41:34
 */
@Tag(name = "博客信息")
@RestController
public class BlogInfoController {
    @Autowired
    private BlogInfoService blogInfoService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    /**
     * 获取博客首页信息
     *
     * @return {@link Result}<{@link BlogHomeInfoDTO}>
     */
    @Operation(summary = "查看博客信息")
    @GetMapping("/information")
    public Result<BlogHomeInfoDTO> getBlogHomeInfo() {
        return Result.ok(blogInfoService.getBlogHomeInfo());
    }

    /**
     * 获取网站的配置
     *
     * @return {@link Result<WebsiteConfigVO>} 网站配置
     */
    @Operation(summary = "获取网站配置信息")
    @GetMapping("/admin/website/config")
    public Result<WebsiteConfigVO> getWebsiteConfig() {
        return Result.ok(blogInfoService.getWebsiteConfig());
    }


    /**
     * 更新网站配置 后台
     *
     * @param websiteConfigVO 网站配置VO
     * @return {@link Result}<{@link ?}>
     */
    @Operation(summary = "更新网站配置")
    @PutMapping("/admin/website/config")
    public Result<?> updateWebsiteConfig(@Validated @RequestBody WebsiteConfigVO websiteConfigVO) {
        blogInfoService.updateWebsiteConfig(websiteConfigVO);
        return Result.ok();
    }

    /**
     * 上传博客配置图片
     *
     * @param file 文件
     * @return {@link Result<String>} 博客配置图片
     */
    @Operation(summary = "上传博客配置图片")
    @Parameter(name = "file", description = "图片", required = true)
    @PostMapping("/admin/config/images")
    public Result<String> savePhotoAlbumCover(MultipartFile file) {
        return Result.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.CONFIG.getPath()));
    }

    /**
     * 上传访客信息
     *
     * @return {@link Result}
     */
    @Operation(summary = "上传访客信息")
    @PostMapping("/report")
    public Result<?> report() {
        blogInfoService.report();
        return Result.ok();
    }


    /**
     * 查看关于我信息
     *
     * @return {@link Result<String>} 关于我信息
     */
    @Operation(summary = "查看关于我信息")
    @GetMapping("/web/about")
    public Result<String> getAbout() {
        return Result.ok(blogInfoService.getAbout());
    }


}


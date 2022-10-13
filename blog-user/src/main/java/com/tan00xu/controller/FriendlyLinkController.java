package com.tan00xu.controller;


import com.tan00xu.dto.FriendlyLinkDTO;
import com.tan00xu.service.FriendlyLinkService;
import com.tan00xu.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 友情链接控制器类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/13 13:49:40
 */
@Tag(name = "友链模块")
@RestController
public class FriendlyLinkController {
    @Autowired
    private FriendlyLinkService friendlyLinkService;

    /**
     * 查看友链列表
     *
     * @return {@link Result<FriendlyLinkDTO>} 友链列表
     */
    @Operation(summary = "查看友链列表")
    @GetMapping("/web/links")
    public Result<List<FriendlyLinkDTO>> listFriendLinks() {
        return Result.ok(friendlyLinkService.listFriendLinks());
    }

}


package com.tan00xu.controller;

import com.tan00xu.dto.TalkDTO;
import com.tan00xu.service.TalkService;
import com.tan00xu.vo.PageResult;
import com.tan00xu.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 说说控制器类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/13 09:32:21
 */
@Tag(name = "说说模块")
@RestController
public class TalkController {
    @Autowired
    private TalkService talkService;
 

    /**
     * 查看首页说说
     *
     * @return {@link Result<String>}
     */
    @Operation(summary = "查看首页说说")
    @GetMapping("/web/home/talks")
    public Result<List<String>> listHomeTalks() {
        return Result.ok(talkService.listHomeTalks());
    }

    /**
     * 查看说说列表
     *
     * @return {@link Result<TalkDTO>}
     */
    @Operation(summary = "查看说说列表")
    @GetMapping("/talks")
    public Result<PageResult<TalkDTO>> listTalks() {
        return Result.ok(talkService.listTalks());
    }


}

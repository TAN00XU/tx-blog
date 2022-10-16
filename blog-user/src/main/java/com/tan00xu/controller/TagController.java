package com.tan00xu.controller;

import com.tan00xu.dto.TagDTO;
import com.tan00xu.service.TagService;
import com.tan00xu.vo.PageResult;
import com.tan00xu.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 标签控制器类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/15 11:05:03
 */
@RestController
public class TagController {


    @Autowired
    private TagService tagService;

    /**
     * 查询标签列表
     *
     * @return {@link Result<TagDTO>} 标签列表
     */
    @Operation(summary = "查询标签列表")
    @GetMapping("/web/tags")
    public Result<PageResult<TagDTO>> listTags() {
        return Result.ok(tagService.listTags());
    }

}

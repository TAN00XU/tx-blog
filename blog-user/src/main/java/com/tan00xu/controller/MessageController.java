package com.tan00xu.controller;

import com.tan00xu.annotation.AccessLimit;
import com.tan00xu.dto.MessageDTO;
import com.tan00xu.service.MessageService;
import com.tan00xu.vo.MessageVO;
import com.tan00xu.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 留言控制器类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/07 18:39:35
 */
@Tag(name = "留言模块")
@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * 查看留言列表
     *
     * @return {@link Result<MessageDTO>} 留言列表
     */
    @Operation(summary = "查看留言列表")
    @GetMapping("/messages")
    public Result<List<MessageDTO>> listMessages() {
        return messageService.listMessages();
    }


    /**
     * 添加留言
     *
     * @param messageVO 留言信息
     * @return {@link Result<>}
     */
    @AccessLimit(seconds = 60, maxCount = 1)
    @Operation(summary = "添加留言")
    @PostMapping("/messages")
    public Result<?> saveMessage(@Validated @RequestBody MessageVO messageVO) {
        messageService.saveMessage(messageVO);
        return Result.ok();
    }
}

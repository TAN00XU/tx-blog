package com.tan00xu.controller;

import com.tan00xu.dto.MessageDTO;
import com.tan00xu.service.MessageService;
import com.tan00xu.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/web")
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

}

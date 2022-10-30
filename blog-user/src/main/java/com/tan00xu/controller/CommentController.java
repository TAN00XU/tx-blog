package com.tan00xu.controller;

import com.tan00xu.dto.CommentDTO;
import com.tan00xu.dto.ReplyDTO;
import com.tan00xu.service.CommentService;
import com.tan00xu.vo.CommentVO;
import com.tan00xu.vo.PageResult;
import com.tan00xu.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论控制器类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/17 21:47:38
 */
@Tag(name = "评论模块")
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;


    /**
     * 查询评论
     *
     * @param commentVO 评论信息
     * @return {@link Result<CommentDTO>}
     */
    @Operation(summary = "查询评论")
    @GetMapping("/comments")
    public Result<PageResult<CommentDTO>> listComments(CommentVO commentVO) {
        return Result.ok(commentService.listComments(commentVO));
    }


    /**
     * 查询评论下的回复
     *
     * @param commentId 评论id
     * @return {@link Result}<{@link List}<{@link ReplyDTO}>>
     */
    @Operation(summary = "查询评论下的回复")
    @Parameter(name = "commentId", description = "评论id", required = true)
    @GetMapping("/comments/{commentId}/replies")
    public Result<List<ReplyDTO>> listRepliesByCommentId(@PathVariable("commentId") Integer commentId) {
        return Result.ok(commentService.listRepliesByCommentId(commentId));
    }

    /**
     * 添加评论
     *
     * @param commentVO 评论信息
     * @return {@link Result<>}
     */
    @Operation(summary = "添加评论")
    @PostMapping("/comments")
    public Result<?> saveComment(@Validated @RequestBody CommentVO commentVO) {
        commentService.saveComment(commentVO);
        return Result.ok();
    }

}

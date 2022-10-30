package com.tan00xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tan00xu.dto.CommentDTO;
import com.tan00xu.dto.ReplyDTO;
import com.tan00xu.entity.Comment;
import com.tan00xu.vo.CommentVO;
import com.tan00xu.vo.PageResult;

import java.util.List;

/**
 * 评论服务类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/27 10:42:27
 */
public interface CommentService extends IService<Comment> {

    /**
     * 查看评论
     *
     * @param commentVO 评论信息
     * @return 评论列表
     */
    PageResult<CommentDTO> listComments(CommentVO commentVO);


    /**
     * 查看评论下的回复
     *
     * @param commentId 评论id
     * @return 回复列表
     */
    List<ReplyDTO> listRepliesByCommentId(Integer commentId);

    /**
     * 添加评论
     *
     * @param commentVO 评论对象
     */
    void saveComment(CommentVO commentVO);
}

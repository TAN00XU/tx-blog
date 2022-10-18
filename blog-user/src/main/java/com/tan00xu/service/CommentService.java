package com.tan00xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tan00xu.dto.CommentDTO;
import com.tan00xu.entity.Comment;
import com.tan00xu.vo.CommentVO;
import com.tan00xu.vo.PageResult;

/**
 * @author 饮梦 TAN00XU
 * @date 2022/10/17 22:00
 */
public interface CommentService extends IService<Comment> {

    /**
     * 查看评论
     *
     * @param commentVO 评论信息
     * @return 评论列表
     */
    PageResult<CommentDTO> listComments(CommentVO commentVO);
}

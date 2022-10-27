package com.tan00xu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tan00xu.dto.CommentCountDTO;
import com.tan00xu.dto.CommentDTO;
import com.tan00xu.dto.ReplyCountDTO;
import com.tan00xu.dto.ReplyDTO;
import com.tan00xu.entity.Comment;
import com.tan00xu.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 评论Dao类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/13 11:29:24
 */
@Mapper
public interface CommentDao extends BaseMapper<Comment> {


    /**
     * 根据评论主题id获取评论量
     *
     * @param topicIdList 说说id列表
     * @return 说说评论量 {@link List<CommentCountDTO>}
     */
    List<CommentCountDTO> listCommentCountByTopicIds(@Param("topicIdList") List<Integer> topicIdList);


    /**
     * 查看评论列表
     *
     * @param current   当前页码
     * @param size      大小
     * @param commentVO 评论信息
     * @return 评论集合
     */
    List<CommentDTO> listComments(@Param("current") Long current, @Param("size") Long size, @Param("commentVO") CommentVO commentVO);


    /**
     * 查看评论id集合下的回复
     *
     * @param commentIdList 评论id列表
     * @return 回复集合 {@link List}<{@link ReplyDTO}>
     */
    List<ReplyDTO> listReplies(@Param("commentIdList") List<Integer> commentIdList);

    /**
     * 根据评论id查询回复总量
     *
     * @param commentIdList 评论id集合
     * @return 回复数量
     */
    List<ReplyCountDTO> listReplyCountByCommentId(@Param("commentIdList") List<Integer> commentIdList);

}

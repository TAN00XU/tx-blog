package com.tan00xu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tan00xu.dto.CommentCountDTO;
import com.tan00xu.entity.Comment;
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
     * @return {@link List<CommentCountDTO>}说说评论量
     */
    List<CommentCountDTO> listCommentCountByTopicIds(@Param("topicIdList") List<Integer> topicIdList);


}

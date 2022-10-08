package com.tan00xu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tan00xu.entity.Message;
import org.apache.ibatis.annotations.Mapper;


/**
 * 留言Dao类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/07 18:44:27
 */
@Mapper
public interface MessageDao extends BaseMapper<Message> {

}

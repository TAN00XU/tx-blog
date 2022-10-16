package com.tan00xu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tan00xu.entity.Tag;
import org.apache.ibatis.annotations.Mapper;


/**
 * 标签Dao类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/15 11:07:59
 */
@Mapper
public interface TagDao extends BaseMapper<Tag> {


}

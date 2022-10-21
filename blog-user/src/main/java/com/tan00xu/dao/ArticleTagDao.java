package com.tan00xu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tan00xu.entity.ArticleTag;
import org.apache.ibatis.annotations.Mapper;


/**
 * 文章与标签对应Dao类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/21 16:24:04
 */
@Mapper
public interface ArticleTagDao extends BaseMapper<ArticleTag> {

}

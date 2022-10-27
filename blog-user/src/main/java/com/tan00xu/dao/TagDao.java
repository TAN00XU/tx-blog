package com.tan00xu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tan00xu.dto.TagBackDTO;
import com.tan00xu.entity.Tag;
import com.tan00xu.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 标签Dao类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/15 11:07:59
 */
@Mapper
public interface TagDao extends BaseMapper<Tag> {


    /**
     * 查询标签列表 后台
     *
     * @param current   页码
     * @param size      大小
     * @param condition 条件
     * @return {@link List}<{@link TagBackDTO}>
     */
    List<TagBackDTO> listTagBackDTO(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVO condition);

    /**
     * 根据文章id查询标签名
     *
     * @param articleId 文章id
     * @return {@link List<String>} 标签名列表
     */
    List<String> listTagNameByArticleId(Integer articleId);
}

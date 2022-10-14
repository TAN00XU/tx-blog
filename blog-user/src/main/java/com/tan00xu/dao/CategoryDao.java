package com.tan00xu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tan00xu.dto.CategoryDTO;
import com.tan00xu.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 文章分类Dao类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/14 15:19:01
 */
@Mapper
public interface CategoryDao extends BaseMapper<Category> {

    /**
     * 查询分类和对应文章数量
     *
     * @return 分类列表
     */
    List<CategoryDTO> listCategoryDTO();


}

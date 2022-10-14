package com.tan00xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tan00xu.dto.CategoryDTO;
import com.tan00xu.entity.Category;
import com.tan00xu.vo.PageResult;


/**
 * 文章分类服务类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/14 15:17:58
 */
public interface CategoryService extends IService<Category> {

    /**
     * 查询分类列表
     *
     * @return 分类列表
     */
    PageResult<CategoryDTO> listCategories();


}
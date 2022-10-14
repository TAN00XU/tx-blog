package com.tan00xu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tan00xu.dao.CategoryDao;
import com.tan00xu.dto.CategoryDTO;
import com.tan00xu.entity.Category;
import com.tan00xu.service.CategoryService;
import com.tan00xu.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 文章分类服务实现类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/14 15:18:20
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public PageResult<CategoryDTO> listCategories() {
        return new PageResult<>(categoryDao.listCategoryDTO(), Math.toIntExact(categoryDao.selectCount(null)));
    }

}

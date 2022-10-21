package com.tan00xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tan00xu.dto.CategoryBackDTO;
import com.tan00xu.dto.CategoryDTO;
import com.tan00xu.entity.Category;
import com.tan00xu.vo.CategoryVO;
import com.tan00xu.vo.ConditionVO;
import com.tan00xu.vo.PageResult;

import java.util.List;


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


    /**
     * 查询分类 后台
     *
     * @param conditionVO 条件VO
     * @return {@link PageResult}<{@link CategoryBackDTO}>
     */
    PageResult<CategoryBackDTO> listBackCategories(ConditionVO conditionVO);


    /**
     * 删除分类 后台
     *
     * @param categoryIdList 分类id列表
     */
    void deleteCategory(List<Integer> categoryIdList);


    /**
     * 添加或修改分类 后台
     *
     * @param categoryVO 分类
     */
    void saveOrUpdateCategory(CategoryVO categoryVO);
}
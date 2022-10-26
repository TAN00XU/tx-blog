package com.tan00xu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tan00xu.dao.ArticleDao;
import com.tan00xu.dao.CategoryDao;
import com.tan00xu.dto.CategoryBackDTO;
import com.tan00xu.dto.CategoryDTO;
import com.tan00xu.dto.CategoryOptionDTO;
import com.tan00xu.entity.Article;
import com.tan00xu.entity.Category;
import com.tan00xu.exception.BizException;
import com.tan00xu.service.CategoryService;
import com.tan00xu.util.BeanCopyUtils;
import com.tan00xu.util.CmdOutputInformationUtils;
import com.tan00xu.util.PagingUtils;
import com.tan00xu.vo.CategoryVO;
import com.tan00xu.vo.ConditionVO;
import com.tan00xu.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


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

    @Autowired
    private ArticleDao articleDao;

    @Override
    public PageResult<CategoryDTO> listCategories() {
        return new PageResult<>(categoryDao.listCategoryDTO(), Math.toIntExact(categoryDao.selectCount(null)));
    }

    @Override
    public List<CategoryOptionDTO> listCategoriesBySearch(ConditionVO condition) {
        // 搜索分类
        List<Category> categoryList = categoryDao.selectList(
                new LambdaQueryWrapper<Category>()
                        .like(StringUtils.isNotBlank(condition.getKeywords()), Category::getCategoryName, condition.getKeywords())
                        .orderByDesc(Category::getId));
        return BeanCopyUtils.copyList(categoryList, CategoryOptionDTO.class);
    }

    @Override
    public PageResult<CategoryBackDTO> listBackCategories(ConditionVO condition) {
        // 查询分类数量
        Long count = categoryDao.selectCount(
                new LambdaQueryWrapper<Category>()
                        .like(StringUtils.isNotBlank(condition.getKeywords()), Category::getCategoryName, condition.getKeywords())
        );
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询分类列表
        List<CategoryBackDTO> categoryList = categoryDao.listCategoryBackDTO(PagingUtils.getLimitCurrent(), PagingUtils.getSize(), condition);
        return new PageResult<>(categoryList, Math.toIntExact(count));
    }

    @Override
    public void deleteCategory(List<Integer> categoryIdList) {
        // 查询分类id下是否有文章
        Long count = articleDao.selectCount(
                new LambdaQueryWrapper<Article>()
                        .in(Article::getCategoryId, categoryIdList));
        if (count > 0) {
            throw new BizException("删除失败，该分类下存在文章");
        }
        categoryDao.deleteBatchIds(categoryIdList);
    }

    @Override
    public void saveOrUpdateCategory(CategoryVO categoryVO) {
        CmdOutputInformationUtils.error("进入");
        // 判断分类名重复
        Category existCategory = categoryDao.selectOne(
                new LambdaQueryWrapper<Category>()
                        .select(Category::getId)
                        .eq(Category::getCategoryName, categoryVO.getCategoryName()));
        if (Objects.nonNull(existCategory) && !existCategory.getId().equals(categoryVO.getId())) {
            throw new BizException("分类名已存在");
        }
        // 插入数据
        Category category = Category.builder()
                .id(categoryVO.getId())
                .categoryName(categoryVO.getCategoryName())
                .build();
        CmdOutputInformationUtils.error(category);
        this.saveOrUpdate(category);
    }
}

package com.tan00xu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tan00xu.dao.ArticleTagDao;
import com.tan00xu.dao.TagDao;
import com.tan00xu.dto.TagBackDTO;
import com.tan00xu.dto.TagDTO;
import com.tan00xu.entity.ArticleTag;
import com.tan00xu.entity.Tag;
import com.tan00xu.exception.BizException;
import com.tan00xu.service.TagService;
import com.tan00xu.util.BeanCopyUtils;
import com.tan00xu.util.PagingUtils;
import com.tan00xu.vo.ConditionVO;
import com.tan00xu.vo.PageResult;
import com.tan00xu.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * 标签服务实现类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/15 11:13:01
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagDao, Tag> implements TagService {
    @Autowired
    private TagDao tagDao;
    @Autowired
    private ArticleTagDao articleTagDao;

    @Override
    public PageResult<TagDTO> listTags() {
        // 查询标签列表
        List<Tag> tagList = tagDao.selectList(null);
        // 转换DTO
        List<TagDTO> tagDTOList = BeanCopyUtils.copyList(tagList, TagDTO.class);
        // 查询标签数量
        Integer count = Math.toIntExact(tagDao.selectCount(null));
        return new PageResult<>(tagDTOList, count);
    }

    @Override
    public List<TagDTO> listTagsBySearch(ConditionVO condition) {
        // 搜索标签
        List<Tag> tagList = tagDao.selectList(
                new LambdaQueryWrapper<Tag>()
                        .like(StringUtils.isNotBlank(condition.getKeywords()), Tag::getTagName, condition.getKeywords())
                        .orderByDesc(Tag::getId));
        return BeanCopyUtils.copyList(tagList, TagDTO.class);
    }

    @Override
    public PageResult<TagBackDTO> listTagBackDTO(ConditionVO condition) {
        // 查询标签数量
        Long count = tagDao.selectCount(
                new LambdaQueryWrapper<Tag>()
                        .like(StringUtils.isNotBlank(condition.getKeywords()), Tag::getTagName, condition.getKeywords()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询标签列表
        List<TagBackDTO> tagList = tagDao.listTagBackDTO(PagingUtils.getLimitCurrent(), PagingUtils.getSize(), condition);
        return new PageResult<>(tagList, Math.toIntExact(count));
    }

    @Override
    public void deleteTag(List<Integer> tagIdList) {
        // 查询标签下是否有文章
        Long count = articleTagDao.selectCount(
                new LambdaQueryWrapper<ArticleTag>()
                        .in(ArticleTag::getTagId, tagIdList));
        if (count > 0) {
            throw new BizException("删除失败，该标签下存在文章");
        }
        tagDao.deleteBatchIds(tagIdList);
    }


    @Override
    public void saveOrUpdateTag(TagVO tagVO) {
        // 查询标签名是否存在
        Tag existTag = tagDao.selectOne(
                new LambdaQueryWrapper<Tag>()
                        .select(Tag::getId)
                        .eq(Tag::getTagName, tagVO.getTagName()));
        if (Objects.nonNull(existTag) && !existTag.getId().equals(tagVO.getId())) {
            throw new BizException("标签名已存在");
        }
//        Tag tag = BeanCopyUtils.copyObject(tagVO, Tag.class);
        Tag tag = Tag.builder()
                .id(tagVO.getId())
                .tagName(tagVO.getTagName())
                .build();
        this.saveOrUpdate(tag);
    }
}

package com.tan00xu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tan00xu.dao.TagDao;
import com.tan00xu.dto.TagDTO;
import com.tan00xu.entity.Tag;
import com.tan00xu.service.TagService;
import com.tan00xu.util.BeanCopyUtils;
import com.tan00xu.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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


}

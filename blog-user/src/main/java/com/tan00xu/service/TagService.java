package com.tan00xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tan00xu.dto.TagDTO;
import com.tan00xu.entity.Tag;
import com.tan00xu.vo.PageResult;

/**
 * 标签服务
 *
 * @author yezhiqiu
 * @date 2021/07/29
 */
public interface TagService extends IService<Tag> {


    /**
     * 查询标签列表
     *
     * @return 标签列表 {@link PageResult}<{@link TagDTO}>
     */
    PageResult<TagDTO> listTags();


}

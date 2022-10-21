package com.tan00xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tan00xu.dto.TagBackDTO;
import com.tan00xu.dto.TagDTO;
import com.tan00xu.entity.Tag;
import com.tan00xu.vo.ConditionVO;
import com.tan00xu.vo.PageResult;
import com.tan00xu.vo.TagVO;

import java.util.List;

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


    /**
     * 查询标签列表 后台
     *
     * @param condition 条件
     * @return {@link PageResult}<{@link TagBackDTO}>
     */
    PageResult<TagBackDTO> listTagBackDTO(ConditionVO condition);


    /**
     * 删除标签 后台
     *
     * @param tagIdList 标签id列表
     */
    void deleteTag(List<Integer> tagIdList);


    /**
     * 添加或修改标签 后台
     *
     * @param tagVO 标签
     */
    void saveOrUpdateTag(TagVO tagVO);
}

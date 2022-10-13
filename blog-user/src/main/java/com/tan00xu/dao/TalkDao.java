package com.tan00xu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tan00xu.dto.TalkDTO;
import com.tan00xu.entity.Talk;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 说说Dao类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/13 09:38:31
 */
@Mapper
public interface TalkDao extends BaseMapper<Talk> {

    /**
     * 获取说说列表
     *
     * @param current 页码
     * @param size    大小
     * @return {@link List<TalkDTO>}
     */
    List<TalkDTO> listTalks(@Param("current") Long current, @Param("size") Long size);


}





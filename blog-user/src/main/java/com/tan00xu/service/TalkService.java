package com.tan00xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tan00xu.dto.TalkDTO;
import com.tan00xu.entity.Talk;
import com.tan00xu.vo.PageResult;

import java.util.List;


/**
 * 说说服务类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/13 09:37:12
 */
public interface TalkService extends IService<Talk> {

    /**
     * 获取首页说说列表
     *
     * @return {@link List<String>} 说说列表
     */
    List<String> listHomeTalks();

    /**
     * 获取说说列表
     *
     * @return {@link PageResult<TalkDTO>} 说说列表
     */
    PageResult<TalkDTO> listTalks();

}

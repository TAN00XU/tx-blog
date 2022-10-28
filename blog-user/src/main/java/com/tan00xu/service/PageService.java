package com.tan00xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tan00xu.entity.Page;
import com.tan00xu.vo.PageVO;

import java.util.List;


/**
 * 页面服务类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/28 14:23:59
 */
public interface PageService extends IService<Page> {

    /**
     * 获取页面列表
     *
     * @return {@link List}<{@link PageVO}>
     */
    List<PageVO> listPages();

}

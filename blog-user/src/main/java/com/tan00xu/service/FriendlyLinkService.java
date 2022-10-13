package com.tan00xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tan00xu.dto.FriendlyLinkDTO;
import com.tan00xu.entity.FriendlyLink;

import java.util.List;


/**
 * 友情链接服务类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/13 14:09:16
 */
public interface FriendlyLinkService extends IService<FriendlyLink> {

    /**
     * 查看友链列表
     *
     * @return 友链列表
     */
    List<FriendlyLinkDTO> listFriendLinks();

}

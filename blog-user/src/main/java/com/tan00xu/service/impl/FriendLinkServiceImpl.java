package com.tan00xu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tan00xu.dao.FriendlyLinkDao;
import com.tan00xu.dto.FriendlyLinkDTO;
import com.tan00xu.entity.FriendlyLink;
import com.tan00xu.service.FriendlyLinkService;
import com.tan00xu.util.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 友情链接服务实现类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/13 14:09:34
 */
@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendlyLinkDao, FriendlyLink> implements FriendlyLinkService {
    @Autowired
    private FriendlyLinkDao friendlyLinkDao;

    @Override
    public List<FriendlyLinkDTO> listFriendLinks() {
        // 查询友链列表
        List<FriendlyLink> friendlyLinkList = friendlyLinkDao.selectList(null);
        return BeanCopyUtils.copyList(friendlyLinkList, FriendlyLinkDTO.class);
    }

}

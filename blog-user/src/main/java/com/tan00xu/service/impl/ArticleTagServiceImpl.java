package com.tan00xu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tan00xu.dao.ArticleTagDao;
import com.tan00xu.entity.ArticleTag;
import com.tan00xu.service.ArticleTagService;
import org.springframework.stereotype.Service;


/**
 * 文章与标签关联服务实现类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/27 12:06:52
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagDao, ArticleTag> implements ArticleTagService {

}

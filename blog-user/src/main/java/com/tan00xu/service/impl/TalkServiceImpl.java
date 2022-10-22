package com.tan00xu.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tan00xu.dao.CommentDao;
import com.tan00xu.dao.TalkDao;
import com.tan00xu.dto.CommentCountDTO;
import com.tan00xu.dto.TalkDTO;
import com.tan00xu.entity.Talk;
import com.tan00xu.service.RedisService;
import com.tan00xu.service.TalkService;
import com.tan00xu.util.CommonUtils;
import com.tan00xu.util.HTMLUtils;
import com.tan00xu.util.PagingUtils;
import com.tan00xu.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.tan00xu.constant.RedisPrefixConst.TALK_LIKE_COUNT;
import static com.tan00xu.enums.TalkStatusEnum.PUBLIC;


/**
 * 说说服务实现类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/22 18:16:24
 */
@Service
public class TalkServiceImpl extends ServiceImpl<TalkDao, Talk> implements TalkService {
    @Autowired
    private TalkDao talkDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private RedisService redisService;

    @Override
    public List<String> listHomeTalks() {
        // 查询最新10条说说
        return talkDao.selectList(new LambdaQueryWrapper<Talk>()
                        .eq(Talk::getStatus, PUBLIC.getStatus())
                        .orderByDesc(Talk::getIsTop)
                        .orderByDesc(Talk::getId)
                        .last("limit 10"))
                .stream()
                .map(item -> item.getContent().length() > 200 ?
                        HTMLUtils.deleteHMTLTag(item.getContent().substring(0, 200)) :
                        HTMLUtils.deleteHMTLTag(item.getContent()))
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<TalkDTO> listTalks() {
        // 查询说说总量
        Long count = talkDao.selectCount(
                new LambdaQueryWrapper<Talk>()
                        .eq(Talk::getStatus, PUBLIC.getStatus())
        );
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询说说
        List<TalkDTO> talkDTOList = talkDao.listTalks(PagingUtils.getLimitCurrent(), PagingUtils.getSize());

        //说说id列表
        List<Integer> talkIdList = talkDTOList
                .stream()
                .map(TalkDTO::getId)
                .collect(Collectors.toList());
        // 说说id查询说说评论量
        Map<Integer, Integer> commentCountMap = commentDao.listCommentCountByTopicIds(talkIdList)
                .stream()
                .collect(Collectors.toMap(CommentCountDTO::getId, CommentCountDTO::getCommentCount));
        // 查询说说点赞量
        Map<String, Object> likeCountMap = redisService.hGetAll(TALK_LIKE_COUNT);
        talkDTOList.forEach(
                item -> {
                    item.setLikeCount((Integer) likeCountMap.get(item.getId().toString()));
                    item.setCommentCount(commentCountMap.get(item.getId()));
                    // 转换图片格式
                    if (Objects.nonNull(item.getImages())) {
                        item.setImgList(CommonUtils.castList(JSON.parseObject(item.getImages(), List.class), String.class));
                    }
                });
        return new PageResult<>(talkDTOList, Math.toIntExact(count));
    }

}





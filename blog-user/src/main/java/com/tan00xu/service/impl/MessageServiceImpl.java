package com.tan00xu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tan00xu.dao.MessageDao;
import com.tan00xu.dto.MessageDTO;
import com.tan00xu.entity.Message;
import com.tan00xu.service.MessageService;
import com.tan00xu.util.BeanCopyUtils;
import com.tan00xu.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.tan00xu.constant.CommonConst.TRUE;


/**
 * 留言服务实现类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/07 18:42:53
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageDao, Message> implements MessageService {
    @Autowired
    private MessageDao messageDao;


    @Override
    public Result<List<MessageDTO>> listMessages() {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Message::getId, Message::getNickname, Message::getAvatar, Message::getMessageContent, Message::getTime);
        wrapper.eq(Message::getIsReview, TRUE);
        //查询留言列表
        List<Message> messages = messageDao.selectList(wrapper);
        if (messages.size() > 0) {
            List<MessageDTO> messageList = BeanCopyUtils.copyList(messages, MessageDTO.class);
            return Result.ok(messageList);
        }
        return Result.fail();
    }


}

package com.tan00xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tan00xu.dto.MessageDTO;
import com.tan00xu.entity.Message;
import com.tan00xu.vo.Result;

import java.util.List;


/**
 * 留言服务类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/07 18:41:50
 */
public interface MessageService extends IService<Message> {


    /**
     * 查看留言列表
     *
     * @return 留言列表
     */
    Result<List<MessageDTO>> listMessages();


}

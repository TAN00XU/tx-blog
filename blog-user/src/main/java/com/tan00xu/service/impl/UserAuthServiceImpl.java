package com.tan00xu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tan00xu.dao.UserAuthDao;
import com.tan00xu.dto.EmailDTO;
import com.tan00xu.entity.UserAuth;
import com.tan00xu.exception.BizException;
import com.tan00xu.service.RedisService;
import com.tan00xu.service.UserAuthService;
import com.tan00xu.vo.Result;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.tan00xu.constant.MQPrefixConst.EMAIL_EXCHANGE;
import static com.tan00xu.constant.RedisPrefixConst.CODE_EXPIRE_TIME;
import static com.tan00xu.constant.RedisPrefixConst.USER_CODE_KEY;
import static com.tan00xu.util.CommonUtils.checkEmail;
import static com.tan00xu.util.CommonUtils.getRandomCode;


/**
 * 用户账号服务
 *
 * @author yezhiqiu
 * @date 2021/08/10
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthDao, UserAuth> implements UserAuthService {
    @Autowired
    private RedisService redisService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Result<?> sendCode(String username) {
        // 校验账号是否合法
        if (!checkEmail(username)) {
            try {
                throw new BizException("请输入正确邮箱");
            } catch (Exception e) {
                e.printStackTrace();
                return Result.fail();
            }
        }
        // 生成六位随机验证码发送
        String code = getRandomCode();
        // 发送验证码
        EmailDTO emailDTO = EmailDTO.builder()
                .email(username)
                .subject("验证码")
                .content("您的验证码为 " + code + " 有效期15分钟，请不要告诉他人哦！")
                .build();

        //手动序列化的方式
//        rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, "", new Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));
        //设置对消息进行序列化
//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, "", emailDTO);
        // 将验证码存入redis，设置过期时间为15分钟
        redisService.set(USER_CODE_KEY + username, code, CODE_EXPIRE_TIME);
        return Result.ok();
    }

}

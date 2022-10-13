package com.tan00xu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tan00xu.constant.CommonConst;
import com.tan00xu.dao.UserAuthDao;
import com.tan00xu.dao.UserInfoDao;
import com.tan00xu.dao.UserRoleDao;
import com.tan00xu.dto.EmailDTO;
import com.tan00xu.entity.UserAuth;
import com.tan00xu.entity.UserInfo;
import com.tan00xu.entity.UserRole;
import com.tan00xu.enums.LoginTypeEnum;
import com.tan00xu.enums.RoleEnum;
import com.tan00xu.exception.BizException;
import com.tan00xu.service.BlogInfoService;
import com.tan00xu.service.RedisService;
import com.tan00xu.service.UserAuthService;
import com.tan00xu.vo.Result;
import com.tan00xu.vo.UserVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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

    @Autowired
    private UserAuthDao userAuthDao;

    @Autowired
    private BlogInfoService blogInfoService;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public Result<?> sendCode(String username) {
        // 校验账号是否合法
        if (!checkEmail(username)) {
            throw new BizException("请输入正确邮箱");
        }
        // 生成六位随机验证码发送
        String code = getRandomCode();
        // 发送验证码
        EmailDTO emailDTO = EmailDTO.builder()
                .email(username)
                .subject("验证码")
                .content("您的验证码为 " + code + " 有效期15分钟，请不要告诉他人哦！")
                .build();

        /**
         *  //手动序列化的方式
         *  rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, "", new Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));
         *  //设置对消息进行序列化
         *  rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
         */

        rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, "", emailDTO);
        // 将验证码存入redis，设置过期时间为15分钟
        redisService.set(USER_CODE_KEY + username, code, CODE_EXPIRE_TIME);
        return Result.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(UserVO user) {
        // 校验账号是否合法
        if (checkUser(user)) {
            throw new BizException("邮箱已被注册！");
        }
        // 新增用户信息
        UserInfo userInfo = UserInfo.builder()
                .email(user.getUsername())
                .nickname(CommonConst.DEFAULT_NICKNAME + IdWorker.getId())
                .avatar(blogInfoService.getWebsiteConfig().getUserAvatar())
                .build();
        userInfoDao.insert(userInfo);

        // 绑定用户角色
        UserRole userRole = UserRole.builder()
                .userId(userInfo.getId())
                .roleId(RoleEnum.USER.getRoleId())
                .build();
        userRoleDao.insert(userRole);

        // 新增用户账号
        UserAuth userAuth = UserAuth.builder()
                .userInfoId(userInfo.getId())
                .username(user.getUsername())
                //密码加密
                .password(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()))
                .loginType(LoginTypeEnum.EMAIL.getType())
                .build();
        userAuthDao.insert(userAuth);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePassword(UserVO user) {
        // 校验账号是否合法
        if (!checkUser(user)) {
            throw new BizException("邮箱尚未注册！");
        }
        // 根据用户名修改密码
        userAuthDao.update(
                new UserAuth(),
                new LambdaUpdateWrapper<UserAuth>()
                        .set(UserAuth::getPassword, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()))
                        .eq(UserAuth::getUsername, user.getUsername())
        );
    }

    /**
     * 校验用户数据是否合法
     *
     * @param user 用户数据
     * @return 结果
     */
    private Boolean checkUser(UserVO user) {
        if (!user.getCode().equals(redisService.get(USER_CODE_KEY + user.getUsername()))) {
            throw new BizException("验证码错误！");
        }
        //查询用户名邮箱是否存在，及是否已注册
        UserAuth userAuth = userAuthDao.selectOne(
                new LambdaQueryWrapper<UserAuth>()
                        .select(UserAuth::getUsername)
                        .eq(UserAuth::getUsername, user.getUsername())
        );
        return Objects.nonNull(userAuth);
    }
}

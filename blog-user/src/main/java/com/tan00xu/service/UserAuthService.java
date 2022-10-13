package com.tan00xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tan00xu.entity.UserAuth;
import com.tan00xu.vo.Result;
import com.tan00xu.vo.UserVO;


/**
 * 用户账户服务类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/01 13:16:02
 */
public interface UserAuthService extends IService<UserAuth> {

    /**
     * 发送邮箱验证码
     *
     * @param username 邮箱号
     * @return
     */
    Result<?> sendCode(String username);

    /**
     * 用户注册
     *
     * @param user 用户
     */
    void register(UserVO user);

    /**
     * 修改用户密码
     *
     * @param user 用户对象
     */
    void updatePassword(UserVO user);

}

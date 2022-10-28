package com.tan00xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tan00xu.entity.UserInfo;
import com.tan00xu.vo.EmailVO;
import com.tan00xu.vo.UserInfoVO;
import org.springframework.web.multipart.MultipartFile;


/**
 * 用户信息服务类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/28 21:42:06
 */
public interface UserInfoService extends IService<UserInfo> {


    /**
     * 修改用户信息
     *
     * @param userInfoVO 用户资料
     */
    void updateUserInfo(UserInfoVO userInfoVO);

    /**
     * 修改用户头像
     *
     * @param file 头像图片
     * @return 头像地址
     */
    String updateUserAvatar(MultipartFile file);


    /**
     * 绑定用户邮箱
     *
     * @param emailVO 邮箱
     */
    void saveUserEmail(EmailVO emailVO);
}

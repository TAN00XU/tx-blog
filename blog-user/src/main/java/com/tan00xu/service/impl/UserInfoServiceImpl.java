package com.tan00xu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tan00xu.dao.UserInfoDao;
import com.tan00xu.entity.UserInfo;
import com.tan00xu.enums.FilePathEnum;
import com.tan00xu.exception.BizException;
import com.tan00xu.service.RedisService;
import com.tan00xu.service.UserInfoService;
import com.tan00xu.strategy.context.UploadStrategyContext;
import com.tan00xu.util.UserUtils;
import com.tan00xu.vo.EmailVO;
import com.tan00xu.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.tan00xu.constant.RedisPrefixConst.USER_CODE_KEY;


/**
 * 用户信息服务实现类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/28 21:42:41
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfo> implements UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserInfo(UserInfoVO userInfoVO) {
        // 封装用户信息
        UserInfo userInfo = UserInfo.builder()
                .id(UserUtils.getLoginUser().getUserInfoId())
                .nickname(userInfoVO.getNickname())
                .intro(userInfoVO.getIntro())
                .webSite(userInfoVO.getWebSite())
                .build();
        userInfoDao.updateById(userInfo);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updateUserAvatar(MultipartFile file) {
        // 头像上传
        String avatar = uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.AVATAR.getPath());
        // 更新用户信息
        UserInfo userInfo = UserInfo.builder()
                .id(UserUtils.getLoginUser().getUserInfoId())
                .avatar(avatar)
                .build();
        userInfoDao.updateById(userInfo);
        return avatar;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUserEmail(EmailVO emailVO) {
        if (!emailVO.getCode().equals(redisService.get(USER_CODE_KEY + emailVO.getEmail()).toString())) {
            throw new BizException("验证码错误！");
        }
        UserInfo userInfo = UserInfo.builder()
                .id(UserUtils.getLoginUser().getUserInfoId())
                .email(emailVO.getEmail())
                .build();
        userInfoDao.updateById(userInfo);
    }
}

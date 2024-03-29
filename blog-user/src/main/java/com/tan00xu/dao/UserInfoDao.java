package com.tan00xu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tan00xu.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户信息Dao类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/10 23:19:28
 */
@Mapper
public interface UserInfoDao extends BaseMapper<UserInfo> {

}

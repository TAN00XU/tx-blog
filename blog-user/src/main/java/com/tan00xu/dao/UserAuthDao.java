package com.tan00xu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tan00xu.entity.UserAuth;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户账号Dao类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/01 11:44:53
 */
@Mapper
public interface UserAuthDao extends BaseMapper<UserAuth> {


}

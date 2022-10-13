package com.tan00xu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tan00xu.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户角色Dao类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/11 10:13:47
 */
@Mapper
public interface UserRoleDao extends BaseMapper<UserRole> {

}

package com.tan00xu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tan00xu.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色与菜单对应表
 *
 * @author yezhiqiu
 * @date 2021/08/10
 */
@Mapper
public interface RoleMenuDao extends BaseMapper<RoleMenu> {

}

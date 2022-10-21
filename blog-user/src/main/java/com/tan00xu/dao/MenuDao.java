package com.tan00xu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tan00xu.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 菜单Dao类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/20 10:24:20
 */
@Mapper
public interface MenuDao extends BaseMapper<Menu> {

    /**
     * 根据用户id查询菜单
     *
     * @param userId 用户信息id
     * @return 菜单列表
     */
    List<Menu> listMenusByUserInfoId(Integer userId);

}

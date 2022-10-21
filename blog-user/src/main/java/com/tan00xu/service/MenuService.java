package com.tan00xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tan00xu.dto.UserMenuDTO;
import com.tan00xu.entity.Menu;

import java.util.List;


/**
 * 菜单服务类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/20 10:16:11
 */
public interface MenuService extends IService<Menu> {


    /**
     * 查看用户菜单
     *
     * @return 菜单列表
     */
    List<UserMenuDTO> listUserMenus();

}

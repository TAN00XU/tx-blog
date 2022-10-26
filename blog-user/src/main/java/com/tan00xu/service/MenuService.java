package com.tan00xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tan00xu.dto.LabelOptionDTO;
import com.tan00xu.dto.MenuDTO;
import com.tan00xu.dto.UserMenuDTO;
import com.tan00xu.entity.Menu;
import com.tan00xu.vo.ConditionVO;
import com.tan00xu.vo.MenuVO;

import java.util.List;


/**
 * 菜单服务类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/20 10:16:11
 */
public interface MenuService extends IService<Menu> {


    /**
     * 查看菜单列表 后台
     *
     * @param conditionVO 条件VO
     * @return 菜单列表 {@link List}<{@link MenuDTO}>
     */
    List<MenuDTO> listMenus(ConditionVO conditionVO);

    /**
     * 新增或修改菜单 后台
     *
     * @param menuVO 菜单信息
     */
    void saveOrUpdateMenu(MenuVO menuVO);

    /**
     * 删除菜单 后台
     *
     * @param menuId 菜单id
     */
    void deleteMenu(Integer menuId);

    /**
     * 查看用户菜单 后台
     *
     * @return 用户菜单列表 {@link List}<{@link UserMenuDTO}>
     */
    List<UserMenuDTO> listUserMenus();


    /**
     * 查看角色的菜单选项列表 后台
     *
     * @return 菜单选项列表 {@link List}<{@link LabelOptionDTO}>
     */
    List<LabelOptionDTO> listRoleMenuOptions();

}

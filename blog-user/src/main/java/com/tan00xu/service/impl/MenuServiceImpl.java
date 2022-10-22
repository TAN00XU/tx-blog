package com.tan00xu.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tan00xu.dao.MenuDao;
import com.tan00xu.dto.UserMenuDTO;
import com.tan00xu.entity.Menu;
import com.tan00xu.service.MenuService;
import com.tan00xu.util.BeanCopyUtils;
import com.tan00xu.util.CmdOutputInformationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.tan00xu.constant.CommonConst.COMPONENT;
import static com.tan00xu.constant.CommonConst.TRUE;


/**
 * 菜单服务实现类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/20 10:23:42
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {
    @Autowired
    private MenuDao menuDao;

    @Override
    public List<UserMenuDTO> listUserMenus() {
        CmdOutputInformationUtils.info("service.impl.MenuServiceImpl.listUserMenus固定了用户菜单查询");
        // 查询用户菜单信息
//        List<Menu> menuList = menuDao.listMenusByUserInfoId(UserUtils.getLoginUser().getId());
        List<Menu> menuList = menuDao.listMenusByUserInfoId(1);
        // 获取目录列表
        List<Menu> catalogList = listCatalog(menuList);

        // 获取目录下的子菜单
        Map<Integer, List<Menu>> childrenMap = getMenuMap(menuList);

        // 转换前端菜单格式
        return convertUserMenuList(catalogList, childrenMap);
    }

    /**
     * 获取目录列表
     *
     * @param menuList 菜单列表
     * @return 目录列表
     */
    private List<Menu> listCatalog(List<Menu> menuList) {
        return menuList.stream()
                //过滤掉有父级id的
                .filter(item -> Objects.isNull(item.getParentId()))
                .sorted(Comparator.comparing(Menu::getOrderNum))
                .collect(Collectors.toList());
    }

    /**
     * 获取目录下菜单列表
     *
     * @param menuList 菜单列表
     * @return 目录下的菜单列表
     */
    private Map<Integer, List<Menu>> getMenuMap(List<Menu> menuList) {
        return menuList.stream()
                //过滤有父级id的
                .filter(item -> Objects.nonNull(item.getParentId()))
                //按父级id分组
                .collect(Collectors.groupingBy(Menu::getParentId));
    }

    /**
     * 转换用户菜单格式
     *
     * @param catalogList 目录
     * @param childrenMap 子菜单
     */
    private List<UserMenuDTO> convertUserMenuList(List<Menu> catalogList, Map<Integer, List<Menu>> childrenMap) {
        return catalogList.stream().map(
                item -> {
                    // 获取目录
                    UserMenuDTO userMenuDTO = new UserMenuDTO();
                    List<UserMenuDTO> listUserMenuDTO = new ArrayList<>();
                    // 获取目录下的子菜单
                    List<Menu> listChildren = childrenMap.get(item.getId());
                    if (CollectionUtils.isNotEmpty(listChildren)) {
                        // 多级菜单处理
                        userMenuDTO = BeanCopyUtils.copyObject(item, UserMenuDTO.class);
                        listUserMenuDTO = listChildren.stream()
                                .sorted(Comparator.comparing(Menu::getOrderNum))
                                .map(menu -> {
                                    UserMenuDTO dto = BeanCopyUtils.copyObject(menu, UserMenuDTO.class);
                                    dto.setHidden(menu.getIsHidden().equals(TRUE));
                                    return dto;
                                })
                                .collect(Collectors.toList());
                    } else {
                        // 一级菜单处理
                        userMenuDTO.setPath(item.getPath());
                        userMenuDTO.setComponent(COMPONENT);
                        listUserMenuDTO.add(
                                UserMenuDTO.builder()
                                        .path("")
                                        .name(item.getName())
                                        .icon(item.getIcon())
                                        .component(item.getComponent())
                                        .build()
                        );
                    }
                    userMenuDTO.setHidden(item.getIsHidden().equals(TRUE));
                    userMenuDTO.setChildren(listUserMenuDTO);
                    return userMenuDTO;
                }
        ).collect(Collectors.toList());
    }
}

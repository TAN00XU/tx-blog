package com.tan00xu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tan00xu.dao.MenuDao;
import com.tan00xu.dto.LabelOptionDTO;
import com.tan00xu.dto.MenuDTO;
import com.tan00xu.dto.UserMenuDTO;
import com.tan00xu.entity.Menu;
import com.tan00xu.service.MenuService;
import com.tan00xu.util.BeanCopyUtils;
import com.tan00xu.util.UserUtils;
import com.tan00xu.vo.ConditionVO;
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
    public List<MenuDTO> listMenus(ConditionVO conditionVO) {
        // 查询菜单数据
        List<Menu> menuList = menuDao.selectList(
                new LambdaQueryWrapper<Menu>()
                        .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Menu::getName, conditionVO.getKeywords()));
        // 获取目录列表
        List<Menu> catalogList = listCatalog(menuList);
        // 获取目录下的子菜单
        Map<Integer, List<Menu>> childrenMap = getMenuMap(menuList);
        // 组装目录菜单数据
        List<MenuDTO> menuDTOList = catalogList.stream()
                .map(item -> {
                            MenuDTO menuDTO = BeanCopyUtils.copyObject(item, MenuDTO.class);
                            // 获取目录下的菜单排序
                            List<MenuDTO> list = BeanCopyUtils.copyList(childrenMap.get(item.getId()), MenuDTO.class)
                                    .stream()
                                    .sorted(Comparator.comparing(MenuDTO::getOrderNum))
                                    .collect(Collectors.toList());
                            menuDTO.setChildren(list);
                            // 移除已添加进目录的
                            childrenMap.remove(item.getId());
                            return menuDTO;
                        }
                ).sorted(Comparator.comparing(MenuDTO::getOrderNum))
                .collect(Collectors.toList());
        // 若还有菜单未取出则拼接
        if (CollectionUtils.isNotEmpty(childrenMap)) {
            List<Menu> childrenList = new ArrayList<>();
            childrenMap.values().forEach(childrenList::addAll);
            List<MenuDTO> childrenDTOList = childrenList.stream()
                    .map(item -> BeanCopyUtils.copyObject(item, MenuDTO.class))
                    .sorted(Comparator.comparing(MenuDTO::getOrderNum))
                    .collect(Collectors.toList());
            menuDTOList.addAll(childrenDTOList);
        }
        return menuDTOList;
    }

    @Override
    public List<UserMenuDTO> listUserMenus() {
        // 查询用户菜单信息
        List<Menu> menuList = menuDao.listMenusByUserInfoId(UserUtils.getLoginUser().getId());
        // 获取目录列表
        List<Menu> catalogList = listCatalog(menuList);
        // 获取目录下的子菜单
        Map<Integer, List<Menu>> childrenMap = getMenuMap(menuList);
        // 转换前端菜单格式
        return convertUserMenuList(catalogList, childrenMap);
    }

    @Override
    public List<LabelOptionDTO> listRoleMenuOptions() {
        // 查询菜单数据
        List<Menu> menuList = menuDao.selectList(
                new LambdaQueryWrapper<Menu>()
                        .select(Menu::getId, Menu::getName, Menu::getParentId, Menu::getOrderNum));
        // 获取目录列表
        List<Menu> catalogList = listCatalog(menuList);
        // 获取目录下的子菜单
        Map<Integer, List<Menu>> childrenMap = getMenuMap(menuList);
        // 组装目录菜单数据
        return catalogList.stream().map(
                item -> {
                    // 获取目录下的菜单排序
                    List<LabelOptionDTO> childrenList = new ArrayList<>();
                    List<Menu> children = childrenMap.get(item.getId());
                    if (CollectionUtils.isNotEmpty(children)) {
                        childrenList = children.stream()
                                .sorted(Comparator.comparing(Menu::getOrderNum))
                                .map(menu -> LabelOptionDTO.builder()
                                        .id(menu.getId())
                                        .label(menu.getName())
                                        .build())
                                .collect(Collectors.toList());
                    }
                    return LabelOptionDTO.builder()
                            .id(item.getId())
                            .label(item.getName())
                            .children(childrenList)
                            .build();
                }).collect(Collectors.toList());
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

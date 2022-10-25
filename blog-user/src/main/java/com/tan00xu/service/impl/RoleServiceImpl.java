package com.tan00xu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tan00xu.constant.CommonConst;
import com.tan00xu.dao.RoleDao;
import com.tan00xu.dao.UserRoleDao;
import com.tan00xu.dto.RoleDTO;
import com.tan00xu.entity.*;
import com.tan00xu.exception.BizException;
import com.tan00xu.handler.FilterInvocationSecurityMetadataSourceImpl;
import com.tan00xu.service.MenuService;
import com.tan00xu.service.RoleMenuService;
import com.tan00xu.service.RoleResourceService;
import com.tan00xu.service.RoleService;
import com.tan00xu.util.PagingUtils;
import com.tan00xu.vo.ConditionVO;
import com.tan00xu.vo.PageResult;
import com.tan00xu.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 角色服务实现类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/23 10:39:02
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RoleResourceService roleResourceService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private MenuService menuService;
    @Autowired
    private FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource;

    @Override
    public PageResult<RoleDTO> listRoles(ConditionVO conditionVO) {
        // 查询角色列表
        List<RoleDTO> roleDTOList = roleDao.listRoles(PagingUtils.getLimitCurrent(), PagingUtils.getSize(), conditionVO);


        List<Integer> parentList = menuService.list(
                new LambdaQueryWrapper<Menu>()
                        .select(Menu::getId)
                        .eq(Menu::getComponent, "Layout")
        ).stream().map(Menu::getId).collect(Collectors.toList());
        // 过滤掉layout的menuId防止前端显示出错
        roleDTOList = roleDTOList.stream()
                .map(item -> {
                            List<Integer> newMenuIdList = item.getMenuIdList()
                                    .stream()
                                    .filter(menuId -> !parentList.contains(menuId))
                                    .collect(Collectors.toList());
                            item.setMenuIdList(newMenuIdList);
                            return item;
                        }
                ).collect(Collectors.toList());

        // 查询总量
        Long count = roleDao.selectCount(
                new LambdaQueryWrapper<Role>()
                        .like(StringUtils.isNotBlank(conditionVO.getKeywords()),
                                Role::getRoleName, conditionVO.getKeywords())
        );
        return new PageResult<>(roleDTOList, Math.toIntExact(count));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateRole(RoleVO roleVO) {
        // 判断角色名是否有重复
        Role existRole = roleDao.selectOne(
                new LambdaQueryWrapper<Role>()
                        .select(Role::getId)
                        .eq(Role::getRoleName, roleVO.getRoleName())
                        .or()
                        .eq(Role::getRoleLabel, roleVO.getRoleLabel())
        );
        if (Objects.nonNull(existRole) && !existRole.getId().equals(roleVO.getId())) {
            throw new BizException("角色名或标签名已存在");
        }
        // 保存或更新角色信息
        Role role = Role.builder()
                .id(roleVO.getId())
                .roleName(roleVO.getRoleName())
                .roleLabel(roleVO.getRoleLabel())
                .isDisable(CommonConst.FALSE)
                .build();
        this.saveOrUpdate(role);

        // 更新角色资源关系
        if (Objects.nonNull(roleVO.getResourceIdList())) {
            // 删除角色和资源对应表记录
            if (Objects.nonNull(roleVO.getId())) {
                roleResourceService.remove(
                        new LambdaQueryWrapper<RoleResource>()
                                .eq(RoleResource::getRoleId, roleVO.getId())
                );
            }
            // 插入角色和资源对应表记录
            List<RoleResource> roleResourceList =
                    roleVO.getResourceIdList().stream()
                            .map(resourceId -> RoleResource.builder()
                                    .roleId(role.getId())
                                    .resourceId(resourceId)
                                    .build()
                            )
                            .collect(Collectors.toList());
            roleResourceService.saveBatch(roleResourceList);
            // 重新加载角色资源信息
            filterInvocationSecurityMetadataSource.clearDataSource();
        }
        // 更新角色菜单关系
        if (Objects.nonNull(roleVO.getMenuIdList())) {
            // 删除角色和菜单对应表记录
            if (Objects.nonNull(roleVO.getId())) {
                roleMenuService.remove(
                        new LambdaQueryWrapper<RoleMenu>()
                                .eq(RoleMenu::getRoleId, roleVO.getId())
                );
            }
            // 插入角色和菜单对应表记录
            List<RoleMenu> roleMenuList =
                    roleVO.getMenuIdList().stream()
                            .map(menuId -> RoleMenu.builder()
                                    .roleId(role.getId())
                                    .menuId(menuId)
                                    .build())
                            .collect(Collectors.toList());
            roleMenuService.saveBatch(roleMenuList);
        }
    }

    @Override
    public void deleteRoles(List<Integer> roleIdList) {
        // 判断角色下是否有用户
        Long count = userRoleDao.selectCount(
                new LambdaQueryWrapper<UserRole>()
                        .in(UserRole::getRoleId, roleIdList));
        if (count > 0) {
            throw new BizException("该角色下存在用户");
        }
        roleDao.deleteBatchIds(roleIdList);
    }


}

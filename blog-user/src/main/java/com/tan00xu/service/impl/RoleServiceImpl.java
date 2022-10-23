package com.tan00xu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tan00xu.dao.RoleDao;
import com.tan00xu.dto.RoleDTO;
import com.tan00xu.entity.Role;
import com.tan00xu.service.RoleService;
import com.tan00xu.util.PagingUtils;
import com.tan00xu.vo.ConditionVO;
import com.tan00xu.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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

    @Override
    public PageResult<RoleDTO> listRoles(ConditionVO conditionVO) {
        // 查询角色列表
        List<RoleDTO> roleDTOList = roleDao.listRoles(PagingUtils.getLimitCurrent(), PagingUtils.getSize(), conditionVO);
        // 查询总量
        Long count = roleDao.selectCount(
                new LambdaQueryWrapper<Role>()
                        .like(StringUtils.isNotBlank(conditionVO.getKeywords()),
                                Role::getRoleName, conditionVO.getKeywords())
        );
        return new PageResult<>(roleDTOList, Math.toIntExact(count));
    }


}

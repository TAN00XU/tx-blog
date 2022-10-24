package com.tan00xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tan00xu.dto.RoleDTO;
import com.tan00xu.entity.Role;
import com.tan00xu.vo.ConditionVO;
import com.tan00xu.vo.PageResult;
import com.tan00xu.vo.RoleVO;

import java.util.List;


/**
 * 角色服务类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/23 10:38:55
 */
public interface RoleService extends IService<Role> {


    /**
     * 查询角色列表
     *
     * @param conditionVO 条件
     * @return 角色列表
     */
    PageResult<RoleDTO> listRoles(ConditionVO conditionVO);


    /**
     * 保存或更新角色 后台
     *
     * @param roleVO 角色
     */
    void saveOrUpdateRole(RoleVO roleVO);


    /**
     * 删除角色 后台
     *
     * @param roleIdList 角色id列表
     */
    void deleteRoles(List<Integer> roleIdList);

}

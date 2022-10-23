package com.tan00xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tan00xu.dto.RoleDTO;
import com.tan00xu.entity.Role;
import com.tan00xu.vo.ConditionVO;
import com.tan00xu.vo.PageResult;


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


}

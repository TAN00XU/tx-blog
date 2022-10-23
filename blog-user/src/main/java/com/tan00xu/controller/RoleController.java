package com.tan00xu.controller;

import com.tan00xu.dto.RoleDTO;
import com.tan00xu.service.RoleService;
import com.tan00xu.vo.ConditionVO;
import com.tan00xu.vo.PageResult;
import com.tan00xu.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色控制器类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/23 10:33:18
 */
@Tag(name = "角色模块")
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 查询角色列表
     *
     * @param conditionVO 条件VO
     * @return {@link Result}<{@link PageResult}<{@link RoleDTO}>>
     */
    @Operation(summary = "查询角色列表")
    @GetMapping("/admin/roles")
    public Result<PageResult<RoleDTO>> listRoles(ConditionVO conditionVO) {
        return Result.ok(roleService.listRoles(conditionVO));
    }

}

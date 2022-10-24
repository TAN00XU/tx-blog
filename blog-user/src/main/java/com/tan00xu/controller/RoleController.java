package com.tan00xu.controller;

import com.tan00xu.dto.RoleDTO;
import com.tan00xu.service.RoleService;
import com.tan00xu.util.CmdOutputInformationUtils;
import com.tan00xu.vo.ConditionVO;
import com.tan00xu.vo.PageResult;
import com.tan00xu.vo.Result;
import com.tan00xu.vo.RoleVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * 查询角色列表 后台
     *
     * @param conditionVO 条件VO
     * @return {@link Result}<{@link PageResult}<{@link RoleDTO}>>
     */
    @Operation(summary = "查询角色列表")
    @GetMapping("/admin/roles")
    public Result<PageResult<RoleDTO>> listRoles(ConditionVO conditionVO) {
        return Result.ok(roleService.listRoles(conditionVO));
    }

    /**
     * 保存或更新角色 后台
     *
     * @param roleVO 角色信息
     * @return {@link Result<>}
     */
    @Operation(summary = "保存或更新角色")
    @PostMapping("/admin/roles")
    public Result<?> saveOrUpdateRole(@RequestBody @Validated RoleVO roleVO) {
        CmdOutputInformationUtils.error(roleVO);
        roleService.saveOrUpdateRole(roleVO);
        return Result.ok();
    }

    /**
     * 删除角色 后台
     *
     * @param roleIdList 角色id列表
     * @return {@link Result<>}
     */
    @Operation(summary = "删除角色")
    @DeleteMapping("/admin/roles")
    public Result<?> deleteRoles(@RequestBody List<Integer> roleIdList) {
        roleService.deleteRoles(roleIdList);
        return Result.ok();
    }
}

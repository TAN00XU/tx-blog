package com.tan00xu.controller;

import com.tan00xu.dto.UserMenuDTO;
import com.tan00xu.service.MenuService;
import com.tan00xu.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单控制器类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/20 10:12:41
 */
@Tag(name = "菜单模块")
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;


    /**
     * 查看当前用户菜单
     *
     * @return {@link Result}<{@link List}<{@link UserMenuDTO}>>
     */
    @Operation(summary = "查看当前用户菜单")
    @GetMapping("/admin/listMenus")
    public Result<List<UserMenuDTO>> listUserMenus() {
        return Result.ok(menuService.listUserMenus());
    }

}

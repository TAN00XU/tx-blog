package com.tan00xu.controller;

import com.tan00xu.annotation.AccessLimit;
import com.tan00xu.service.UserAuthService;
import com.tan00xu.vo.Result;
import com.tan00xu.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * 用户账户控制器类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/09/30 14:30:56
 */
@RestController
@RequestMapping("/web")
@Tag(name = "用户账户控制")
public class UserAuthController {
    @Autowired
    private UserAuthService userAuthService;


    /**
     * 发送邮箱验证码
     *
     * @param email 用户名
     * @return {@link Result<>}
     */
    @AccessLimit(seconds = 60, maxCount = 1)
    @Operation(summary = "发送邮箱验证码")
    @Parameter(name = "email", description = "邮箱号", required = true)
    @GetMapping("/code")
    public Result<?> sendCode(@RequestParam(name = "email", required = true) String email) {
        return userAuthService.sendCode(email);
    }

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return {@link Result<>}
     */
    @Operation(summary = "用户注册")
    @PostMapping("/user/register")
    public Result<?> register(@Valid @RequestBody UserVO user) {
        userAuthService.register(user);
        return Result.ok();
    }


    /**
     * 修改用户密码
     *
     * @param user 用户信息
     * @return {@link Result<>}
     */
    @Operation(summary = "修改密码")
    @PutMapping("/user/password")
    public Result<?> updatePassword(@Valid @RequestBody UserVO user) {
        userAuthService.updatePassword(user);
        return Result.ok();
    }
}

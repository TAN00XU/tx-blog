package com.tan00xu.controller;

import com.tan00xu.service.UserInfoService;
import com.tan00xu.vo.EmailVO;
import com.tan00xu.vo.Result;
import com.tan00xu.vo.UserInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 饮梦 TAN00XU
 * @date 2022/10/28 21:33
 */
@Tag(name = "用户信息模块")
@RestController
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;


    /**
     * 更新用户信息
     *
     * @param userInfoVO 用户信息
     * @return {@link Result<>}
     */
    @Operation(summary = "更新用户信息")
    @PutMapping("/users/info")
    public Result<?> updateUserInfo(@Validated @RequestBody UserInfoVO userInfoVO) {
        userInfoService.updateUserInfo(userInfoVO);
        return Result.ok();
    }


    /**
     * 更新用户头像
     *
     * @param file 文件
     * @return 头像地址 {@link Result}<{@link String}>
     */
    @Operation(summary = "更新用户头像")
    @Parameter(name = "file", description = "用户头像", required = true)
    @PostMapping("/users/avatar")
    public Result<String> updateUserAvatar(MultipartFile file) {
        return Result.ok(userInfoService.updateUserAvatar(file));
    }

    /**
     * 绑定用户邮箱
     *
     * @param emailVO 邮箱信息
     * @return {@link Result<>}
     */
    @Operation(summary = "绑定用户邮箱")
    @PostMapping("/users/email")
    public Result<?> saveUserEmail(@Validated @RequestBody EmailVO emailVO) {
        userInfoService.saveUserEmail(emailVO);
        return Result.ok();
    }
}

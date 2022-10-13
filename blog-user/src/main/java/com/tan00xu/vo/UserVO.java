package com.tan00xu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * 用户登录类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/01 11:50:10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户账号信息")
public class UserVO {

    /**
     * 用户名
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Schema(name = "username", description = "用户名", required = true, type = "String")
    private String username;

    /**
     * 密码
     */
    @Size(min = 6, message = "密码不能少于6位")
    @NotBlank(message = "密码不能为空")
    @Schema(name = "password", description = "密码", required = true, type = "String")
    private String password;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    @Schema(name = "code", description = "邮箱验证码", required = true, type = "String")
    private String code;

}

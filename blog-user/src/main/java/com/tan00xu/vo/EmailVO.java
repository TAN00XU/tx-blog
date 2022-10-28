package com.tan00xu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


/**
 * 电子邮件VO类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/28 21:52:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "绑定邮箱")
public class EmailVO {

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Schema(description = "用户名", required = true)
    private String email;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    @Schema(description = "邮箱验证码", required = true)
    private String code;

}

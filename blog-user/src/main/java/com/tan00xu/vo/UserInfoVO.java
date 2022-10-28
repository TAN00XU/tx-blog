package com.tan00xu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


/**
 * 用户信息VO类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/28 21:46:47
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户信息对象")
public class UserInfoVO {

    /**
     * 用户昵称
     */
    @NotBlank(message = "昵称不能为空")
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 用户简介
     */
    @Schema(description = "介绍")
    private String intro;

    /**
     * 个人网站
     */
    @Schema(description = "个人网站")
    private String webSite;
}

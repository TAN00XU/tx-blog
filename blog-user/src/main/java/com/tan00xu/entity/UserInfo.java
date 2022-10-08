package com.tan00xu.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 用户信息类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/09/24 22:09:12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_user_info")
@Schema(description = "用户信息")
public class UserInfo {

    /**
     * 用户ID
     */
    @Schema(name = "id", title = "id", required = true, type = "Integer")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 邮箱号
     */
    @Schema(name = "email", title = "邮箱", required = true, type = "String")
    private String email;

    /**
     * 用户昵称
     */
    @Schema(name = "nickname", title = "昵称", required = true, type = "String")
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户简介
     */
    private String intro;

    /**
     * 个人网站
     */
    private String webSite;

    /**
     * 是否禁言
     */
    private Integer isDisable;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


}

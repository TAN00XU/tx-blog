package com.tan00xu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;


/**
 * 角色VO类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/24 14:20:06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "角色")
public class RoleVO {

    /**
     * id
     */
    @Schema(description = "用户id")
    private Integer id;

    /**
     * 标签名
     */
    @NotBlank(message = "角色名不能为空")
    @Schema(description = "角色名", required = true)
    private String roleName;

    /**
     * 标签名
     */
    @NotBlank(message = "权限标签名不能为空")
    @Schema(description = "标签名", required = true)
    private String roleLabel;

    /**
     * 资源列表
     */
    @Schema(description = "资源列表", required = true)
    private List<Integer> resourceIdList;

    /**
     * 菜单列表
     */
    @Schema(description = "菜单列表", required = true)
    private List<Integer> menuIdList;

}

package com.tan00xu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * 逻辑删除VO类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/27 14:19:22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "逻辑删除")
public class LogicDeleteVO {

    /**
     * id列表
     */
    @NotNull(message = "id不能为空")
    @Schema(description = "id列表", required = true)
    private List<Integer> idList;

    /**
     * 状态值
     */
    @NotNull(message = "状态值不能为空")
    @Schema(description = "删除的状态")
    private Integer isDelete;

}

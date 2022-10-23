package com.tan00xu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 查询条件VO类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/01 12:29:22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "查询条件")
public class ConditionVO {

    /**
     * 页码
     */
    @Schema(name = "current", description = "页码", type = "Long")
    private Long current;

    /**
     * 条数
     */
    @Schema(name = "size", description = "条数", type = "Long")
    private Long size;

    /**
     * 搜索内容 关键字
     */
    @Schema(name = "keywords", description = "搜索内容", type = "String")
    private String keywords;

    /**
     * 分类id
     */
    @Schema(name = "categoryId", description = "分类id", type = "Integer")
    private Integer categoryId;

    /**
     * 标签id
     */
    @Schema(name = "tagId", description = "标签id", type = "Integer")
    private Integer tagId;

    /**
     * 相册id
     */
    @Schema(name = "albumId", description = "相册id", type = "Integer")
    private Integer albumId;

    /**
     * 登录类型
     */
    @Schema(name = "type", description = "登录类型", type = "Integer")
    private Integer loginType;

    /**
     * 类型
     */
    @Schema(name = "type", description = "类型", type = "Integer")
    private Integer type;

    /**
     * 状态
     */
    @Schema(name = "status", description = "状态", type = "Integer")
    private Integer status;

    /**
     * 开始时间
     */
    @Schema(name = "startTime", description = "开始时间", type = "LocalDateTime")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Schema(name = "endTime", description = "结束时间", type = "LocalDateTime")
    private LocalDateTime endTime;

    /**
     * 是否删除
     */
    @Schema(name = "isDelete", description = "是否删除", type = "Integer")
    private Integer isDelete;

    /**
     * 是否审核
     */
    @Schema(name = "isReview", description = "是否审核", type = "Integer")
    private Integer isReview;

}

package com.tan00xu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * 评论VO类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/17 21:53:23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "评论")
public class CommentVO {

    /**
     * 回复用户id
     */
    @Schema(description = "回复用户id")
    private Integer replyUserId;

    /**
     * 评论主题id
     */
    @Schema(description = "主题id")
    private Integer topicId;

    /**
     * 评论内容
     */
    @NotBlank(message = "评论内容不能为空")
    @Schema(description = "评论内容", required = true)
    private String commentContent;

    /**
     * 父评论id
     */
    @Schema(description = "评论父id")
    private Integer parentId;

    /**
     * 类型
     */
    @NotNull(message = "评论类型不能为空")
    @Schema(description = "评论类型")
    private Integer type;

}

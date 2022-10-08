package com.tan00xu.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 网站配置信息
 *
 * @author yezhiqiu
 * @date 2021/08/09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "网站配置")
public class WebsiteConfigVO {

    /**
     * 网站头像
     */
    @Schema(description = "网站头像", required = true)
    private String websiteAvatar;

    /**
     * 网站名称
     */
    @Schema(description = "网站名称", required = true)
    private String websiteName;

    /**
     * 网站作者
     */
    @Schema(description = "网站作者", required = true)
    private String websiteAuthor;

    /**
     * 网站介绍
     */
    @Schema(description = "网站介绍", required = true)
    private String websiteIntro;

    /**
     * 网站公告
     */
    @Schema(description = "网站公告", required = true)
    private String websiteNotice;

    /**
     * 网站创建时间
     */
    @Schema(description = "网站创建时间", required = true)
    private String websiteCreateTime;

    /**
     * 网站备案号
     */
    @Schema(description = "网站备案号", required = true)
    private String websiteRecordNo;

    /**
     * 社交登录列表
     */
    @Schema(description = "社交登录列表", required = true)
    private List<String> socialLoginList;

    /**
     * 社交url列表
     */
    @Schema(description = "社交url列表", required = true)
    private List<String> socialUrlList;

    /**
     * qq
     */
    @Schema(description = "qq", required = true)
    private String qq;

    /**
     * github
     */
    @Schema(description = "github", required = true)
    private String github;

    /**
     * gitee
     */
    @Schema(description = "gitee", required = true)
    private String gitee;

    /**
     * 游客头像
     */
    @Schema(description = "游客头像", required = true)
    private String touristAvatar;

    /**
     * 用户头像
     */
    @Schema(description = "用户头像", required = true)
    private String userAvatar;

    /**
     * 是否评论审核
     */
    @Schema(description = "是否评论审核", required = true)
    private Integer isCommentReview;

    /**
     * 是否留言审核
     */
    @Schema(description = "是否留言审核", required = true)
    private Integer isMessageReview;

    /**
     * 是否邮箱通知
     */
    @Schema(description = "是否邮箱通知", required = true)
    private Integer isEmailNotice;

    /**
     * 是否打赏
     */
    @Schema(description = "是否打赏", required = true)
    private Integer isReward;

    /**
     * 微信二维码
     */
    @Schema(description = "微信二维码", required = true)
    private String weiXinQRCode;

    /**
     * 支付宝二维码
     */
    @Schema(description = "支付宝二维码", required = true)
    private String alipayQRCode;

    /**
     * 文章封面
     */
    @Schema(description = "文章封面", required = true)
    private String articleCover;

    /**
     * 是否开启聊天室
     */
    @Schema(description = "是否打赏", required = true)
    private Integer isChatRoom;

    /**
     * websocket地址
     */
    @Schema(description = "websocket地址", required = true)
    private String websocketUrl;

    /**
     * 是否开启音乐
     */
    @Schema(description = "是否开启音乐", required = true)
    private Integer isMusicPlayer;

}

package com.tan00xu.util;

import com.github.houbb.sensitive.word.bs.SensitiveWordBs;


/**
 * HTML工具类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/13 10:10:41
 */
public class HTMLUtils {

    /**
     * sensitive-word配置
     * ignoreCase	忽略大小写
     * ignoreWidth	忽略半角圆角
     * ignoreNumStyle	忽略数字的写法
     * ignoreChineseStyle	忽略中文的书写格式
     * ignoreEnglishStyle	忽略英文的书写格式
     * ignoreRepeat	忽略重复词
     * enableNumCheck	是否启用数字检测。默认连续 8 位数字认为是敏感词
     * enableEmailCheck	是有启用邮箱检测
     * enableUrlCheck	是否启用链接检测
     */
    private static final SensitiveWordBs WORD_BS =
            SensitiveWordBs
                    .newInstance()
                    .ignoreCase(true)
                    .ignoreWidth(true)
                    .ignoreNumStyle(true)
                    .ignoreChineseStyle(true)
                    .ignoreEnglishStyle(true)
                    .ignoreRepeat(true)
                    .enableNumCheck(false)
                    .enableEmailCheck(false)
                    .enableUrlCheck(false)
                    .init();


    /**
     * 删除HTML标签
     *
     * @param source 需要进行剔除HTML的文本
     * @return 过滤后的内容
     */
    public static String filter(String source) {
        // 敏感词过滤
        source = WORD_BS.replace(source);
        // 保留图片标签
        source = source
                .replaceAll("(?!<(img).*?>)<.*?>", "")
                .replaceAll("(onload(.*?)=)", "")
                .replaceAll("(onerror(.*?)=)", "");
        return deleteHMTLTag(source);
    }

    /**
     * 删除 转义字符、script标签、style标签
     *
     * @param source 文本
     * @return 过滤后的文本
     */
    public static String deleteHMTLTag(String source) {
        // 删除转义字符
        source = source.replaceAll("&.{2,6}?;", "");
        // 删除script标签
        source = source.replaceAll("<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>", "");
        // 删除style标签
        source = source.replaceAll("<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>", "");
        return source;
    }

}

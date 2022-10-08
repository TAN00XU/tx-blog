package com.tan00xu.util;


import java.util.function.Consumer;

/**
 * 用于控制命令行输出字体颜色
 *
 * @author 饮梦 TAN00XU
 * @date 2022/09/30 23:29:44
 */
public class CmdOutputInformationUtils {

    /**
     * 操作对象
     *
     * @param obj      obj
     * @param consumer 消费者
     */
    public static void operatorObject(Object obj, Consumer<Object> consumer) {
        consumer.accept(obj);
    }

    /**
     * debug 下划线 蓝色字
     *
     * @param debug 待输出的字符串
     */
    public static void debug(Object debug) {
        System.out.println("\033[4;34m" + debug + "\033[0m");
    }

    /**
     * info 绿色字
     *
     * @param info 待输出的字符串
     */
    public static void info(Object info) {
        System.out.println("\033[32m" + info + "\033[0m");
    }

    /**
     * warn 黄色字
     *
     * @param warn 待输出的字符串
     */
    public static void warn(Object warn) {
        System.out.println("\033[33m" + warn + "\033[0m");
    }

    /**
     * error 反显 红色字 黑底
     *
     * @param error 待输出的字符串
     */
    public static void error(Object error) {
        System.out.println("\033[7;31;40m" + error + "\033[0m");
    }

}

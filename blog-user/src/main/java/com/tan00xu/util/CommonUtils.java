package com.tan00xu.util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 公共工具类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/01 13:28:28
 */
public class CommonUtils {


    /**
     * 电子邮件正则表达式
     */
    private static final String EMAIL_REGEX = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
    /**
     * 电子邮件正则表达式的模式 编译正则表达式
     */
    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * 检测邮箱是否合法
     *
     * @param email 邮箱号
     * @return 合法状态
     */
    public static boolean checkEmail(String email) {
        //匹配器
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    /**
     * 生成6位随机验证码
     *
     * @return 验证码
     */
    public static String getRandomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    /**
     * 获取括号内容
     *
     * @param str str
     * @return {@link String} 括号内容
     */
    public static String getBracketsContent(String str) {
        return str.substring(str.indexOf("(") + 1, str.indexOf(")"));
    }


    /**
     * 转换List
     *
     * @param obj   obj
     * @param clazz clazz
     * @return {@link List<T>}
     */
    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return result;
    }

    /**
     * 转换set
     *
     * @param obj   obj
     * @param clazz clazz
     * @return {@link Set<T>}
     */
    public static <T> Set<T> castSet(Object obj, Class<T> clazz) {
        Set<T> result = new HashSet<>();
        if (obj instanceof Set<?>) {
            for (Object o : (Set<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return result;
    }


}

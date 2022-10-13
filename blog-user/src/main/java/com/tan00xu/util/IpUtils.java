package com.tan00xu.util;

import com.alibaba.fastjson.JSON;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;


/**
 * ip工具类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/10 23:57:36
 */
@SuppressWarnings("all")
public class IpUtils {

    /**
     * 获取用户ip地址
     *
     * @param request 请求
     * @return ip地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                /*防止本地访问出现ipv6*/
                ipAddress = ipAddress.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ipAddress;
                if ("127.0.0.1".equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) {
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }

    /**
     * 解析ip地址，获取地理信息
     *
     * @param ipAddress ip地址
     * @return 解析后的ip地址
     */
    public static String getIpSource(String ipAddress) {
        try {
            URL url = new URL("http://opendata.baidu.com/api.php?query=" + ipAddress + "&co=&resource_id=6006&oe=utf8");
            //获取HttpURRLConnection对象
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //获取输入流
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuffer result = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            bufferedReader.close();
            Map map = JSON.parseObject(result.toString(), Map.class);
            List<Map<String, String>> data = (List) map.get("data");
            return data.get(0).get("location");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取访问设备
     *
     * @param request 请求
     * @return {@link UserAgent} 访问设备
     */
    public static UserAgent getUserAgent(HttpServletRequest request) {
        return UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
    }

}

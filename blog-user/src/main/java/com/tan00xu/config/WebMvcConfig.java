package com.tan00xu.config;


import com.tan00xu.handler.PagingHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * web mvc配置类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/04 17:29:30
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry
                //允许跨域的路径
                .addMapping("/**")
                //是否允许cookie
                .allowCredentials(true)
                //设置允许的header属性
                .allowedHeaders("*")
                //设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                //设置允许的请求方法
//                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .allowedMethods("*")
                //跨域允许时间
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PagingHandlerInterceptor());
//        registry.addInterceptor(getWebSecurityHandler());
    }

}

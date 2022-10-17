package com.tan00xu.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenApi配置类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/09/29 09:43:39
 */
@Configuration
public class OpenApiConfig {


    /**
     * Web api
     *
     * @return {@link GroupedOpenApi}
     */
    @Bean
    public GroupedOpenApi webApi() {
        String[] paths = {"/**"};
        String[] packagedToMatch = {"com.tan00xu.controller"};
        return GroupedOpenApi.builder()
                .group("Web模块")
                .pathsToMatch(paths)
                .packagesToScan(packagedToMatch)
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("饮梦的博客api文档")
                        .description("springboot+vue项目")
                        .contact(new Contact()
                                .name("饮梦")
                                .url("https://gitee.com/TAN00XU")
                                .email("2507320149@qq.com"))
                        .version("1.0")
                        .termsOfService("http://doc.xiaominfo.com")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://doc.xiaominfo.com")
                        )

                );
    }


}

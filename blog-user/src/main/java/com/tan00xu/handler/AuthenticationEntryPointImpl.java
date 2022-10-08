package com.tan00xu.handler;

import com.alibaba.fastjson2.JSON;
import com.tan00xu.enums.StatusCodeEnum;
import com.tan00xu.vo.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.tan00xu.constant.CommonConst.APPLICATION_JSON;

/**
 * 用户未登录处理类
 * 身份验证条实现类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/01 11:28:53
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setContentType(APPLICATION_JSON);
        httpServletResponse.getWriter().write(JSON.toJSONString(Result.fail(StatusCodeEnum.NO_LOGIN)));
    }

}
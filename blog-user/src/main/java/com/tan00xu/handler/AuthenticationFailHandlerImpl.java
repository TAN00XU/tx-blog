package com.tan00xu.handler;

import com.alibaba.fastjson.JSON;
import com.tan00xu.vo.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.tan00xu.constant.CommonConst.APPLICATION_JSON;


/**
 * 登录失败处理类
 * 身份验证失败处理程序实现类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/01 12:05:17
 */
@Component
public class AuthenticationFailHandlerImpl implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setContentType(APPLICATION_JSON);
        httpServletResponse.getWriter().write(JSON.toJSONString(Result.fail(e.getMessage())));
    }

}

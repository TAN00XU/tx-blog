package com.tan00xu.handler;

import com.alibaba.fastjson.JSON;
import com.tan00xu.vo.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.tan00xu.constant.CommonConst.APPLICATION_JSON;


/**
 * 用户退出登录处理类
 * 注销成功处理程序实现类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/01 12:07:10
 */
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        httpServletResponse.setContentType(APPLICATION_JSON);
        httpServletResponse.getWriter().write(JSON.toJSONString(Result.ok()));
    }

}

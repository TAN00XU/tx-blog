package com.tan00xu.handler;

import com.alibaba.fastjson2.JSON;
import com.tan00xu.vo.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.tan00xu.constant.CommonConst.APPLICATION_JSON;

/**
 * 用户权限不足处理类
 * 访问被拒绝处理程序实现类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/01 11:31:23
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
        httpServletResponse.setContentType(APPLICATION_JSON);
        httpServletResponse.getWriter().write(JSON.toJSONString(Result.fail("权限不足")));
    }

}

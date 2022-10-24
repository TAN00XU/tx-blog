package com.tan00xu.handler;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tan00xu.util.PagingUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.tan00xu.constant.CommonConst.*;


/**
 * 分页处理程序拦截器类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/11 15:02:34
 */
public class PagingHandlerInterceptor implements HandlerInterceptor {

    /**
     * 前处理 在Controller请求执行之前，
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理程序
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String currentPage = request.getParameter(CURRENT);
//        CmdOutputInformationUtils.info("handler.PagingHandlerInterceptor=>\n前端的页码=>" + currentPage);
        //非空？值：orElse(非空？值：new Optional())
        String pageSize = Optional.ofNullable(request.getParameter(SIZE)).orElse(DEFAULT_SIZE);
        long size = Long.parseLong(pageSize);
        if (size > MAX_SIZE || size < MIN_SIZE) {
            size = Long.parseLong(DEFAULT_SIZE);
        }
//        CmdOutputInformationUtils.info("handler.PagingHandlerInterceptor=>\n前端的条数=>" + currentPage);
        if (StringUtils.hasText(currentPage)) {
            PagingUtils.setCurrentPage(new Page<>(Long.parseLong(currentPage), size));
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        PagingUtils.remove();
    }

}
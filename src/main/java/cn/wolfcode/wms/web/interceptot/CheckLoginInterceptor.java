package cn.wolfcode.wms.web.interceptot;

import cn.wolfcode.wms.util.UserContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckLoginInterceptor extends HandlerInterceptorAdapter{

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        Object emp = UserContext.getCurrentUser();
        if (emp == null) {
            //没有登录
            response.sendRedirect("/login.html");
            return false;
        }
        //已经登录
        return true;
    }
}

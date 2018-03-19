package cn.wolfcode.wms.web.interceptot;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.util.PermissionUtil;
import cn.wolfcode.wms.util.RequiredPermission;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

//权限拦截器
public class SecurityInterceptor extends HandlerInterceptorAdapter{
    @Override
    public boolean preHandle(HttpServletRequest request, 
                             HttpServletResponse response, Object handler) throws Exception {
        Employee emp = UserContext.getCurrentUser();
        //1:判断当前用户是否是超级管理员
        if(emp.isAdmin()){
            return true;
        }
        //2:当前访问的方法不需要权限放行
        HandlerMethod method = (HandlerMethod) handler;
        Method m = method.getMethod();
        if (m.isAnnotationPresent(RequiredPermission.class)){
            return true;
        }
        //3:被访问的方法需要权限,当前用户拥有该权限,则放行
        String exp = PermissionUtil.buildExpression(m);
        List<String> exps = (List<String>) request.getSession().getAttribute("EXPS_IN_SESSION");
        if (exps.contains(exp)){
            return true;
        }
        throw new SecurityException();//没有权限
    }
}

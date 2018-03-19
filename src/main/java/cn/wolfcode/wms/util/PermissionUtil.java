package cn.wolfcode.wms.util;

import java.lang.reflect.Method;

public abstract class PermissionUtil {

    public static String buildExpression(Method method) {
        Class clz = method.getDeclaringClass();
        return clz.getName()+":"+method.getName();
    }
}

package com.biubiu.rpc.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yule.zhang
 * @date 2019/2/23 0:01
 * @email zhangyule1993@sina.com
 * @description WEB工具类
 * 获取当前线程中的上下文
 * 获取当前线程中的request请求
 * 获取当前线程中的请求的response
 */
public class Web {
    public Web() {
    }

    public static HttpServletRequest getCurrentRequest() throws IllegalStateException {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attrs == null) {
            throw new IllegalStateException("当前线程中不存在 Request 上下文");
        } else {
            return attrs.getRequest();
        }
    }

    public static HttpServletResponse getCurrentResponse() throws IllegalStateException {
        ServletRequestAttributes attrs = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if(attrs == null) {
            throw new IllegalStateException("当前线程中不存在 Request 上下文");
        } else {
            return attrs.getResponse();
        }
    }

    public static String getRequestParameter(String name) {
        try {
            HttpServletRequest request = getCurrentRequest();
            return request.getParameter(name);
        } catch (IllegalStateException e) {
            return null;
        }
    }

    public static String getRequestParameter(HttpServletRequest request, String name) {
        return request == null ? null : request.getParameter(name);
    }
}

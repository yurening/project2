/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/19
 * Time: 16:50
 */
package com.cskaoyan.shiro;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class AuthSessionManager extends DefaultWebSessionManager {

    @Override
    protected Serializable getSessionId(ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String adminHeader = request.getHeader("X-Litemall-Admin-Token");
        String WxHeader = request.getHeader("X-cskaoyanmall-Admin-Token");
        if (adminHeader != null && !"".equals(adminHeader)) {
            return adminHeader;
        }else if (WxHeader != null && !"".equals(WxHeader)) {
            return WxHeader;
        }else {
            return super.getSessionId(servletRequest, servletResponse);
        }
    }














}

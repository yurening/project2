/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/20
 * Time: 0:51
 */
package com.cskaoyan.filter;

import com.alibaba.druid.util.StringUtils;
import com.cskaoyan.bean.BaseReqVo;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class FormAuthFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
       Subject subject = getSubject(request, response);
       if (subject.getPrincipal() == null) {
           return false;
       }
       if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                return true;

            }
        } else {
            WebUtils.toHttp(response).setContentType("application/json; charset=utf-8");
            WebUtils.toHttp(response).getWriter().print(BaseReqVo.fail(507,"权限不足,请联系超级管理员"));
            return false;
        }
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        boolean allowed = super.isAccessAllowed(request, response, mappedValue);
        if (!allowed) {
            String method = WebUtils.toHttp(request).getMethod();
            if (StringUtils.equalsIgnoreCase("OPTIONS", method)) {
                return true;
            }
        }
        return allowed;
    }
}

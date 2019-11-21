/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/20
 * Time: 16:33
 */
package com.cskaoyan.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandle {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandle.class);

    //    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public String handleShiroException(Exception ex) {
        return "redirect:/error/403";
    }

    //    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public String AuthorizationException(Exception ex) {
        return "redirect:/error/401";
    }
}

/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/20
 * Time: 16:33
 */
package com.cskaoyan.exception;

import com.cskaoyan.bean.BaseReqVo;
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
    public BaseReqVo handleShiroException(Exception ex) {
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(506);
        baseReqVo.setErrmsg("无操作权限");
        return baseReqVo;
    }

    //    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public String AuthorizationException(Exception ex) {
        return "redirect:/error/401";
    }

    /*@ExceptionHandler(Exception.class)
    public BaseReqVo exception(Exception ex){
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(507);
        baseReqVo.setErrmsg("网络出问题喽");
        return baseReqVo;
    }*/

}

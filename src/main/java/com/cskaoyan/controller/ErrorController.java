package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseReqVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {

    @RequestMapping("error/401")
    public BaseReqVo error1(){
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(502);
        baseReqVo.setErrmsg("你并没有登录");
        return baseReqVo;
    }

    @RequestMapping("error/403")
    public BaseReqVo error2(){
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(502);
        baseReqVo.setErrmsg("你并没有权限");
        return baseReqVo;
    }

}

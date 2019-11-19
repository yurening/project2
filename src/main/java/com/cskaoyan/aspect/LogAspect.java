package com.cskaoyan.aspect;

import com.cskaoyan.bean.systemBean.Admin;
import com.cskaoyan.bean.systemBean.Log;
import com.cskaoyan.service.SystemService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

@Component
@Aspect
public class LogAspect {

    Log log;

    String admin;

    @Autowired
    SystemService systemService;

    @Pointcut("execution(* com.cskaoyan.controller..*(..))")
    public void logPointCut(){

    }

    @Autowired
    HttpServletRequest request;

    @Before("logPointCut()")
    public void mybefore(JoinPoint joinPoint){
        log = new Log();
        request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        if (request.getRequestURI().contains("auth/info")){
            admin = request.getParameter("token");
        }
        if (request.getRequestURI().contains("auth/login")){

        }
        String ip = request.getRemoteAddr();
        log.setIp(ip);
        //String admin = (String) request.getSession().getAttribute("admin");
        log.setStatus(true);
        log.setAddTime(new Date());
        log.setUpdateTime(new Date());
        log.setDeleted(false);
        if (admin != null) {
            log.setAdmin(admin);
        }
        String requestURI = request.getRequestURI();
        if (requestURI.contains("login")){
            log.setAction("登录");
            log.setType(1);
        } else if(requestURI.contains("admin/create")){
            log.setAction("创建管理员");
            log.setType(1);
        } else if(requestURI.contains("admin/update")){
            log.setAction("编辑管理员");
            log.setType(1);
        } else if(requestURI.contains("admin/delete")){
            log.setAction("删除管理员");
            log.setType(1);
        } else if(requestURI.contains("role/create")){
            log.setAction("创建管理权限");
            log.setType(1);
        } else if(requestURI.contains("role/update")){
            log.setAction("编辑管理权限");
            log.setType(1);
        } else if(requestURI.contains("role/delete")){
            log.setAction("删除管理权限");
            log.setType(1);
        }else if(requestURI.contains("create")){
            log.setAction("创建");
            log.setType(0);
        } else if(requestURI.contains("logout")){
            log.setAction("退出");
            log.setType(1);
        } else if(requestURI.contains("update")){
            log.setAction("编辑");
            log.setType(0);
        } else if(requestURI.contains("delete")){
            log.setAction("删除");
            log.setType(0);
        } else if(requestURI.contains("read")){
            log.setAction("查看详情");
            log.setType(0);
        } else if(requestURI.contains("config/order")){
            log.setAction("订单配置");
            log.setType(2);
        } else if(requestURI.contains("config/express")){
            log.setAction("运费配置");
            log.setType(2);
        } else if(requestURI.contains("config/mall")){
            log.setAction("商场配置");
            log.setType(1);
        } else if(requestURI.contains("config/wx")){
            log.setAction("小程序配置");
            log.setType(1);
        } else {
            log.setAction("登录");
            log.setType(1);
        }
    }


    public void myafter(){

    }

    public Object myaround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        return proceed;
    }

    @AfterReturning("logPointCut()")
    public void myafterReturning(){
        if (request.getRequestURI().contains("auth/info")){
            admin = request.getParameter("token");
        }
        if (!request.getRequestURI().contains("list")) {
            if (log.getAdmin() != null) {
                if (!request.getMethod().equals("OPTIONS")) {
                    systemService.insertLog(log);
                }
            }
        }
    }

    @AfterThrowing("logPointCut()")
    public void myafterThrowing() {
        log.setStatus(false);
        String requestURI = request.getRequestURI();
        if (requestURI != null) {
            if (requestURI.contains("login")) {
                log.setComment("帐号或密码错误");
                log.setResult("登录失败");
            } else if (requestURI.contains("create")) {
                log.setResult("创建失败");
                log.setComment("参数错误");
            } else if (requestURI.contains("update")) {
                log.setResult("修改失败");
                log.setComment("参数错误");
            } else if (requestURI.contains("delete")) {
                log.setResult("删除失败");
                log.setComment("未知异常");
            }
        }
        if (!request.getRequestURI().contains("list")) {
            if (log.getAdmin() != null) {
                systemService.insertLog(log);
            }
        }
    }
}

package com.cskaoyan.wx_controller;


import com.cskaoyan.bean.BaseReqVo;
import com.cskaoyan.bean.user.Collect;
import com.cskaoyan.bean.user.User;
import com.cskaoyan.needdelete.BaseRespVo;
import com.cskaoyan.needdelete.UserTokenManager;
import com.cskaoyan.service.CollectService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("wx")
@ResponseBody
public class CollectController_wx {

    @Autowired
    CollectService collectService;

    @RequestMapping("collect/list")
    public Object collectList(Integer type, Integer page, Integer size, HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Integer userId = user.getId();
        HashMap<String,Object> collectList = collectService.collectList(type, page, size,userId);
        return BaseRespVo.ok(collectList);
    }

    @RequestMapping("collect/addordelete")
    public Object collectAddOrDelete(@RequestBody Collect collect,HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Integer userId = user.getId();
        HashMap<String,Object> hashMap = collectService.addOrDelete(collect,userId);
        return BaseRespVo.ok(hashMap);
    }
}

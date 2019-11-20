package com.cskaoyan.wx_controller;


import com.cskaoyan.bean.BaseReqVo;
import com.cskaoyan.bean.user.Collect;
import com.cskaoyan.needdelete.BaseRespVo;
import com.cskaoyan.needdelete.UserTokenManager;
import com.cskaoyan.service.CollectService;
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
        //前端写了一个token放在请求头中
        //*************************
        //获得请求头
        String tokenKey = request.getHeader("5cn9hnzh0lgki9n69bxjegsafqzocpq2");
        Integer userId = UserTokenManager.getUserId(tokenKey);

        //通过请求头获得userId，进而可以获得一切关于user的信息
        //**************************
        if (userId == null) {
            return BaseRespVo.fail();
        }
        HashMap<String,Object> collectList = collectService.collectList(type, page, size,userId);
        return BaseRespVo.ok(collectList);
    }

    @RequestMapping("collect/addordelete")
    public Object collectAddOrDelete(@RequestBody Collect collect,HttpServletRequest request){
        //前端写了一个token放在请求头中
        //*************************
        //获得请求头
        String tokenKey = request.getHeader("5cn9hnzh0lgki9n69bxjegsafqzocpq2");
        Integer userId = UserTokenManager.getUserId(tokenKey);

        //通过请求头获得userId，进而可以获得一切关于user的信息
        //**************************
        if (userId == null) {
            return BaseRespVo.fail();
        }
        HashMap<String,Object> hashMap = collectService.addOrDelete(collect,userId);
        return BaseRespVo.ok(hashMap);
    }
}

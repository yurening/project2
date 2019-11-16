package com.cskaoyan.controller;

import com.cskaoyan.bean.goods.InfoData;
import com.cskaoyan.bean.goods.ResponseType;
import com.cskaoyan.bean.goods.UserTest;
import com.cskaoyan.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
@ResponseBody
@RequestMapping("admin")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    /*@RequestMapping("auth/login")
    public ResponseType login(@RequestBody UserTest user, HttpServletRequest request){
        String sessionId = request.getSession().getId();
        ResponseType responseType = new ResponseType();
        responseType.setErrno(0);
        responseType.setErrmsg("成功");
        responseType.setData(sessionId);
        return responseType;
    }
    @RequestMapping("auth/info")
    public ResponseType info(String token){
        ResponseType baseReqVo = new ResponseType();
        InfoData data = new InfoData();
        data.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        data.setName("songge");
        ArrayList<String> perms = new ArrayList<>();
        perms.add("*");
        data.setPerms(perms);
        ArrayList<String> roles = new ArrayList<>();
        roles.add("超级管理员");
        data.setRoles(roles);

        baseReqVo.setData(data);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }*/

    @RequestMapping("goods/list")
    public ResponseType showGoods(Integer page,Integer limit,
                          String order,String sort,
                          String goodsSn,String name){
        ResponseType allGoods = goodsService.getAllGoods(page, limit, order, sort, goodsSn, name);
        return allGoods;
    }


}

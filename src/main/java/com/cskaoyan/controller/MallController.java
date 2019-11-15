package com.cskaoyan.controller;

import java.util.ArrayList;
import java.util.List;


import com.cskaoyan.bean.mall.BaseReqVo;
import com.cskaoyan.bean.mall.InfoData;
import com.cskaoyan.bean.mall.Login;
import com.cskaoyan.bean.mall.region.MallRegionI;
import com.cskaoyan.service.MallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MallController {
    @Autowired
    MallService mallService;

    @RequestMapping("admin/auth/login")
    public BaseReqVo login(@RequestBody Login login){
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setData("1195ea17-fc25-48a3-8d06-65c3b0f055a7");
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/auth/info")
    public BaseReqVo info(String token){
        BaseReqVo baseReqVo = new BaseReqVo();
        InfoData data = new InfoData();
        data.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        data.setName("admin123");
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
    }

    @RequestMapping("admin/region/list")
    public BaseReqVo getRegionList(){
        List<MallRegionI> allRegions= mallService.getAllRegion();
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setData(allRegions);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

}

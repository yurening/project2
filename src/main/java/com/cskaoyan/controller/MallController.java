package com.cskaoyan.controller;

import java.util.List;


import com.cskaoyan.bean.mall.BaseReqVo;
import com.cskaoyan.bean.mall.brand.AllBrandsInfo;
import com.cskaoyan.bean.mall.brand.CreatBrand;
import com.cskaoyan.bean.mall.brand.MallBrand;
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

    /*@RequestMapping("admin/auth/login")
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
    }*/

    @RequestMapping("admin/region/list")
    public BaseReqVo getRegionList(){
        List<MallRegionI> allRegions= mallService.getAllRegion();
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setData(allRegions);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/brand/list")
    public BaseReqVo getBrandList(Integer page,Integer limit,String sort,String order,String name,Integer id){
        BaseReqVo baseReqVo = new BaseReqVo();
        AllBrandsInfo allBrandsInfo=mallService.getAllBrandByplso(page,limit,sort,order,name,id);
        baseReqVo.setData(allBrandsInfo);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }


    @RequestMapping("admin/brand/create")
    public BaseReqVo creatBrand(@RequestBody CreatBrand creatBrand){
        BaseReqVo baseReqVo = new BaseReqVo();
        MallBrand newBrand= mallService.creatBrand(creatBrand);
        baseReqVo.setData(newBrand);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/brand/update")
    public BaseReqVo updateBrand(@RequestBody MallBrand mallBrand){
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setData(mallBrand);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/brand/delete")
    public BaseReqVo deleteBrand(@RequestBody MallBrand mallBrand){
        BaseReqVo baseReqVo = new BaseReqVo();
        mallService.deleteBrand(mallBrand);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

   /* @RequestMapping("admin/order/list")
    public BaseReqVo getOrderList(Integer page,Integer limit,String sort,String order,Integer orderSn,Integer userId,Integer[] orderStatusArray){
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setData();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }*/
}

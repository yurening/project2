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

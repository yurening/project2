package com.cskaoyan.wx_controller;

import com.cskaoyan.bean.goods.ResponseType;
import com.cskaoyan.service.BrandService;
import com.cskaoyan.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("wx")
@ResponseBody
public class BrandController_wx {
    @Autowired
    BrandService brandService;

    @RequestMapping("brand/list")
    public ResponseType brandList(Integer page, Integer size){
        ResponseType brandList = brandService.getBrandList(page, size);
        return brandList;
    }

    @RequestMapping("brand/detail")
    public ResponseType brandDetail(Integer id){
        ResponseType brandById = brandService.getBrandById(id);
        return brandById;
    }
}

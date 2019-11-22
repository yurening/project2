package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseReqVo;
import com.cskaoyan.bean.goods.*;
import com.cskaoyan.service.GoodsService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("admin")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @RequestMapping("goods/list")
    @RequiresPermissions(value={"admin:goods:list"})
    public ResponseType showGoods(Integer page,Integer limit,
                          String order,String sort,
                          String goodsSn,String name){
        ResponseType allGoods = goodsService.getAllGoods(page, limit, order, sort, goodsSn, name);
        return allGoods;
    }

    @RequestMapping("goods/catAndBrand")
    @RequiresPermissions(value={"admin:goods:read","admin:goods:update","admin:goods:create","admin:goods:list"},logical = Logical.OR)
    public ResponseType catAndBrand(){
        //获取分类及其子类
        List<CategoryResp> categoryList = goodsService.getCategory();
        //获取品牌分类
        List<CategoryResp> brand = goodsService.getBrand();
        Map map = new HashMap();
        map.put("categoryList",categoryList);
        map.put("brandList",brand);
        ResponseType<Object> responseType = new ResponseType();
        responseType.setErrno(0);
        responseType.setErrmsg("成功");
        responseType.setData(map);

        return responseType;
    }

    @RequestMapping("goods/create")
    @RequiresPermissions(value={"admin:goods:create"})
    public ResponseType createGoods(@RequestBody CreateGoods createGoods) throws Exception{
        ResponseType goods = goodsService.createGoods(createGoods);
        return goods;
    }

    @RequestMapping("goods/detail")
    @RequiresPermissions(value={"admin:goods:read"})
    public ResponseType goodsDetail(/*@RequestBody */Integer id){
        CreateGoods goodsDetail = goodsService.getGoodsDetail(id);
        ResponseType responseType = new ResponseType();
        responseType.setErrno(0);
        responseType.setErrmsg("成功");
        responseType.setData(goodsDetail);
        return responseType;
    }

    @RequestMapping("goods/update")
    @RequiresPermissions(value={"admin:goods:update"})
    public ResponseType updateGoods(@RequestBody CreateGoods createGoods){
        int i = goodsService.updateGoods(createGoods);
        ResponseType responseType = new ResponseType();
        responseType.setErrmsg("成功");
        responseType.setErrno(0);
        return responseType;
    }

    @RequestMapping("goods/delete")
    @RequiresPermissions(value={"admin:goods:delete"})
    public ResponseType deleteLogic(@RequestBody Goods goods){
        goodsService.deleteGoods(goods);
        ResponseType responseType = new ResponseType();
        responseType.setErrmsg("成功");
        responseType.setErrno(0);
        return responseType;
    }
}

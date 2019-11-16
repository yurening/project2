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

    @RequestMapping("goods/list")
    public ResponseType showGoods(Integer page,Integer limit,
                          String order,String sort,
                          String goodsSn,String name){
        ResponseType allGoods = goodsService.getAllGoods(page, limit, order, sort, goodsSn, name);
        return allGoods;
    }


}

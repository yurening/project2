package com.cskaoyan.wx_controller;

import com.cskaoyan.bean.goods.Brand;
import com.cskaoyan.bean.goods.ResponseType;
import com.cskaoyan.mapper.BrandMapper;
import com.cskaoyan.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("wx")
@ResponseBody
public class GoodsController_wx {
    @Autowired
    GoodsService goodsService;

    /**
     * 查找所有商品的总数
     * @return
     */
    @RequestMapping("goods/count")
    public ResponseType count(){
        Long goodsCount = goodsService.getGoodsCount();
        Map<String,Long> map = new HashMap<>();
        map.put("goodsCount",goodsCount);
        ResponseType responseType = new ResponseType();
        responseType.setErrmsg("成功");
        responseType.setData(map);
        responseType.setErrno(0);
        return responseType;
    }

    @RequestMapping("goods/list")
    public ResponseType goodsList(Integer brandId,Integer page,Integer size,Integer categoryId,
                                  String keyword,String sort,String desc){
        if (brandId != null) {
            ResponseType goodsByBrandId = goodsService.getGoodsByBrandId(brandId, page, size);
            return goodsByBrandId;
        }
        if (categoryId != null){
            ResponseType goodsByCategory = goodsService.getGoodsByCategory(categoryId);
            return goodsByCategory;
        }
        if(keyword != null){

        }
        return null;
    }

    @RequestMapping("goods/detail")
    public ResponseType goodsDetail(Integer id){
        ResponseType goodsAndAllById = goodsService.getGoodsAndAllById(id);
        return goodsAndAllById;
    }

    @RequestMapping("goods/category")
    public ResponseType goodsCategory(Integer id){
        ResponseType categoryByParent = goodsService.getCategoryByParent(id);
        return categoryByParent;
    }

    @RequestMapping("goods/related")
    public ResponseType goodsRelated(Integer id){
        ResponseType relativeGoods = goodsService.getRelativeGoods(id);
        return relativeGoods;
    }

}

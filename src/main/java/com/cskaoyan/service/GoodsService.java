package com.cskaoyan.service;

import com.cskaoyan.bean.goods.CategoryResp;
import com.cskaoyan.bean.goods.CreateGoods;
import com.cskaoyan.bean.goods.Goods;
import com.cskaoyan.bean.goods.ResponseType;
import com.cskaoyan.bean.wx_index.HomeIndex;

import java.util.List;

public interface GoodsService {
    ResponseType getAllGoods(Integer page, Integer limit,
                             String order, String sort,
                             String goodSn, String name);

    List<CategoryResp> getCategory();

    List<CategoryResp> getBrand();

    ResponseType createGoods(CreateGoods createGoods);

    CreateGoods getGoodsDetail(Integer id);

    int updateGoods(CreateGoods createGoods);

    int deleteGoods(Goods goods);

    Long getGoodsCount();

    //微信获取品牌下的商品
    ResponseType getGoodsByBrandId(Integer brandId,Integer page,Integer size);

    //微信获取商品详情
    ResponseType getGoodsAndAllById(Integer id);

    //微信获取分类
    ResponseType getCategoryByParent(Integer id);

    //微信获取品类下的商品
    ResponseType getGoodsByCategory(Integer id);

    //微信获取相关商品
    ResponseType getRelativeGoods(Integer id);

    //微信按照关键字取商品
    ResponseType getGoodsByKeyword(String keyword,String sort,String order,Integer page,Integer size,Integer categoryId);

    List<HomeIndex.NewGoodsListBean> getNewGoodsList();

    List<HomeIndex.ChannelBean> getChannel();

    List<HomeIndex.HotGoodsListBean> getHotGoodsList();

    List<HomeIndex.FloorGoodsListBean> getFloorGoodsList();
}

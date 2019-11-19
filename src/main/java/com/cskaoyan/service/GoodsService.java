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

    List<HomeIndex.NewGoodsListBean> getNewGoodsList();

    List<HomeIndex.ChannelBean> getChannel();

    List<HomeIndex.HotGoodsListBean> getHotGoodsList();

    List<HomeIndex.FloorGoodsListBean> getFloorGoodsList();
}

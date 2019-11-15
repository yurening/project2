package com.cskaoyan.service;

import com.cskaoyan.bean.goods.ResponseType;

public interface GoodsService {
    ResponseType getAllGoods(Integer page, Integer limit,
                             String order, String sort,
                             String goodSn, String name);
}

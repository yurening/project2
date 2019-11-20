package com.cskaoyan.service;

import com.cskaoyan.bean.goods.ResponseType;

public interface BrandService {

    /*微信端接口：品牌*/
    ResponseType getBrandList(Integer page, Integer size);
    ResponseType getBrandById(Integer id);
}

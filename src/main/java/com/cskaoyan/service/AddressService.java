package com.cskaoyan.service;

import com.cskaoyan.bean.mall.address.AddressInfo;

import java.util.List;

public interface AddressService {
    List<AddressInfo> getList(Integer userId);
}

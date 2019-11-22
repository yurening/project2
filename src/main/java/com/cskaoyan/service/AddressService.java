package com.cskaoyan.service;

import com.cskaoyan.bean.mall.address.AddressInfo;
import com.cskaoyan.bean.mall.address.MallAddress;

import java.util.List;

public interface AddressService {
    List<AddressInfo> getList(Integer userId);

    MallAddress getAddressDetail(Integer id);

    void saveAddress(MallAddress mallAddress);

    void deleteAddress(Integer id);

    void saveNewAddress(MallAddress mallAddress);

    void setOtherAddressDefaultFalse(Integer id);
}

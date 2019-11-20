package com.cskaoyan.service;

import com.cskaoyan.bean.mall.address.AddressInfo;
import com.cskaoyan.bean.mall.address.MallAddress;
import com.cskaoyan.bean.mall.address.MallAddressExample;
import com.cskaoyan.mapper.MallAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    MallAddressMapper addressMapper;

    @Override
    public List<AddressInfo> getList(Integer userId) {
        MallAddressExample mallAddressExample = new MallAddressExample();
        mallAddressExample.createCriteria().andUserIdEqualTo(userId);
        List<MallAddress> mallAddresses = addressMapper.selectByExample(mallAddressExample);
        List<AddressInfo> list=new ArrayList<>();
        for (MallAddress x:
             mallAddresses) {
            AddressInfo addressInfo = new AddressInfo();
            addressInfo.setIsDefault(x.getIsDefault());
            addressInfo.setDetailedAddress(x.getAddress());
            addressInfo.setName(x.getName());
            addressInfo.setMobile(x.getMobile());
            addressInfo.setId(x.getId());
            list.add(addressInfo);
        }
        return list;
    }
}

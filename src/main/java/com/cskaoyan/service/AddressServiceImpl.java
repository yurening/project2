package com.cskaoyan.service;

import com.cskaoyan.bean.mall.address.AddressInfo;
import com.cskaoyan.bean.mall.address.MallAddress;
import com.cskaoyan.bean.mall.address.MallAddressExample;
import com.cskaoyan.bean.mall.region.MallRegionExample;
import com.cskaoyan.mapper.MallAddressMapper;
import com.cskaoyan.mapper.MallRegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    MallAddressMapper addressMapper;
    @Autowired
    MallRegionMapper regionMapper;

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

    @Override
    public MallAddress getAddressDetail(Integer id) {
        MallAddress mallAddress = addressMapper.selectByPrimaryKey(id);

        MallRegionExample example = new MallRegionExample();

        example.createCriteria().andCodeEqualTo(mallAddress.getAreaId());
        String areaName = regionMapper.selectByExample(example).get(0).getName();
        mallAddress.setAreaName(areaName);

        example.clear();
        example.createCriteria().andCodeEqualTo(mallAddress.getCityId());
        String cityName = regionMapper.selectByExample(example).get(0).getName();
        mallAddress.setCityName(cityName);

        example.clear();
        example.createCriteria().andCodeEqualTo(mallAddress.getProvinceId());
        String provinceName = regionMapper.selectByExample(example).get(0).getName();
        mallAddress.setProvinceName(provinceName);

        return mallAddress;
    }

    @Override
    public void saveAddress(MallAddress mallAddress) {
        addressMapper.updateByPrimaryKey(mallAddress);
    }

    @Override
    public void deleteAddress(Integer id) {
        addressMapper.deleteByPrimaryKey(id);
    }
}

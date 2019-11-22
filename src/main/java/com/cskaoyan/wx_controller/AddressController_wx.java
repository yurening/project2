package com.cskaoyan.wx_controller;

import com.cskaoyan.bean.mall.BaseRespVo;
import com.cskaoyan.bean.mall.address.AddressInfo;
import com.cskaoyan.bean.mall.address.MallAddress;
import com.cskaoyan.bean.mall.wx_order.WxId;
import com.cskaoyan.bean.user.User;
import com.cskaoyan.service.AddressService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("wx/address")
public class AddressController_wx {
    @Autowired
    AddressService addressService;

    @RequestMapping("list")
    public BaseRespVo getAddressList(@RequestHeader("X-cskaoyanmall-Admin-Token") String token){
        Integer userId = getUserID();
        List<AddressInfo> addressInfoList= addressService.getList(userId);
        return BaseRespVo.ok(addressInfoList);
    }

    @RequestMapping("detail")
    public BaseRespVo getAddressDetail(Integer id){
        MallAddress mallAddress = addressService.getAddressDetail(id);
        return BaseRespVo.ok(mallAddress);
    }

    @RequestMapping("save")
    public BaseRespVo saveAddress(@RequestBody MallAddress mallAddress){
        Integer userId = getUserID();
        mallAddress.setUserId(userId);
        if(mallAddress.getIsDefault()==true){
            addressService.setOtherAddressDefaultFalse(mallAddress.getId());
        }
        if(mallAddress.getId()==0){
            addressService.saveNewAddress(mallAddress);
        }else {
            addressService.saveAddress(mallAddress);
        }
        return BaseRespVo.ok(mallAddress.getId());
    }

    @RequestMapping("delete")
    public BaseRespVo deleteAddress(@RequestBody WxId id){
        addressService.deleteAddress(id.getId());
        return BaseRespVo.ok(null);
    }

    private Integer getUserID(){
        User principal =(User) SecurityUtils.getSubject().getPrincipal();
        return principal.getId();
    }
}

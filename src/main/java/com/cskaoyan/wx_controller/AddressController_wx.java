package com.cskaoyan.wx_controller;

import com.cskaoyan.bean.mall.BaseRespVo;
import com.cskaoyan.bean.mall.address.AddressInfo;
import com.cskaoyan.needdelete.UserTokenManager;
import com.cskaoyan.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
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
        Integer userId = UserTokenManager.getUserId(token);
        List<AddressInfo> addressInfoList= addressService.getList(userId);
        return BaseRespVo.ok(addressInfoList);
    }
}

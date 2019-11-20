package com.cskaoyan.wx_controller;

import com.cskaoyan.bean.BaseReqVo;
import com.cskaoyan.bean.user.Cart;
import com.cskaoyan.bean.wx_index.CartIndex;
import com.cskaoyan.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wx/cart")
public class CartController_wx {
    @Autowired
    CartService cartService;

    @RequestMapping("index")
    public BaseReqVo cartIndex() {
        CartIndex cartIndex = new CartIndex();
        cartIndex.setCartTotal(cartService.getCartTotal(8));
        cartIndex.setCartList(cartService.getCartListByUserId(8));
        BaseReqVo<CartIndex> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(cartIndex);
        return baseReqVo;
    }

    @RequestMapping("checked")
    public BaseReqVo updateChecked(@RequestBody Map<String, Object> map) {
        List<Integer> productIds = (List<Integer>) map.get("productIds");
        int isChecked = (int) map.get("isChecked");
        cartService.updateChecked(8, productIds.get(0), isChecked);
        return cartIndex();
    }

    @RequestMapping("update")
    public BaseReqVo updateNumber(@RequestBody Cart cart) {
        BaseReqVo baseReqVo = new BaseReqVo();
        if (!cartService.updateNumberById(cart.getId(), cart.getNumber())) {
            baseReqVo.setErrno(501);
            baseReqVo.setErrmsg("库存不足!");
        } else {
            baseReqVo.setErrmsg("成功");
        }
        return baseReqVo;
    }
}

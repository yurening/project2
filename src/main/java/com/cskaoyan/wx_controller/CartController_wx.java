package com.cskaoyan.wx_controller;

import com.cskaoyan.bean.BaseReqVo;
import com.cskaoyan.bean.user.Cart;
import com.cskaoyan.bean.wx_index.CartIndex;
import com.cskaoyan.service.CartService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        cartIndex.setCartTotal(cartService.getCartTotal());
        cartIndex.setCartList(cartService.getCartListByUserId());
        cartIndex.setManmian(cartService.getManmian());
        BaseReqVo<CartIndex> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(cartIndex);
        return baseReqVo;
    }

    @RequestMapping("checked")
    public BaseReqVo updateChecked(@RequestBody Map<String, Object> map) {
        List<Integer> productIds = (List<Integer>) map.get("productIds");
        int isChecked = (int) map.get("isChecked");
        cartService.updateChecked( productIds, isChecked);
        return cartIndex();
    }

    @RequestMapping("update")
    public BaseReqVo updateNumber(@RequestBody Cart cart) {
        BaseReqVo baseReqVo = new BaseReqVo();
        if (!cartService.updateNumberById(cart.getId(), cart.getNumber())) {
            baseReqVo.setErrno(508);
            baseReqVo.setErrmsg("库存不足");
        } else {
            baseReqVo.setErrmsg("成功");
        }
        return baseReqVo;
    }

    @RequestMapping("delete")
    public BaseReqVo deleteCart(@RequestBody Map<String, List<Integer>> map) {
        List<Integer> productIds = map.get("productIds");
        cartService.deleteCartByUserIdAndProductIdS( productIds);
        return cartIndex();
    }

    @RequestMapping("goodscount")
    public BaseReqVo getGoodsCount() {
        BaseReqVo<Integer> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(cartService.getGoodsCount());
        return baseReqVo;
    }

    @RequestMapping("add")
    public BaseReqVo addCart(@RequestBody Cart cart) {
        BaseReqVo<Integer> baseReqVo = new BaseReqVo<>();
        if(!cartService.addCart(cart)) {
            baseReqVo.setErrno(508);
            baseReqVo.setErrmsg("库存不足");
        } else {
            baseReqVo.setErrmsg("成功");
            baseReqVo.setData(cartService.getGoodsCount());
        }
        return baseReqVo;
    }

    @RequestMapping("fastadd")
    public BaseReqVo fastAddCart(@RequestBody Cart cart) {
        BaseReqVo<Integer> baseReqVo = new BaseReqVo<>();
        Integer id = cartService.fastAddCart(cart, true);
        baseReqVo.setData(id);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("checkout")
    public BaseReqVo checkout(Integer cartId, Integer addressId, Integer couponId, Integer grouponRulesId) {
        BaseReqVo<Map<String, Object>> baseReqVo = new BaseReqVo<>();
        Map<String, Object> result = cartService.checkout(cartId, addressId, couponId, grouponRulesId);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(result);
        return baseReqVo;
    }
}

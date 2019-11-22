package com.cskaoyan.service;

import com.cskaoyan.bean.user.Cart;
import com.cskaoyan.bean.wx_index.CartIndex;

import java.util.List;
import java.util.Map;

public interface CartService {
    CartIndex.CartTotalBean getCartTotal();

    List<Cart> getCartListByUserId();

    void updateChecked(List<Integer> productIds, int isChecked);

    boolean updateNumberById(Integer id, Short number);


    void deleteCartByUserIdAndProductIdS( List<Integer> productIds);

    boolean addCart(Cart cart);

    Integer getGoodsCount();

    Integer fastAddCart(Cart cart, Boolean deleted);

    Map<String, Object> checkout(Integer cartId, Integer addressId, Integer couponId, Integer grouponRulesId);

    double getManmian();
}

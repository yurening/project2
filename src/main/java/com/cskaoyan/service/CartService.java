package com.cskaoyan.service;

import com.cskaoyan.bean.user.Cart;
import com.cskaoyan.bean.wx_index.CartIndex;

import java.util.List;
import java.util.Map;

public interface CartService {
    CartIndex.CartTotalBean getCartTotal(Integer userId);

    List<Cart> getCartListByUserId(Integer userId);

    void updateChecked(int userId, List<Integer> productIds, int isChecked);

    boolean updateNumberById(Integer id, Short number);


    void deleteCartByUserIdAndProductIdS(Integer userId, List<Integer> productIds);

    boolean addCart(Cart cart);

    Integer getGoodsCount(int userId);

    Integer fastAddCart(Cart cart, Boolean deleted);

    Map<String, Object> checkout(Integer cartId, Integer addressId, Integer couponId, Integer grouponRulesId);
}

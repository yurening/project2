package com.cskaoyan.service;

import com.cskaoyan.bean.user.Cart;
import com.cskaoyan.bean.wx_index.CartIndex;

import java.util.List;

public interface CartService {
    CartIndex.CartTotalBean getCartTotal(Integer userId);

    List<Cart> getCartListByUserId(Integer userId);

    void updateChecked(int userId, int productId, int isChecked);

    boolean updateNumberById(Integer id, Short number);
}

package com.cskaoyan.service;

import com.cskaoyan.bean.user.Cart;
import com.cskaoyan.bean.user.CartExample;
import com.cskaoyan.bean.wx_index.CartIndex;
import com.cskaoyan.mapper.CartMapper;
import com.cskaoyan.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartMapper cartMapper;
    @Autowired
    ProductMapper productMapper;

    @Override
    public CartIndex.CartTotalBean getCartTotal(Integer userId) {
        CartIndex.CartTotalBean cartTotalBean = new CartIndex.CartTotalBean();
        Integer count = cartMapper.getGoodsCountByUserId(null, userId);
        cartTotalBean.setGoodsCount(count == null ? 0 : count);
        Integer countChecked = cartMapper.getGoodsCountByUserId(true, userId);
        cartTotalBean.setCheckedGoodsCount(countChecked == null ? 0 : countChecked);
        Double amount = cartMapper.getGoodsAmountByUserId(null, userId);
        cartTotalBean.setGoodsAmount(amount == null ? 0 : amount);
        Double amountChecked = cartMapper.getGoodsAmountByUserId(true, userId);
        cartTotalBean.setCheckedGoodsAmount(amountChecked == null ? 0 : amountChecked);
        return cartTotalBean;
    }

    @Override
    public List<Cart> getCartListByUserId(Integer userId) {
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return cartMapper.selectByExample(cartExample);
    }

    @Override
    public void updateChecked(int userId, int productId, int isChecked) {
        CartExample cartExample = new CartExample();
        boolean b = false;
        if (isChecked == 1) {
            b = true;
        }
        cartExample.createCriteria().andProductIdEqualTo(productId).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        Cart cart = new Cart();
        cart.setChecked(b);
        cartMapper.updateByExampleSelective(cart, cartExample);
    }

    @Override
    public boolean updateNumberById(Integer id, Short number) {
        if (productMapper.selectByPrimaryKey(cartMapper.selectByPrimaryKey(id).getProductId()).getNumber() < number) {
            return false;
        }
        Cart cart = new Cart();
        cart.setId(id);
        cart.setNumber(number);
        cartMapper.updateByPrimaryKeySelective(cart);
        return true;
    }
}

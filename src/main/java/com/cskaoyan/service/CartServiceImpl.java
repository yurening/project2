package com.cskaoyan.service;

import com.cskaoyan.bean.generalize.GrouponRules;
import com.cskaoyan.bean.goods.Goods;
import com.cskaoyan.bean.goods.Product;
import com.cskaoyan.bean.user.Cart;
import com.cskaoyan.bean.user.CartExample;
import com.cskaoyan.bean.wx_index.CartIndex;
import com.cskaoyan.mapper.CartMapper;
import com.cskaoyan.mapper.GoodsMapper;
import com.cskaoyan.mapper.GrouponRulesMapper;
import com.cskaoyan.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    GrouponRulesMapper grouponRulesMapper;

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
    public void updateChecked(int userId, List<Integer> productIds, int isChecked) {
        CartExample cartExample = new CartExample();
        boolean b = false;
        if (isChecked == 1) {
            b = true;
        }
        cartExample.createCriteria().andProductIdIn(productIds).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        Cart cart = new Cart();
        cart.setChecked(b);
        cartMapper.updateByExampleSelective(cart, cartExample);
        updateTimeByExample(cartExample);
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
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andIdEqualTo(id);
        updateTimeByExample(cartExample);
        return true;
    }

    @Override
    public void deleteCartByUserIdAndProductIdS(Integer userId, List<Integer> productIds) {
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(userId).andProductIdIn(productIds).andDeletedEqualTo(false);
        Cart cart = new Cart();
        cart.setDeleted(true);
        cartMapper.updateByExampleSelective(cart, cartExample);
        updateTimeByExample(cartExample);
    }

    @Override
    public boolean addCart(Cart cart) {
        Integer productId = cart.getProductId();
        Product product = productMapper.selectByPrimaryKey(productId);
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(8).andProductIdEqualTo(productId).andDeletedEqualTo(false);
        List<Cart> carts = cartMapper.selectByExample(cartExample);
        if (carts.size() != 0) {
            Cart cartExists = carts.get(0);
            int totalNumber = cartExists.getNumber() + cart.getNumber();
            if (totalNumber > product.getNumber()) {
                return false;
            }
            cart.setId(cartExists.getId());
            cart.setNumber((short) totalNumber);
            cart.setChecked(true);
            cart.setUpdateTime(new Date());
            cartMapper.updateByPrimaryKeySelective(cart);
            return true;
        }
        if (cart.getNumber() > product.getNumber()) {
            return false;
        }
        fastAddCart(cart, false);
        return true;
    }

    @Override
    public Integer getGoodsCount(int userId) {
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return Math.toIntExact(cartMapper.countByExample(cartExample));
    }

    @Override
    public Integer fastAddCart(Cart cart, Boolean deleted) {
        Integer productId = cart.getProductId();
        Product product = productMapper.selectByPrimaryKey(productId);
        Goods goods = goodsMapper.selectByPrimaryKey(cart.getGoodsId());
        cart.setUserId(8);
        cart.setGoodsSn(goods.getGoodsSn());
        cart.setGoodsName(goods.getName());
        cart.setPrice(product.getPrice());
        cart.setSpecifications(product.getSpecifications());
        cart.setChecked(true);
        cart.setPicUrl(product.getUrl());
        Date date = new Date();
        cart.setAddTime(date);
        cart.setUpdateTime(date);
        cart.setDeleted(deleted);
        cartMapper.insertSelective(cart);
        return cart.getId();
    }

    @Override
    public Map<String, Object> checkout(Integer cartId, Integer addressId, Integer couponId, Integer grouponRulesId) {
        BigDecimal goodsTotalPrice = getGoodsTotalPrice(8, cartId, grouponRulesId);
        return null;
    }

    private BigDecimal getGoodsTotalPrice(int userId, int cartId, int grouponRulesId) {
        BigDecimal goodsTotalPrice;
        if (cartId == 0) {
            CartExample cartExample = new CartExample();
            cartExample.createCriteria().andDeletedEqualTo(false).andCheckedEqualTo(true).andUserIdEqualTo(userId);
            List<Cart> carts = cartMapper.selectByExample(cartExample);
            goodsTotalPrice = new BigDecimal("0");
            for (Cart cart : carts) {
                goodsTotalPrice =  goodsTotalPrice.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            }
        } else {
            Cart cart = cartMapper.selectByPrimaryKey(cartId);
            BigDecimal number = new BigDecimal(cart.getNumber());
            goodsTotalPrice = cart.getPrice().multiply(number);
            if (grouponRulesId != 0) {
                goodsTotalPrice = goodsTotalPrice.subtract(grouponRulesMapper.selectByPrimaryKey(grouponRulesId).getDiscount().multiply(number));
            }
        }
        return goodsTotalPrice;
    }

    private void updateTimeByExample(CartExample cartExample) {
        Cart cart = new Cart();
        cart.setUpdateTime(new Date());
        cartMapper.updateByExampleSelective(cart , cartExample);
    }
}

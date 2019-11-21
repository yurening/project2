package com.cskaoyan.service;

import com.cskaoyan.bean.generalize.Coupon;
import com.cskaoyan.bean.goods.Goods;
import com.cskaoyan.bean.goods.Product;
import com.cskaoyan.bean.goods.System;
import com.cskaoyan.bean.goods.SystemExample;
import com.cskaoyan.bean.mall.address.MallAddress;
import com.cskaoyan.bean.mall.address.MallAddressExample;
import com.cskaoyan.bean.user.Cart;
import com.cskaoyan.bean.user.CartExample;
import com.cskaoyan.bean.user.CouponRequest;
import com.cskaoyan.bean.wx_index.CartIndex;
import com.cskaoyan.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;

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
    @Autowired
    SystemMapper systemMapper;
    @Autowired
    UserService userService;
    @Autowired
    MallAddressMapper addressMapper;

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
        // 获取运费满减最低消费、规定运费金额
        List<System> systems = systemMapper.selectByExample(new SystemExample());
        BigDecimal minFreight = new BigDecimal("0");
        BigDecimal freightValue = new BigDecimal("0");
        for (System system : systems) {
            if ("cskaoyan_mall_express_freight_min".equals(system.getKeyName())) {
                 minFreight = new BigDecimal(system.getKeyValue());
            }
            if ("cskaoyan_mall_express_freight_value".equals(system.getKeyName())) {
                freightValue = new BigDecimal(system.getKeyValue());
            }
        }

        // 判断是否拼团并得出商品总价和拼团优惠金额
        BigDecimal goodsTotalPrice = getGoodsTotalPrice(8, cartId, grouponRulesId);
        BigDecimal grouponPrice = new BigDecimal("0");
        if (grouponRulesId != 0) {
            grouponPrice = grouponRulesMapper.selectByPrimaryKey(grouponRulesId).getDiscount();
        }

        // 判断是否需要运费
        BigDecimal freightPrice = new BigDecimal("0");
        if (goodsTotalPrice.compareTo(minFreight) > 0) {
            freightPrice = freightValue;
        }

        // 获取可以使用的优惠券列表
        CouponRequest couponRequest = new CouponRequest();
        couponRequest.setCartId(cartId);
        List<Coupon> coupons = userService.couponSelectList(couponRequest);
        int availableCouponLength = coupons.size();

        // 获取优惠券金额
        BigDecimal couponPrice = new BigDecimal("0");
        if (availableCouponLength != 0) {
            Coupon coupon = coupons.get(0);
            couponId = coupon.getId();
            couponPrice = new BigDecimal(coupon.getDiscount());
        }

        // 获取用户地址信息
        MallAddress address = addressMapper.selectByPrimaryKey(addressId);

        // 获取下单的商品信息
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(8).andCheckedEqualTo(true).andDeletedEqualTo(false);
        List<Cart> carts = new ArrayList<>();
        if (cartId != 0) {
            carts.add(cartMapper.selectByPrimaryKey(cartId));
        }
        carts = cartMapper.selectByExample(cartExample);

        // 算出订单最终实付价格
        BigDecimal finalPrice = goodsTotalPrice.add(freightPrice).subtract(couponPrice);

        // 构造返回数据
        Map<String, Object> map = new HashMap<>();
        map.put("actualPrice", finalPrice);
        map.put("addressId", addressId);
        map.put("availableCouponLength", availableCouponLength);
        map.put("checkedAddress", address);
        map.put("checkedGoodsList", carts);
        map.put("couponId", couponId);
        map.put("couponPrice", couponPrice);
        map.put("freightPrice", freightPrice);
        map.put("goodsTotalPrice", goodsTotalPrice);
        map.put("grouponPrice", grouponPrice);
        map.put("grouponRulesId", grouponRulesId);
        map.put("orderTotalPrice", finalPrice);

        return map;
    }

    private BigDecimal getGoodsTotalPrice(int cartId, int grouponRulesId) {
        BigDecimal goodsTotalPrice;
        if (cartId == 0) {
            CartExample cartExample = new CartExample();
            cartExample.createCriteria().andDeletedEqualTo(false).andCheckedEqualTo(true).andUserIdEqualTo(8);
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

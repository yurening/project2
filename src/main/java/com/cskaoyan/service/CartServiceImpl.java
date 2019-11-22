package com.cskaoyan.service;

import com.cskaoyan.bean.generalize.Coupon;
import com.cskaoyan.bean.generalize.CouponUser;
import com.cskaoyan.bean.generalize.CouponUserExample;
import com.cskaoyan.bean.goods.Goods;
import com.cskaoyan.bean.goods.Product;
import com.cskaoyan.bean.goods.System;
import com.cskaoyan.bean.goods.SystemExample;
import com.cskaoyan.bean.mall.address.MallAddress;
import com.cskaoyan.bean.mall.address.MallAddressExample;
import com.cskaoyan.bean.user.Cart;
import com.cskaoyan.bean.user.CartExample;
import com.cskaoyan.bean.user.CouponRequest;
import com.cskaoyan.bean.user.User;
import com.cskaoyan.bean.wx_index.CartIndex;
import com.cskaoyan.mapper.*;
import org.apache.shiro.SecurityUtils;


import org.apache.shiro.subject.Subject;


import org.apache.shiro.subject.Subject;

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
    MallAddressMapper addressMapper;
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    CouponUserMapper couponUserMapper;

    @Override
    public CartIndex.CartTotalBean getCartTotal() {
        //获取用户id
        Subject subject = SecurityUtils.getSubject();
        User userLogin = (User) subject.getPrincipal();
        CartIndex.CartTotalBean cartTotalBean = new CartIndex.CartTotalBean();
        Integer count = cartMapper.getGoodsCountByUserId(null, userLogin.getId());
        cartTotalBean.setGoodsCount(count == null ? 0 : count);
        Integer countChecked = cartMapper.getGoodsCountByUserId(true, userLogin.getId());
        cartTotalBean.setCheckedGoodsCount(countChecked == null ? 0 : countChecked);
        Double amount = cartMapper.getGoodsAmountByUserId(null, userLogin.getId());
        cartTotalBean.setGoodsAmount(amount == null ? 0 : amount);
        Double amountChecked = cartMapper.getGoodsAmountByUserId(true, userLogin.getId());
        cartTotalBean.setCheckedGoodsAmount(amountChecked == null ? 0 : amountChecked);
        return cartTotalBean;
    }

    @Override
    public List<Cart> getCartListByUserId() {
        //获取用户id
        Subject subject = SecurityUtils.getSubject();
        User userLogin = (User) subject.getPrincipal();
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(userLogin.getId()).andDeletedEqualTo(false);
        return cartMapper.selectByExample(cartExample);
    }

    @Override
    public void updateChecked(List<Integer> productIds, int isChecked) {
        //获取用户id
        Subject subject = SecurityUtils.getSubject();
        User userLogin = (User) subject.getPrincipal();
        CartExample cartExample = new CartExample();
        boolean b = false;
        if (isChecked == 1) {
            b = true;
        }
        cartExample.createCriteria().andProductIdIn(productIds).andUserIdEqualTo(userLogin.getId()).andDeletedEqualTo(false);
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
    public void deleteCartByUserIdAndProductIdS(List<Integer> productIds) {
        //获取用户id
        Subject subject = SecurityUtils.getSubject();
        User userLogin = (User) subject.getPrincipal();
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(userLogin.getId()).andProductIdIn(productIds).andDeletedEqualTo(false);
        Cart cart = new Cart();
        cart.setDeleted(true);
        cartMapper.updateByExampleSelective(cart, cartExample);
        updateTimeByExample(cartExample);
    }

    @Override
    public boolean addCart(Cart cart) {
        //获取用户id
        Subject subject = SecurityUtils.getSubject();
        User userLogin = (User) subject.getPrincipal();
        Integer productId = cart.getProductId();
        Product product = productMapper.selectByPrimaryKey(productId);
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(userLogin.getId()).andProductIdEqualTo(productId).andDeletedEqualTo(false);
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
    public Integer getGoodsCount() {
        //获取用户id
        Subject subject = SecurityUtils.getSubject();
        User userLogin = (User) subject.getPrincipal();
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(userLogin.getId()).andDeletedEqualTo(false);
        return Math.toIntExact(cartMapper.countByExample(cartExample));
    }

    @Override
    public Integer fastAddCart(Cart cart, Boolean deleted) {
        //获取用户id
        Subject subject = SecurityUtils.getSubject();
        User userLogin = (User) subject.getPrincipal();
        Integer productId = cart.getProductId();
        Product product = productMapper.selectByPrimaryKey(productId);
        Goods goods = goodsMapper.selectByPrimaryKey(cart.getGoodsId());
        cart.setUserId(userLogin.getId());
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
        // 获取用户id
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Integer userId = user.getId();

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
        BigDecimal goodsTotalPrice = getGoodsTotalPrice(cartId, grouponRulesId);
        BigDecimal grouponPrice = new BigDecimal("0");
        if (grouponRulesId != 0) {
            grouponPrice = grouponRulesMapper.selectByPrimaryKey(grouponRulesId).getDiscount();
        }

        // 判断是否需要运费
        BigDecimal freightPrice = new BigDecimal("0");
        if (goodsTotalPrice.compareTo(minFreight) < 0) {
            freightPrice = freightValue;
        }

        // 获取可以使用的优惠券列表
        List<Coupon> coupons = couponSelectList(goodsTotalPrice);
        int availableCouponLength = coupons.size();

        // 获取优惠券金额
//        BigDecimal couponPrice = new BigDecimal("0");
//        if (availableCouponLength != 0) {
//            Coupon coupon = coupons.get(0);
//            couponId = coupon.getId();
//            couponPrice = new BigDecimal(coupon.getDiscount());
//        }
        BigDecimal couponPrice = new BigDecimal("0");
        if (availableCouponLength != 0) {
            Coupon coupon = null;
            if (couponId <= 0) {
                coupon = coupons.get(0);
            } else {
                coupon = couponMapper.selectByPrimaryKey(couponId);
                couponId = coupon.getId();
                couponPrice = new BigDecimal(coupon.getDiscount());
            }
        }

            // 获取用户地址信息
            if (addressId == 0) {
                addressId = addressMapper.selectByExample(new MallAddressExample()).get(0).getId();
            }
            MallAddress address = addressMapper.selectByPrimaryKey(addressId);

            // 获取下单的商品信息
            List<Cart> carts = new ArrayList<>();
            if (cartId != 0) {
                carts.add(cartMapper.selectByPrimaryKey(cartId));
            } else {
                CartExample cartExample = new CartExample();
                cartExample.createCriteria().andUserIdEqualTo(userId).andCheckedEqualTo(true).andDeletedEqualTo(false);
                carts = cartMapper.selectByExample(cartExample);
            }


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

        public BigDecimal getGoodsTotalPrice ( int cartId, int grouponRulesId){

            User user = (User) SecurityUtils.getSubject().getPrincipal();
            Integer userId = user.getId();
            BigDecimal goodsTotalPrice;
            if (cartId == 0) {
                CartExample cartExample = new CartExample();
                cartExample.createCriteria().andDeletedEqualTo(false).andCheckedEqualTo(true).andUserIdEqualTo(userId);
                List<Cart> carts = cartMapper.selectByExample(cartExample);
                goodsTotalPrice = new BigDecimal("0");
                for (Cart cart : carts) {
                    goodsTotalPrice = goodsTotalPrice.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
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

        private void updateTimeByExample (CartExample cartExample){
            Cart cart = new Cart();
            cart.setUpdateTime(new Date());
            cartMapper.updateByExampleSelective(cart, cartExample);
        }



        public List<Coupon> couponSelectList (BigDecimal goodsTotalPrice){
            //获取用户id
            Subject subject = SecurityUtils.getSubject();
            User userLogin = (User) subject.getPrincipal();
            CouponUserExample couponUserExample = new CouponUserExample();
            couponUserExample.createCriteria().andUserIdEqualTo(userLogin.getId()).andStatusEqualTo((short) 0);
            List<CouponUser> couponUsers = couponUserMapper.selectByExample(couponUserExample);
            List<Coupon> coupons = new ArrayList<>();
            for (CouponUser couponUser : couponUsers) {
                //判断购物车商品的价格是否大于优惠券的最低消费额，如果小于，则不能使用优惠券，如果大于，可以使用优惠券
                Coupon coupon = couponMapper.selectByPrimaryKey(couponUser.getCouponId());
                //当优惠券未使用时可以显示出来以供使用
                if ((goodsTotalPrice.doubleValue() >= Double.parseDouble(coupon.getMin())) && (coupon.getStatus() == 0)) {
                    coupons.add(coupon);
                }
            }
            return coupons;
        }
}



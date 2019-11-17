package com.cskaoyan.service;

import com.cskaoyan.bean.generalize.*;

import java.util.HashMap;
import java.util.List;

public interface GeneralizeService {

    HashMap<String,Object> queryAd(Integer page,Integer limit,String name,String content,String sort,String order);

    Ad adCreate(Ad ad);

    Ad adUpdate(Ad ad);

    void adDelete(Ad ad);

    HashMap<String, Object> queryCoupon(Integer page,Integer limit,String name,
                             Integer type,Integer status,String sort,String order);

    Coupon selectCouponById(Integer id);

    HashMap<String, Object> queryUserByCouponId(Integer page,Integer limit
            ,Integer couponId,String sort,String order,Integer userId,Integer status);

    Coupon couponCreate(Coupon coupon);

    Coupon couponUpdate(Coupon coupon);

    void couponDelete(Coupon coupon);

    HashMap<String, Object> topicList(Integer page,Integer limit,String title ,String subtitle,String sort,String order);

    Topic topicCreate(Topic topic);

    Topic topicUpdate(Topic topic);

    void topicDelete(Topic topic);

    HashMap<String, Object> grouponList(Integer page
            , Integer limit,Integer goodsId,String sort,String order);

    GrouponRules grouponRulesCreate(GrouponRules grouponRules);

    HashMap<String,Object> grouponListRecord(Integer page, Integer limit
            , String sort, String order,Integer goodsId);

    GrouponRules grouponRulesUpdate(GrouponRules grouponRules);

    void grouponDelete(GrouponRules grouponRules);
}

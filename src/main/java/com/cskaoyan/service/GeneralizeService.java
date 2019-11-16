package com.cskaoyan.service;

import com.cskaoyan.bean.generalize.*;

import java.util.HashMap;
import java.util.List;

public interface GeneralizeService {

    List<Ad> queryAd(Integer page,Integer limit,String name,String content,String sort,String order);

    Ad adCreate(Ad ad);

    Ad adUpdate(Ad ad);

    void adDelete(Ad ad);

    List<Coupon> queryCoupon(Integer page,Integer limit,String name,
                             Integer type,Integer status,String sort,String order);

    Coupon selectCouponById(Integer id);

    List<CouponUser> queryUserByCouponId(Integer page,Integer limit
            ,Integer couponId,String sort,String order,Integer userId,Integer status);

    Coupon couponCreate(Coupon coupon);

    Coupon couponUpdate(Coupon coupon);

    void couponDelete(Coupon coupon);

    List<Topic> topicList(Integer page,Integer limit,String title ,String subtitle,String sort,String order);

    Topic topicCreate(Topic topic);

    Integer totalAd();

    Integer totalCoupon();

    Integer totalUser();

    Integer totalTopic();

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

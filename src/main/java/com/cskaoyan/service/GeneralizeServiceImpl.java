package com.cskaoyan.service;

import com.cskaoyan.bean.generalize.*;
import com.cskaoyan.bean.goods.Goods;
import com.cskaoyan.bean.goods.GoodsExample;
import com.cskaoyan.mapper.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class GeneralizeServiceImpl implements GeneralizeService{

    @Autowired
    AdMapper adMapper;

    @Autowired
    CouponMapper couponMapper;

    @Autowired
    CouponUserMapper couponUserMapper;

    @Autowired
    TopicMapper topicMapper;

    @Autowired
    GrouponMapper grouponMapper;

    @Autowired
    GrouponRulesMapper grouponRulesMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public List<Ad> queryAd(Integer page, Integer limit, String name, String content, String sort, String order) {
        PageHelper.startPage(page,limit);
        AdExample adExample = new AdExample();
        AdExample.Criteria criteria = adExample.createCriteria();
        if (name != null){
            criteria.andNameLike("%" + name + "%");
        }
        if (content != null){
            criteria.andContentLike("%" + name + "%");
        }
        List<Ad> adList = adMapper.selectByExample(adExample);
        return adList;
    }

    @Override
    public Ad adCreate(Ad ad) {
        Date date = new Date();
        ad.setAddTime(date);
        ad.setUpdateTime(date);
        adMapper.insert(ad);
        return ad;
    }

    @Override
    public Ad adUpdate(Ad ad) {
        AdExample adExample = new AdExample();
        AdExample.Criteria criteria = adExample.createCriteria();
        criteria.andIdEqualTo(ad.getId());
        adMapper.updateByExample(ad,adExample);
        return ad;
    }

    @Override
    public void adDelete(Ad ad) {
        AdExample adExample = new AdExample();
        AdExample.Criteria criteria = adExample.createCriteria();
        criteria.andIdEqualTo(ad.getId());
        adMapper.deleteByExample(adExample);
    }

    @Override
    public List<Coupon> queryCoupon(Integer page, Integer limit, String name, Integer type, Integer status, String sort, String order) {
        PageHelper.startPage(page,limit);
        CouponExample couponExample = new CouponExample();
        CouponExample.Criteria criteria = couponExample.createCriteria();
        if (name != null){
            criteria.andNameLike("%" + name + "%");
        }
        if (type != null){
            criteria.andTypeEqualTo(type.shortValue());
        }
        if (status !=null){
            criteria.andStatusEqualTo(status.shortValue());
        }
        List<Coupon> couponList = couponMapper.selectByExample(couponExample);
        return couponList;
    }

    @Override
    public Coupon selectCouponById(Integer id) {
        Coupon coupon = couponMapper.selectByPrimaryKey(id);
        return coupon;
    }

    @Override
    public List<CouponUser> queryUserByCouponId(Integer page, Integer limit
            , Integer couponId, String sort, String order,Integer userId,Integer status) {
        PageHelper.startPage(page,limit);
        CouponUserExample couponUserExample = new CouponUserExample();
        CouponUserExample.Criteria criteria = couponUserExample.createCriteria();
        if (userId != null){
            criteria.andUserIdEqualTo(userId);
        }
        if (status != null){
            criteria.andStatusEqualTo(status.shortValue());
        }
        criteria.andCouponIdEqualTo(couponId);
        List<CouponUser> couponUsers = couponUserMapper.selectByExample(couponUserExample);
        return couponUsers;
    }

    @Override
    public Coupon couponCreate(Coupon coupon) {
        couponMapper.insert(coupon);
        return coupon;
    }

    @Override
    public Coupon couponUpdate(Coupon coupon) {
        int i = couponMapper.updateByPrimaryKey(coupon);
        return coupon;
    }

    @Override
    public void couponDelete(Coupon coupon) {
        CouponExample couponExample = new CouponExample();
        CouponExample.Criteria criteria = couponExample.createCriteria();
        criteria.andIdEqualTo(coupon.getId());
        couponMapper.deleteByExample(couponExample);
    }

    @Override
    public List<Topic> topicList(Integer page, Integer limit, String title, String subtitle, String sort, String order) {
        PageHelper.startPage(page,limit);
        TopicExample topicExample = new TopicExample();
        TopicExample.Criteria criteria = topicExample.createCriteria();
        if (title != null){
            criteria.andTitleLike("%" + title + "%");
        }
        if (subtitle != null){
            criteria.andSubtitleLike("%" + subtitle + "%");
        }
        List<Topic> topicList = topicMapper.selectByExample(topicExample);
        return topicList;
    }

    @Override
    public Topic topicCreate(Topic topic) {
        Date date = new Date();
        topic.setAddTime(date);
        topic.setUpdateTime(date);
        topic.setDeleted(false);
        topic.setSortOrder(0);
        topicMapper.insert(topic);
        return topic;
    }

    @Override
    public Integer totalAd() {
        AdExample adExample = new AdExample();
        long l = adMapper.countByExample(adExample);
        return Math.toIntExact(l);
    }

    @Override
    public Integer totalCoupon() {
        CouponExample coupon = new CouponExample();
        long l = couponMapper.countByExample(coupon);
        return Math.toIntExact(l);
    }

    @Override
    public Integer totalUser() {
        CouponUserExample couponUserExample = new CouponUserExample();
        long l = couponUserMapper.countByExample(couponUserExample);
        return Math.toIntExact(l);
    }

    @Override
    public Integer totalTopic() {
        TopicExample topicExample = new TopicExample();
        long l = topicMapper.countByExample(topicExample);
        return Math.toIntExact(l);
    }

    @Override
    public Topic topicUpdate(Topic topic) {
        int i = topicMapper.updateByPrimaryKey(topic);
        return topic;
    }

    @Override
    public void topicDelete(Topic topic) {
        TopicExample topicExample = new TopicExample();
        TopicExample.Criteria criteria = topicExample.createCriteria();
        criteria.andIdEqualTo(topic.getId());
        topicMapper.deleteByExample(topicExample);
    }

    @Override
    public HashMap<String, Object> grouponList(Integer page, Integer limit, Integer goodsId, String sort, String order) {
        PageHelper.startPage(page,limit);
        HashMap<String,Object> hashMap = new HashMap<>();
        GrouponRulesExample grouponRulesExample = new GrouponRulesExample();
        long l = grouponRulesMapper.countByExample(grouponRulesExample);
        GrouponRulesExample.Criteria criteria = grouponRulesExample.createCriteria();
        if (goodsId != null){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        List<GrouponRules> grouponRulesList = grouponRulesMapper.selectByExample(grouponRulesExample);
        hashMap.put("total",Math.toIntExact(l));
        hashMap.put("items",grouponRulesList);
        return hashMap;
    }

    @Override
    public GrouponRules grouponRulesCreate(GrouponRules grouponRules) {
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        criteria.andIdEqualTo(grouponRules.getGoodsId());
        List<Goods> goods = goodsMapper.selectByExample(goodsExample);
        Goods goods1 = goods.get(0);
        grouponRules.setGoodsName(goods1.getName());
        grouponRules.setPicUrl(goods1.getPicUrl());
        grouponRules.setAddTime(new Date());
        grouponRules.setUpdateTime(new Date());
        int insert = grouponRulesMapper.insert(grouponRules);
        return grouponRules;
    }

    @Override
    public HashMap<String, Object> grouponListRecord(Integer page, Integer limit, String sort, String order,Integer goodsId) {
        PageHelper.startPage(page,limit);
        GrouponExample grouponExample = new GrouponExample();
        long l = grouponMapper.countByExample(grouponExample);
        GrouponExample.Criteria criteria = grouponExample.createCriteria();
        if (goodsId != null){
            criteria.andIdEqualTo(goodsId);
        }
        List<Groupon> grouponList = grouponMapper.selectByExample(grouponExample);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("total",Math.toIntExact(l));
        hashMap.put("items",grouponList);
        return hashMap;
    }

    @Override
    public GrouponRules grouponRulesUpdate(GrouponRules grouponRules) {
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        criteria.andIdEqualTo(grouponRules.getGoodsId());
        grouponRules.setUpdateTime(new Date());
        GrouponRulesExample grouponRulesExample = new GrouponRulesExample();
        GrouponRulesExample.Criteria criteria1 = grouponRulesExample.createCriteria();
        criteria1.andIdEqualTo(grouponRules.getId());
        int i = grouponRulesMapper.updateByExample(grouponRules, grouponRulesExample);
        return grouponRules;
    }

    @Override
    public void grouponDelete(GrouponRules grouponRules) {
        GrouponRulesExample grouponRulesExample = new GrouponRulesExample();
        GrouponRulesExample.Criteria criteria = grouponRulesExample.createCriteria();
        criteria.andIdEqualTo(grouponRules.getId());
        grouponRulesMapper.deleteByExample(grouponRulesExample);
    }
}

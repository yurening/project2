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
    public HashMap<String,Object> queryAd(Integer page, Integer limit, String name, String content, String sort, String order) {
        PageHelper.startPage(page,limit);
        AdExample adExample = new AdExample();
        AdExample.Criteria criteria = adExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (name != null){
            criteria.andNameLike("%" + name + "%");
        }
        if (content != null){
            criteria.andContentLike("%" + name + "%");
        }
        long l = adMapper.countByExample(adExample);
        adExample.setOrderByClause(sort + " " + order);
        List<Ad> adList = adMapper.selectByExample(adExample);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("total",Math.toIntExact(l));
        hashMap.put("items",adList);
        return hashMap;
    }

    @Override
    public Ad adCreate(Ad ad) {
        Date date = new Date();
        ad.setAddTime(date);
        ad.setUpdateTime(date);
        ad.setDeleted(false);
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
        ad.setDeleted(true);
        ad.setUpdateTime(new Date());
        adMapper.updateByPrimaryKey(ad);
    }

    @Override
    public HashMap<String, Object> queryCoupon(Integer page, Integer limit, String name, Integer type, Integer status, String sort, String order) {
        PageHelper.startPage(page,limit);
        CouponExample couponExample = new CouponExample();
        CouponExample.Criteria criteria = couponExample.createCriteria();
        HashMap<String,Object> hashMap = new HashMap<>();
        if (name != null){
            criteria.andNameLike("%" + name + "%");
        }
        if (type != null){
            criteria.andTypeEqualTo(type.shortValue());
        }
        if (status !=null){
            criteria.andStatusEqualTo(status.shortValue());
        }
        criteria.andDeletedEqualTo(false);
        long l = couponMapper.countByExample(couponExample);
        couponExample.setOrderByClause(sort + " " + order);
        List<Coupon> couponList = couponMapper.selectByExample(couponExample);
        hashMap.put("total",Math.toIntExact(l));
        hashMap.put("items",couponList);
        return hashMap;
    }

    @Override
    public Coupon selectCouponById(Integer id) {
        Coupon coupon = couponMapper.selectByPrimaryKey(id);
        return coupon;
    }

    @Override
    public HashMap<String, Object> queryUserByCouponId(Integer page, Integer limit
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
        criteria.andDeletedEqualTo(false);
        long l = couponUserMapper.countByExample(couponUserExample);
        HashMap<String, Object> hashMap = new HashMap<>();
        criteria.andCouponIdEqualTo(couponId);
        couponUserExample.setOrderByClause(sort + " " + order);
        List<CouponUser> couponUsers = couponUserMapper.selectByExample(couponUserExample);
        hashMap.put("total",Math.toIntExact(l));
        hashMap.put("items",couponUsers);
        return hashMap;
    }

    @Override
    public Coupon couponCreate(Coupon coupon) {
        coupon.setDeleted(false);
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
        coupon.setDeleted(true);
        coupon.setUpdateTime(new Date());
        couponMapper.updateByPrimaryKey(coupon);
    }

    @Override
    public HashMap<String, Object> topicList(Integer page, Integer limit, String title, String subtitle, String sort, String order) {
        PageHelper.startPage(page,limit);
        TopicExample topicExample = new TopicExample();
        TopicExample.Criteria criteria = topicExample.createCriteria();
        if (title != null){
            criteria.andTitleLike("%" + title + "%");
        }
        if (subtitle != null){
            criteria.andSubtitleLike("%" + subtitle + "%");
        }
        criteria.andDeletedEqualTo(false);
        HashMap<String, Object> hashMap = new HashMap<>();
        long l = topicMapper.countByExample(topicExample);
        topicExample.setOrderByClause(sort + " " + order);
        List<Topic> topicList = topicMapper.selectByExample(topicExample);
        hashMap.put("total",Math.toIntExact(l));
        hashMap.put("items",topicList);
        return hashMap;
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
    public Topic topicUpdate(Topic topic) {
        int i = topicMapper.updateByPrimaryKey(topic);
        return topic;
    }

    @Override
    public void topicDelete(Topic topic) {
        topic.setDeleted(true);
        topic.setUpdateTime(new Date());
        topicMapper.updateByPrimaryKey(topic);
    }

    @Override
    public HashMap<String, Object> grouponList(Integer page, Integer limit, Integer goodsId, String sort, String order) {
        PageHelper.startPage(page,limit);
        HashMap<String,Object> hashMap = new HashMap<>();
        GrouponRulesExample grouponRulesExample = new GrouponRulesExample();
        GrouponRulesExample.Criteria criteria = grouponRulesExample.createCriteria();
        if (goodsId != null){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        criteria.andDeletedEqualTo(false);
        long l = grouponRulesMapper.countByExample(grouponRulesExample);
        grouponRulesExample.setOrderByClause(sort + " " + order);
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
        if (goods.size() == 0){
            return null;
        }
        Goods goods1 = goods.get(0);
        grouponRules.setDeleted(false);
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
        GrouponExample.Criteria criteria = grouponExample.createCriteria();
        if (goodsId != null){
            criteria.andIdEqualTo(goodsId);
        }
        criteria.andDeletedEqualTo(false);
        long l = grouponMapper.countByExample(grouponExample);
        grouponExample.setOrderByClause(sort + " " + order);
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
        List<Goods> goods = goodsMapper.selectByExample(goodsExample);
        if (goods.size() == 0){
            return null;
        }
        Goods g = goods.get(0);
        grouponRules.setUpdateTime(new Date());
        grouponRules.setGoodsName(g.getName());
        grouponRules.setPicUrl(g.getPicUrl());
        GrouponRulesExample grouponRulesExample = new GrouponRulesExample();
        GrouponRulesExample.Criteria criteria1 = grouponRulesExample.createCriteria();
        criteria1.andIdEqualTo(grouponRules.getId());
        int i = grouponRulesMapper.updateByExample(grouponRules, grouponRulesExample);
        return grouponRules;
    }

    @Override
    public void grouponDelete(GrouponRules grouponRules) {
        grouponRules.setDeleted(true);
        grouponRules.setUpdateTime(new Date());
        grouponRulesMapper.updateByPrimaryKey(grouponRules);
    }
}

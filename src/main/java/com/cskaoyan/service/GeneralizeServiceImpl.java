package com.cskaoyan.service;

import com.cskaoyan.bean.generalize.*;
import com.cskaoyan.bean.goods.Goods;
import com.cskaoyan.bean.goods.GoodsExample;
import com.cskaoyan.bean.goods.SystemExample;
import com.cskaoyan.bean.user.UserRequest;
import com.cskaoyan.bean.wx_index.HomeIndex;
import com.cskaoyan.mapper.*;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
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

    @Autowired
    SystemMapper systemMapper;

    @Autowired
    UserService userService;

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
            criteria.andContentLike("%" + content + "%");
        }
        adExample.setOrderByClause(sort + " " + order);
        List<Ad> adList = adMapper.selectByExample(adExample);
        int total = adList.size();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("total",total);
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
        couponExample.setOrderByClause(sort + " " + order);
        List<Coupon> couponList = couponMapper.selectByExample(couponExample);
        int total = couponList.size();
        hashMap.put("total",total);
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
        HashMap<String, Object> hashMap = new HashMap<>();
        criteria.andCouponIdEqualTo(couponId);
        couponUserExample.setOrderByClause(sort + " " + order);
        List<CouponUser> couponUsers = couponUserMapper.selectByExample(couponUserExample);
        int total = couponUsers.size();
        hashMap.put("total",total);
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
        topicExample.setOrderByClause(sort + " " + order);
        List<Topic> topicList = topicMapper.selectByExample(topicExample);
        int total = topicList.size();
        hashMap.put("total",total);
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
        grouponRulesExample.setOrderByClause(sort + " " + order);
        List<GrouponRules> grouponRulesList = grouponRulesMapper.selectByExample(grouponRulesExample);
        int total = grouponRulesList.size();
        hashMap.put("total",total);
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
        } else if ( grouponRules.getDiscount().intValue() > goods.get(0).getRetailPrice().intValue()){
           grouponRules.setGoodsId(0);
           return grouponRules;
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
        List<HashMap> hashMapList = new ArrayList<>();
        GrouponExample grouponExample = new GrouponExample();
        GrouponExample.Criteria criteria = grouponExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        grouponExample.setOrderByClause("groupon_id" + " " + order);
        List<Groupon> grouponList = grouponMapper.selectByExample(grouponExample);
        List<GrouponRules> grouponRulesList = new ArrayList<>();
        List<Goods> goodsList = new ArrayList<>();
        if (grouponList.size() > 0 ) {
            List<Integer> rulesIds = new ArrayList<>();
            List<Integer> goodsIds = new ArrayList<>();
            for (Groupon groupon : grouponList) {
                rulesIds.add(groupon.getRulesId());
            }
            GrouponRulesExample grouponRulesExample = new GrouponRulesExample();
            GrouponRulesExample.Criteria criteriaRules = grouponRulesExample.createCriteria();
            criteriaRules.andIdIn(rulesIds);
            if (goodsId != null) {
                criteriaRules.andGoodsIdEqualTo(goodsId);
            }
            grouponRulesList = grouponRulesMapper.selectByExample(grouponRulesExample);
            if (grouponRulesList.size() > 0) {
                for (GrouponRules grouponRules : grouponRulesList) {
                    Integer goodsId1 = grouponRules.getGoodsId();
                    goodsIds.add(goodsId1);
                }
                GoodsExample goodsExample = new GoodsExample();
                GoodsExample.Criteria criteriaGoods = goodsExample.createCriteria();
                criteriaGoods.andIdIn(goodsIds);
                goodsList = goodsMapper.selectByExample(goodsExample);
                Integer id = 0;
                HashMap<String, Object> hashMap = new HashMap<>();
                List<HashMap> subGroupons = new ArrayList<>();
                for (Groupon groupon : grouponList) {
                    if (!groupon.getGrouponId().equals(id)) {
                        hashMap.put("subGroupons", subGroupons);
                        hashMapList.add(hashMap);
                        subGroupons = new ArrayList<>();
                        hashMap = new HashMap<>();
                        id = groupon.getGrouponId();
                        hashMap.put("groupon", groupon);
                        Integer rulesId = groupon.getRulesId();
                        for (GrouponRules grouponRules : grouponRulesList) {
                            if (grouponRules.getId() == rulesId) {
                                hashMap.put("rules", grouponRules);
                                for (Goods goods : goodsList) {
                                    if (grouponRules.getGoodsId().equals(goods.getId())) {
                                        hashMap.put("goods", goods);
                                    }
                                }
                            }
                        }
                        HashMap<String, Object> subGroupon = new HashMap<>();
                        subGroupon.put("orderId", groupon.getOrderId());
                        subGroupon.put("userId", groupon.getUserId());
                        subGroupons.add(subGroupon);
                    } else {
                        HashMap<String, Object> subGroupon = new HashMap<>();
                        subGroupon.put("orderId", groupon.getOrderId());
                        subGroupon.put("userId", groupon.getUserId());
                        subGroupons.add(subGroupon);
                    }
                }
                hashMap.put("subGroupons", subGroupons);
                hashMapList.add(hashMap);
            }
        }
            /*for (Groupon groupon : grouponList) {

                List<HashMap> subGroupons = new ArrayList<>();
                Integer rulesId = groupon.getRulesId();
                GrouponRulesExample grouponRulesExample = new GrouponRulesExample();
                GrouponRulesExample.Criteria criteriaRules = grouponRulesExample.createCriteria();
                criteriaRules.andIdEqualTo(rulesId);
                if (goodsId != null) {
                    criteriaRules.andGoodsIdEqualTo(goodsId);
                }
                List<GrouponRules> grouponRulesList = grouponRulesMapper.selectByExample(grouponRulesExample);
                if (grouponRulesList.size() == 0) {
                    continue;
                }
                GrouponRules grouponRules = grouponRulesList.get(0);
                Integer goodsId1 = grouponRules.getGoodsId();
                GoodsExample goodsExample = new GoodsExample();
                GoodsExample.Criteria criteriaGoods = goodsExample.createCriteria();
                criteriaGoods.andIdEqualTo(goodsId1);
                List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
                HashMap<String, Object> h = new HashMap<>();
                h.put("groupon", groupon);
                h.put("goods", goodsList.get(0));
                h.put("rules", grouponRules);
                Integer grouponId = groupon.getGrouponId();

                hashMapList.add(h);
            }
        }*/
        if (hashMapList.size() > 0) {
            hashMapList.remove(0);
        }
        int total = hashMapList.size();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("total",total);
        List<HashMap> hashMaps = new ArrayList<>();
        for (int i = 0; i < limit.intValue();i++){
            if (hashMapList.size() > (page.intValue() - 1 ) * limit.intValue() + i){
                hashMaps.add(hashMapList.get((page.intValue() - 1) * limit.intValue() + i));
            }
        }
        hashMap.put("items",hashMaps);
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
        } else if ( grouponRules.getDiscount().intValue() > goods.get(0).getRetailPrice().intValue()){
            grouponRules.setGoodsId(0);
            return grouponRules;
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

    @Override
    public List<HomeIndex.CouponListBean> getCouponList() {
        List<Coupon> coupons;
        if (SecurityUtils.getSubject().getPrincipal() == null) {
            CouponExample couponExample = new CouponExample();
            couponExample.createCriteria().andDeletedEqualTo(false);
            coupons = couponMapper.selectByExample(couponExample);
        } else {
            UserRequest request = new UserRequest();
            request.setPage(0);
            request.setLimit(4);
            coupons = userService.selectCoupon(request);
        }
        List<HomeIndex.CouponListBean> couponList = new ArrayList<>();
        for (Coupon coupon : coupons) {
            HomeIndex.CouponListBean couponListBean = new HomeIndex.CouponListBean();
            couponListBean.setDays(coupon.getDays());
            couponListBean.setDesc(coupon.getDesc());
            couponListBean.setDiscount(coupon.getDiscount());
            couponListBean.setEndTime(coupon.getEndTime());
            couponListBean.setId(coupon.getId());
            couponListBean.setMin(coupon.getMin());
            couponListBean.setName(coupon.getName());
            couponListBean.setStartTime(coupon.getStartTime());
            couponListBean.setTag(coupon.getTag());
            couponList.add(couponListBean);
        }
        return couponList;
    }

    @Override
    public List<HomeIndex.BannerBean> getBanner() {
        AdExample adExample = new AdExample();
        adExample.createCriteria().andDeletedEqualTo(false);
        List<Ad> ads = adMapper.selectByExample(adExample);
        List<HomeIndex.BannerBean> bannerList = new ArrayList<>();
        for (Ad ad : ads) {
            HomeIndex.BannerBean bannerBean = new HomeIndex.BannerBean();
            bannerBean.setId(ad.getId());
            bannerBean.setName(ad.getName());
            bannerBean.setLink(ad.getLink());
            bannerBean.setUrl(ad.getUrl());
            bannerBean.setPosition(ad.getPosition());
            bannerBean.setContent(ad.getContent());
            bannerBean.setEnabled(ad.getEnabled());
            bannerBean.setAddTime(ad.getAddTime());
            bannerBean.setUpdateTime(ad.getUpdateTime());
            bannerBean.setDeleted(false);
            bannerList.add(bannerBean);
        }
        return bannerList;
    }

    @Override
    public List<HomeIndex.TopicListBean> getTopicList() {
        SystemExample systemExample = new SystemExample();
        systemExample.createCriteria().andKeyNameEqualTo("cskaoyan_mall_wx_index_topic");
        int limit = Integer.parseInt(systemMapper.selectByExample(systemExample).get(0).getKeyValue());
        TopicExample topicExample = new TopicExample();
        topicExample.createCriteria().andDeletedEqualTo(false);
        List<Topic> topics = topicMapper.selectByExample(topicExample);
        if (topics.size() > limit) {
            topics = topics.subList(0, limit);
        }
        List<HomeIndex.TopicListBean> topicList = new ArrayList<>();
        for (Topic topic : topics) {
            HomeIndex.TopicListBean topicListBean = new HomeIndex.TopicListBean();
            topicListBean.setId(topic.getId());
            topicListBean.setTitle(topic.getTitle());
            topicListBean.setSubtitle(topic.getSubtitle());
            topicListBean.setPrice(topic.getPrice());
            topicListBean.setReadCount(topic.getReadCount());
            topicListBean.setPicUrl(topic.getPicUrl());
            topicList.add(topicListBean);
        }
        return topicList;
    }

    @Override
    public List<HomeIndex.GrouponListBean> getGrouponList() {
        GrouponRulesExample grouponRulesExample = new GrouponRulesExample();
        grouponRulesExample.createCriteria().andDeletedEqualTo(false);
        List<GrouponRules> grouponRules = grouponRulesMapper.selectByExample(grouponRulesExample);
        List<HomeIndex.GrouponListBean> grouponList = new ArrayList<>();
        for (GrouponRules grouponRule : grouponRules) {
            GoodsExample goodsExample = new GoodsExample();
            goodsExample.createCriteria().andIdEqualTo(grouponRule.getGoodsId()).andDeletedEqualTo(false);
            List<Goods> goods = goodsMapper.selectByExample(goodsExample);
            Goods simpleGoods = goods.get(0);
            HomeIndex.GrouponListBean grouponListBean = new HomeIndex.GrouponListBean();
            grouponListBean.setGroupon_member(grouponRule.getDiscountMember());
            grouponListBean.setGroupon_price(simpleGoods.getRetailPrice().subtract(grouponRule.getDiscount()));
            HomeIndex.GrouponListBean.GoodsBean g = new HomeIndex.GrouponListBean.GoodsBean();
            g.setId(simpleGoods.getId());
            g.setName(simpleGoods.getName());
            g.setBrief(simpleGoods.getBrief());
            g.setPicUrl(simpleGoods.getPicUrl());
            g.setCounterPrice(simpleGoods.getCounterPrice());
            g.setRetailPrice(simpleGoods.getRetailPrice());
            grouponListBean.setGoods(g);
            grouponList.add(grouponListBean);
        }
        return grouponList;
    }
}

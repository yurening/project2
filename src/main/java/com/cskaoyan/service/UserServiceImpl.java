package com.cskaoyan.service;

import com.cskaoyan.bean.generalize.*;
import com.cskaoyan.bean.goods.Goods;
import com.cskaoyan.bean.goods.GoodsExample;
import com.cskaoyan.bean.mall.keyword.MallKeyword;
import com.cskaoyan.bean.mall.keyword.MallKeywordExample;
import com.cskaoyan.bean.user.*;
import com.cskaoyan.mapper.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;

    @Autowired
    MallKeywordMapper mallKeywordMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    GrouponRulesMapper grouponRulesMapper;

    @Autowired
    CouponMapper couponMapper;

    @Autowired
    CouponUserMapper couponUserMapper;

    @Autowired
    CartMapper cartMapper;

    @Override
    public long countUserByExample(UserExample example) {
        return userMapper.countUserByExample(example);
    }

    @Override
    public List<User> selectUserByExample(UserRequest userRequest, UserExample example) {
        PageHelper.startPage(userRequest.getPage(),userRequest.getLimit());
        return userMapper.selectUserByExample(example);
    }

    //address
    public List<Address> selectAddress(AddressRequest addressRequest,AddressExample addressExample){
        PageHelper.startPage(addressRequest.getPage(),addressRequest.getLimit());
        List<Address> lists = userMapper.selectAddressByExample(addressExample);
        for (Address list : lists) {
            String province = userMapper.selectNameById(list.getProvinceId());
            String city = userMapper.selectNameById(list.getCityId());
            String area = userMapper.selectNameById(list.getAreaId());
            list.setProvince(province);
            list.setCity(city);
            list.setArea(area);
        }
        return lists;
    }

    @Override
    public long countAddressByExample(AddressExample example) {
        long l = userMapper.countAddressByExample(example);
        return l;
    }

    @Override
    public long countCollectByExample(CollectExample example) {
        return userMapper.countCollectByExample(example);
    }

    @Override
    public List<Collect> selectCollectByExample(CollectRequest collectRequest, CollectExample example) {
        PageHelper.startPage(collectRequest.getPage(),collectRequest.getLimit());
        List<Collect> collects = userMapper.selectCollectByExample(example);
        return collects;
    }

    @Override
    public long countFootprintByExample(FootPrintExample example) {
        return userMapper.countFootprintByExample(example);
    }

    @Override
    public List<FootPrint> selectFootprintByExample(FootPrintRequest footPrintRequest,FootPrintExample example) {
        PageHelper.startPage(footPrintRequest.getPage(),footPrintRequest.getLimit());
        List<FootPrint> footPrints = userMapper.selectFootprintByExample(example);
        return footPrints;
    }

    //history
    @Override
    public long countHistoryByExample(HistoryExample example) {
        return userMapper.countHistoryByExample(example);
    }

    @Override
    public List<History> selectHistoryByExample(HistoryRequest historyRequest, HistoryExample example) {
        PageHelper.startPage(historyRequest.getPage(),historyRequest.getLimit());
        List<History> histories = userMapper.selectHistoryByExample(example);
        return histories;
    }

    @Override
    public long countFeedbackByExample(FeedbackExample example) {
        return userMapper.countFeedbackByExample(example);
    }

    @Override
    public List<Feedback> selectFeedbackByExample(FeedBackRequest feedBackRequest, FeedbackExample example) {
        PageHelper.startPage(feedBackRequest.getPage(),feedBackRequest.getLimit());
        return userMapper.selectFeedbackByExample(example);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public Map<String, Object> selectSearchIndex() {
        Map<String, Object> objectHashMap = new HashMap<>();
        MallKeywordExample mallKeywordExample = new MallKeywordExample();
        HistoryExample historyExample = new HistoryExample();
        List<History> histories = userMapper.selectHistoryByExample(historyExample);
        List<MallKeyword> mallKeywords = mallKeywordMapper.selectByExample(mallKeywordExample);
        mallKeywordExample.createCriteria().andSortOrderEqualTo(1);
        List<MallKeyword> mallKeywords1 = mallKeywordMapper.selectByExample(mallKeywordExample);
        objectHashMap.put("defaultKeyword",mallKeywords1.get(0));
        objectHashMap.put("hotKeywordList",mallKeywords);
        objectHashMap.put("historyKeywordList",histories);
        return objectHashMap;
    }

    @Override
    public List<String> searchHelper(String keyword) {
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andNameLike("%"+keyword+"%");
        List<Goods> goods = goodsMapper.selectByExample(goodsExample);
        ArrayList<String> objects = new ArrayList<>();
        for (Goods good : goods) {
            objects.add(good.getName());
        }
        return objects;
    }

    @Override
    public Map selectGroupon(UserRequest userRequest) {
        PageHelper.startPage(userRequest.getPage(),userRequest.getLimit());
        List<GrouponRules> grouponRules = grouponRulesMapper.selectByExample(new GrouponRulesExample());
        ArrayList<Object> lists = new ArrayList<>();
        for (GrouponRules grouponRule : grouponRules) {
            HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
            Goods goods = goodsMapper.selectByPrimaryKey(grouponRule.getGoodsId());
            objectObjectHashMap.put("groupon_price",goods.getRetailPrice().subtract(grouponRule.getDiscount()));
            objectObjectHashMap.put("goods",goods);
            objectObjectHashMap.put("groupon_member",grouponRule.getDiscountMember());
            lists.add(objectObjectHashMap);
        }
        HashMap<Object, Object> returnMap = new HashMap<>();
        returnMap.put("data",lists);
        System.out.println(lists);
        long l = grouponRulesMapper.countByExample(new GrouponRulesExample());
        returnMap.put("count",l);
        return returnMap;
    }

    @Override
    public Map selectCoupon(UserRequest userRequest) {
        PageHelper.startPage(userRequest.getPage(),userRequest.getLimit());
        List<Coupon> coupons = couponMapper.selectByExample(new CouponExample());
        long l = couponMapper.countByExample(new CouponExample());
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("data",coupons);
        objectObjectHashMap.put("count",l);
        return objectObjectHashMap;
    }

    @Override
    public ReturnData couponMyList(CouponRequest couponRequest) {
        PageHelper.startPage(couponRequest.getPage(),couponRequest.getSize());
        CouponUserExample couponUserExample = new CouponUserExample();
        couponUserExample.createCriteria().andUserIdEqualTo(1).andStatusEqualTo(couponRequest.getStatus());
        List<CouponUser> couponUsers = couponUserMapper.selectByExample(couponUserExample);
        List<Coupon> coupons = new ArrayList<>();
        for (CouponUser couponUser : couponUsers) {
            coupons.add(couponMapper.selectByPrimaryKey(couponUser.getCouponId()));
        }
        ReturnData returnData = new ReturnData();
        returnData.setData(coupons);
        returnData.setCount((int)couponUserMapper.countByExample(couponUserExample));
        return returnData;
    }

    @Override
    public int couponReceive(CouponRequest couponRequest) {
        CouponUser couponUser = new CouponUser();
        couponUser.setUserId(1);
        couponUser.setCouponId(couponRequest.getCouponId());
        couponUser.setStatus((short)0);
        Coupon coupon = couponMapper.selectByPrimaryKey(couponRequest.getCouponId());
        couponUser.setStartTime(coupon.getStartTime());
        couponUser.setEndTime(coupon.getEndTime());
        couponUser.setAddTime(new Date());
        couponUser.setUpdateTime(new Date());
        couponUserMapper.insert(couponUser);
        return 1;
    }

    @Override
    public ReturnData couponSelectList(CouponRequest couponRequest) {
        Cart cart = cartMapper.selectByPrimaryKey(couponRequest.getCartId());
        CouponUserExample couponUserExample = new CouponUserExample();
        couponUserExample.createCriteria().andUserIdEqualTo(cart.getGoodsId()).andStatusEqualTo((short)0);
        List<CouponUser> couponUsers = couponUserMapper.selectByExample(couponUserExample);
        List<Coupon> coupons = new ArrayList<>();
        for (CouponUser couponUser : couponUsers) {
            coupons.add(couponMapper.selectByPrimaryKey(couponUser.getCouponId()));
        }
        ReturnData returnData = new ReturnData();
        returnData.setData(coupons);
        returnData.setCount((int)couponUserMapper.countByExample(couponUserExample));
        return returnData;
    }

    @Override
    public int couponExchange(CouponRequest couponRequest) {
        CouponExample couponExample = new CouponExample();
        couponExample.createCriteria().andCodeEqualTo(couponRequest.getCode());
        List<Coupon> coupons = couponMapper.selectByExample(couponExample);
        if (coupons.isEmpty()){
            return 0;
        }
        CouponUser couponUser = new CouponUser();
        couponUser.setUserId(1);
        couponUser.setCouponId(coupons.get(0).getId());
        couponUser.setStatus((short)0);
        couponUser.setStartTime(coupons.get(0).getStartTime());
        couponUser.setEndTime(coupons.get(0).getEndTime());
        couponUser.setAddTime(new Date());
        couponUser.setUpdateTime(new Date());
        couponUserMapper.insert(couponUser);
        return 1;
    }


}

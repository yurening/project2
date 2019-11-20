package com.cskaoyan.service;

import com.cskaoyan.bean.user.FootPrint;
import com.cskaoyan.bean.user.FootPrintExample;
import com.cskaoyan.bean.user.Feedback;
import com.cskaoyan.bean.user.FeedbackExample;
import com.cskaoyan.bean.user.Collect;
import com.cskaoyan.bean.user.CollectExample;
import com.cskaoyan.bean.generalize.*;
import com.cskaoyan.bean.goods.Goods;
import com.cskaoyan.bean.goods.GoodsExample;
import com.cskaoyan.bean.mall.keyword.MallKeyword;
import com.cskaoyan.bean.mall.keyword.MallKeywordExample;
import com.cskaoyan.bean.mall.order.MallOrder;
import com.cskaoyan.bean.user.*;
import com.cskaoyan.bean.user.groupon.GrouponDetail;
import com.cskaoyan.mapper.*;
import com.cskaoyan.teacherCode.UserTokenManager;
import com.cskaoyan.utils.TransferDateUtils;
import com.cskaoyan.utils.TransferUtils_wx;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    GrouponMapper grouponMapper;

    @Autowired
    StatusMapper statusMapper;

    @Autowired
    MallOrderMapper mallOrderMapper;

    @Autowired
    OrderGoodsMapper orderGoodsMapper;

    @Autowired
    GoodsAttributeMapper goodsAttributeMapper;

    @Autowired
    SearchHistoryMapper searchHistoryMapper;

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
    public List<FootPrint> selectFootprintByExample(FootPrintRequest footPrintRequest, FootPrintExample example) {
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
    public void updateLoginTime(Integer id) {
        userMapper.updateLoginTime(id);
    }

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
    public int searchClearHistory(HttpServletRequest request) {
        //删除search_history表中，所有userID = 该用户id
        //通过sessionManager获得id
        Integer userId = UserTokenManager.getUserId(request.getHeader("X-Litemall-Token"));
        SearchHistoryExample searchHistoryExample = new SearchHistoryExample();
        searchHistoryExample.createCriteria().andUserIdEqualTo(userId);
        searchHistoryMapper.deleteByExample(searchHistoryExample);

        return 0;
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
    public GouponMy.DataBeanX grouponMy(int showType) {
        GouponMy.DataBeanX dataBeanX = new GouponMy.DataBeanX();
        List<Groupon> groupons = null;
        if(showType==0){
            GrouponExample grouponExample = new GrouponExample();
            grouponExample.createCriteria().andCreatorUserIdEqualTo(1);
            groupons = grouponMapper.selectByExample(grouponExample);
        }
        if(showType==1){
            GrouponExample grouponExample = new GrouponExample();
            grouponExample.createCriteria().andUserIdEqualTo(1);
            groupons = grouponMapper.selectByExample(grouponExample);
        }
        ArrayList<GouponMy.DataBeanX.DataBean> data = new ArrayList<>();
        for (Groupon groupon : groupons) {
            GouponMy.DataBeanX.DataBean dataBean = new GouponMy.DataBeanX.DataBean();
            MallOrder mallOrder = mallOrderMapper.selectByPrimaryKey(groupon.getOrderId());
            StatusExample statusExample = new StatusExample();
            statusExample.createCriteria().andStatusIdEqualTo((int)mallOrder.getOrderStatus());
            List<Status> statuses = statusMapper.selectByExample(statusExample);
            dataBean.setOrderStatusText(statuses.get(0).getStatusText());
            //获得creator
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(groupon.getUserId());
            List<User> users = userMapper.selectUserByExample(userExample);
            dataBean.setCreator(users.get(0).getNickname());
            //设置groupon
            dataBean.setGroupon(groupon);
            //设置orderId
            dataBean.setOrderId(groupon.getOrderId());
            //设置orderSn
            dataBean.setOrderSn(mallOrder.getOrderSn());
            //设置actualPrice
            dataBean.setActualPrice(mallOrder.getActualPrice().doubleValue());
            //设置joinerCount
            GrouponExample grouponExample = new GrouponExample();
            grouponExample.createCriteria().andGrouponIdEqualTo(groupon.getGrouponId());
            long l = grouponMapper.countByExample(grouponExample);
            dataBean.setJoinerCount((int)l);
            //设置rules
            GrouponRules grouponRules = grouponRulesMapper.selectByPrimaryKey(groupon.getRulesId());
            dataBean.setRules(grouponRules);
            //设置id
            dataBean.setId(groupon.getId());
            //设置isCreator
            if(groupon.getUserId()==groupon.getCreatorUserId()){
                dataBean.setIsCreator(true);
            }else {
                dataBean.setIsCreator(false);
            }
            //设置handleOption
            GouponMy.DataBeanX.DataBean.HandleOptionBean handleOptionBean = new GouponMy.DataBeanX.DataBean.HandleOptionBean();
            switch (mallOrder.getOrderStatus()){
                case 102:
                    handleOptionBean.setDelete(true);
                    break;
                case 103:
                    handleOptionBean.setDelete(true);
                    break;
                case 201:
                    handleOptionBean.setPay(true);
                    break;
                case 202:
                    handleOptionBean.setRefund(true);
                    break;
                case 203:
                    handleOptionBean.setRefund(true);
                    break;
                case 401:
                    handleOptionBean.setConfirm(true);
                    break;
                case 402:
                    handleOptionBean.setConfirm(true);
                    break;
            }
            if(mallOrder.getComments()!=null){
                handleOptionBean.setComment(true);
            }
            handleOptionBean.setRebuy(false);
            //设置goodsList
            OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
            orderGoodsExample.createCriteria().andOrderIdEqualTo(groupon.getOrderId());
            List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(orderGoodsExample);
            List<GouponMy.DataBeanX.DataBean.GoodsListBean> listBeans = new ArrayList<>();
            for (OrderGoods orderGood : orderGoods) {
                GouponMy.DataBeanX.DataBean.GoodsListBean goodsListBean1 = new GouponMy.DataBeanX.DataBean.GoodsListBean();
                goodsListBean1.setGoodsName(orderGood.getGoodsName());
                goodsListBean1.setId(orderGood.getId());
                goodsListBean1.setNumber(orderGood.getNumber());
                goodsListBean1.setPicUrl(orderGood.getPicUrl());
                listBeans.add(goodsListBean1);
            }
            dataBean.setGoodsList(listBeans);
            data.add(dataBean);
        }
        dataBeanX.setData(data);
        dataBeanX.setCount(groupons.size());
        return dataBeanX;
    }

    @Override
    public GrouponDetail.DataBean grouponDetail(int grouponId) {
        GrouponDetail.DataBean dataBean = new GrouponDetail.DataBean();
        Groupon groupon = grouponMapper.selectByPrimaryKey(grouponId);
        //creator
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(groupon.getCreatorUserId());
        List<User> users = userMapper.selectUserByExample(userExample);
        User user = users.get(0);
        GrouponDetail.DataBean.CreatorBean creatorBean = new GrouponDetail.DataBean.CreatorBean();
        creatorBean.setAvatar(user.getAvatar());
        creatorBean.setNickname(user.getNickname());
        dataBean.setCreator(creatorBean);
        //groupon
        dataBean.setGroupon(groupon);
        //orderInfo
        GrouponDetail.DataBean.OrderInfoBean orderInfoBean = new GrouponDetail.DataBean.OrderInfoBean();
        //从groupon中取出orderid 在order表中查询，
        MallOrder mallOrder = mallOrderMapper.selectByPrimaryKey(groupon.getOrderId());
        orderInfoBean.setConsignee(mallOrder.getConsignee());
        orderInfoBean.setAddress(mallOrder.getAddress());
        orderInfoBean.setAddTime(TransferUtils_wx.date2String(mallOrder.getAddTime()));
        orderInfoBean.setOrderSn(mallOrder.getOrderSn());
        orderInfoBean.setMobile(mallOrder.getMobile());
        orderInfoBean.setId(mallOrder.getId());
        orderInfoBean.setFreightPrice(mallOrder.getFreightPrice().doubleValue());
        //从order表中取出orderStatus,在order_status表中查出orderStatusText
        StatusExample statusExample = new StatusExample();
        statusExample.createCriteria().andStatusIdEqualTo((int)mallOrder.getOrderStatus());
        List<Status> statuses = statusMapper.selectByExample(statusExample);
        orderInfoBean.setOrderStatusText(statuses.get(0).getStatusText());
        orderInfoBean.setGoodsPrice(mallOrder.getGoodsPrice().doubleValue());
        //处理handleOption
        GrouponDetail.DataBean.OrderInfoBean.HandleOptionBean handleOptionBean = new GrouponDetail.DataBean.OrderInfoBean.HandleOptionBean();
        switch (mallOrder.getOrderStatus()){
            case 102:
                handleOptionBean.setDelete(true);
                break;
            case 103:
                handleOptionBean.setDelete(true);
                break;
            case 201:
                handleOptionBean.setPay(true);
                break;
            case 202:
                handleOptionBean.setRefund(true);
                break;
            case 203:
                handleOptionBean.setRefund(true);
                break;
            case 401:
                handleOptionBean.setConfirm(true);
                break;
            case 402:
                handleOptionBean.setConfirm(true);
                break;
        }
        if(mallOrder.getComments()!=null){
            handleOptionBean.setComment(true);
        }
        handleOptionBean.setRebuy(false);
        orderInfoBean.setHandleOption(handleOptionBean);
        dataBean.setOrderInfo(orderInfoBean);

        //rules,根据rulesId,查询groupon_rule
        GrouponRules grouponRules = grouponRulesMapper.selectByPrimaryKey(groupon.getRulesId());
        dataBean.setRules(grouponRules);
        //linkGrouponId
        dataBean.setLinkGrouponId(grouponId);
        //joiners 同一个团购的用户有相同的rulesId,查询出同一个团购的Groupon
        GrouponExample grouponExample = new GrouponExample();
        grouponExample.createCriteria().andRulesIdEqualTo(groupon.getRulesId());
        List<Groupon> groupons = grouponMapper.selectByExample(grouponExample);
        //根据Groupon的userId，查询出Joinser
        List<GrouponDetail.DataBean.JoinersBean> joinersBeans = new ArrayList<>();
        for (Groupon groupon1 : groupons) {
            UserExample userExample1 = new UserExample();
            userExample1.createCriteria().andIdEqualTo(groupon1.getUserId());
            List<User> users1 = userMapper.selectUserByExample(userExample1);
            for (User user1 : users1) {
                GrouponDetail.DataBean.JoinersBean joinersBean = new GrouponDetail.DataBean.JoinersBean();
                joinersBean.setAvatar(user1.getAvatar());
                joinersBean.setNickname(user1.getNickname());
                joinersBeans.add(joinersBean);
            }
        }
        dataBean.setJoiners(joinersBeans);
        //orderGoods
        //根据orderId从order_goods表中查出数据，然后封装到orderGoodsBean中
        OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
        orderGoodsExample.createCriteria().andOrderIdEqualTo(groupon.getOrderId());
        List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(orderGoodsExample);
        List<GrouponDetail.DataBean.OrderGoodsBean> orderGoodsBeans = new ArrayList<>();
        for (OrderGoods orderGood : orderGoods) {
            GrouponDetail.DataBean.OrderGoodsBean orderGoodsBean = new GrouponDetail.DataBean.OrderGoodsBean();
            orderGoodsBean.setNumber(orderGood.getNumber());
            orderGoodsBean.setPicUrl(orderGood.getPicUrl());
            orderGoodsBean.setOrderId(orderGood.getOrderId());
            orderGoodsBean.setGoodsId(orderGood.getGoodsId());
            orderGoodsBean.setId(orderGood.getId());
            orderGoodsBean.setGoodsName(orderGood.getGoodsName());
            //retailPrice需要从goods表中查询
            Goods goods = goodsMapper.selectByPrimaryKey(orderGood.getGoodsId());
            orderGoodsBean.setRetailPrice(goods.getRetailPrice().doubleValue());
            //根据goods_id,从goods_attribute表中查询出value
            List<String> lists = new ArrayList<>();
            GoodsAttributeExample goodsAttributeExample = new GoodsAttributeExample();
            goodsAttributeExample.createCriteria().andGoodsIdEqualTo(orderGood.getGoodsId());
            List<GoodsAttribute> goodsAttributes = goodsAttributeMapper.selectByExample(goodsAttributeExample);
            for (GoodsAttribute goodsAttribute : goodsAttributes) {
                lists.add(goodsAttribute.getValue());
            }
            orderGoodsBean.setGoodsSpecificationValues(lists);
            orderGoodsBeans.add(orderGoodsBean);
        }
        dataBean.setOrderGoods(orderGoodsBeans);
        return dataBean;
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
        //在coupon_user表中，根据是否有coupon_id=该优惠券id，判断是否已经领取
        CouponUserExample couponUserExample = new CouponUserExample();
        couponUserExample.createCriteria().andUserIdEqualTo(1).andCouponIdEqualTo(couponRequest.getCouponId());
        long l = couponUserMapper.countByExample(couponUserExample);
        if(l>0){
            return 0;
        }
        //判断优惠券是否已领完，领完时不能再领
        //从coupon中取出total，当total为0时，可以无限领取，当不为零时，已领取的优惠券张数要小于total
        Coupon coupon1 = couponMapper.selectByPrimaryKey(couponRequest.getCouponId());
        String total = coupon1.getTotal();
        //根据coupon_id,通过统计coupon_user中已领优惠券张数，当张数等于total总数时，不能在领取
        if(Integer.parseInt(total)!=0){
            CouponUserExample couponUserExample1 = new CouponUserExample();
            couponUserExample1.createCriteria().andCouponIdEqualTo(couponRequest.getCouponId());
            long l1 = couponUserMapper.countByExample(couponUserExample1);
            if(Integer.parseInt(total)<=l1){
                return 2;
            }
        }
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
    public int couponExchange(CouponRequest couponRequest, HttpServletRequest request) {
        Integer userId = UserTokenManager.getUserId(request.getHeader("X-Litemall-Token"));
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

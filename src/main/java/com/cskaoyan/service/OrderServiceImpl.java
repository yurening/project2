package com.cskaoyan.service;
import com.cskaoyan.bean.generalize.Coupon;
import com.cskaoyan.bean.generalize.Groupon;
import com.cskaoyan.bean.generalize.GrouponRules;
import com.cskaoyan.bean.mall.address.MallAddress;
import com.cskaoyan.bean.mall.coupon.MallCoupon;
import com.cskaoyan.bean.mall.wx_order.WxHandleOption;
import java.util.Date;
import com.cskaoyan.bean.mall.order.MallOrderGoods;
import com.cskaoyan.bean.mall.order.MallOrderGoodsExample;
import com.cskaoyan.bean.mall.wx_order.*;

import com.cskaoyan.bean.mall.BaseListInfo;
import com.cskaoyan.bean.mall.order.MallOrder;
import com.cskaoyan.bean.mall.order.MallOrderExample;
import com.cskaoyan.bean.user.Cart;
import com.cskaoyan.bean.user.CartExample;
import com.cskaoyan.mapper.*;
import com.cskaoyan.needdelete.UserTokenManager;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    MallOrderMapper mallOrderMapper;
    @Autowired
    MallOrderGoodsMapper mallOrderGoodsMapper;
    @Autowired
    MallAddressMapper addressMapper;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    MallCouponMapper couponMapper;
    @Autowired
    GrouponRulesMapper grouponRulesMapper;
    @Autowired
    GrouponMapper grouponMapper;

    /**
     * 获取微信订单
     * 全部，未付款，已付款，待收货，评价
     * @param showType
     * @param page
     * @param size
     * @param token
     * @return
     */
    @Override
    public BaseListInfo<WxOrder> getWxOrderList(Integer showType, Integer page, Integer size, String token) {
        //获取用户id
        Integer userId = UserTokenManager.getUserId(token);
        //分页查询
        PageHelper.startPage(page, size);
        BaseListInfo<WxOrder> baseListInfo = new BaseListInfo<>();

        //查到订单信息，写到微信订单中
        List<WxOrder> wxOrders = new ArrayList<>();
        MallOrderExample example = new MallOrderExample();
        MallOrderExample.Criteria criteria = example.createCriteria();
       /* if(orderStatus!=null) {
            criteria.andOrderStatusEqualTo(orderStatus);
        }*/
        criteria.andUserIdEqualTo(userId);
        List<MallOrder> mallOrders = mallOrderMapper.selectByExample(example);
        for (MallOrder x:mallOrders) {
            WxOrder wxOrder = new WxOrder();
            wxOrder.setOrderStatusText(getWxOrderStatusText(x.getOrderStatus()));
            wxOrder.setIsGroupin(false);
            wxOrder.setOrderSn(x.getOrderSn());
            BigDecimal actualPrice = x.getActualPrice();
            wxOrder.setActualPrice(actualPrice.intValue());
            wxOrder.setId(x.getId());
            wxOrder.setHandleOption(new WxHandleOption(x.getOrderStatus()));
            wxOrder.setGoodsList(getWxGoodsByOrderId(x.getId()));
            wxOrders.add(wxOrder);
        }

        //根据showType对wxOrders进行删减
        wxOrders = changeWxOrders(wxOrders,showType);
        baseListInfo.setData(wxOrders);

        PageInfo<MallOrder> pageInfo = new PageInfo<>(mallOrders);
        baseListInfo.setCount((int) pageInfo.getTotal());
        baseListInfo.setTotalPages(pageInfo.getPages());

        return baseListInfo;
    }

    private List<WxOrder> changeWxOrders(List<WxOrder> wxOrders, Integer showType) {
        if(showType!=null&&showType!=0){
            int i = showType.intValue()*100+1;
            String orderStatusText = getWxOrderStatusText((short) i);
            List<WxOrder> newOrders = new ArrayList<>();
            for(WxOrder x:wxOrders){
                String orderStatusText1 = x.getOrderStatusText();
                if(orderStatusText1.equals(orderStatusText)){
                    newOrders.add(x);
                }
            }
            return newOrders;
        }
        return wxOrders;
    }

    private List<WxGoods> getWxGoodsByOrderId(Integer id) {
        MallOrderGoodsExample mallOrderGoodsExample = new MallOrderGoodsExample();
        mallOrderGoodsExample.createCriteria().andOrderIdEqualTo(id);
        List<MallOrderGoods> mallOrderGoods = mallOrderGoodsMapper.selectByExample(mallOrderGoodsExample);
        List<WxGoods> wxGoods = new ArrayList<>();
        for(MallOrderGoods x: mallOrderGoods){
            WxGoods wxGoods1 = new WxGoods();
            wxGoods1.setNumber(x.getNumber());
            wxGoods1.setPicUrl(x.getPicUrl());
            wxGoods1.setId(x.getId());
            wxGoods1.setComment(x.getComment());
            wxGoods1.setGoodsName(x.getGoodsName());
            wxGoods.add(wxGoods1);
        }
        return wxGoods;
    }

    private String getWxOrderStatusText(Short orderStatus) {
        switch (orderStatus){
            case 101:
                return "未付款";
            case 102:
                return "用户取消";
            case 103:
                return "系统取消";
            case 201:
                return "已付款";
            case 202:
                return "申请退款";
            case 203:
                return "已退款";
            case 301:
                return "已发货";
            case 401:
                return "用户收货";
            case 402:
                return "系统收货";
            default:
                return null;
        }
    }

    /**
     * 显示订单详情
     * @param orderId
     * @return
     */
    @Override
    public WxOrderDetail getOrderDetail(Integer orderId) {
        WxOrderDetail wxOrderDetail = new WxOrderDetail();

        MallOrderGoodsExample mallOrderGoodsExample = new MallOrderGoodsExample();
        mallOrderGoodsExample.createCriteria().andOrderIdEqualTo(orderId);
        List<MallOrderGoods> mallOrderGoods = mallOrderGoodsMapper.selectByExample(mallOrderGoodsExample);
        wxOrderDetail.setOrderGoods(mallOrderGoods);


        MallOrder x = mallOrderMapper.selectByPrimaryKey(orderId);

        Short orderStatus = x.getOrderStatus();
        x.setOrderStatusText(getWxOrderStatusText(orderStatus));
        x.setHandleOption(new WxHandleOption(orderStatus));


        wxOrderDetail.setOrderInfo(x);

        return wxOrderDetail;
    }

    @Override
    public void cancelOrder(Integer orderId) {
        MallOrder mallOrder = mallOrderMapper.selectByPrimaryKey(orderId);
        mallOrder.setOrderStatus((short) 102);
        mallOrder.setOrderStatusText("用户取消");
        mallOrder.setHandleOption(new WxHandleOption((short) 102));
        mallOrderMapper.updateByPrimaryKey(mallOrder);
    }

    @Override
    public void deleteOrder(Integer orderId) {
        MallOrder mallOrder = mallOrderMapper.selectByPrimaryKey(orderId);
        MallOrderGoodsExample mallOrderGoodsExample = new MallOrderGoodsExample();
        mallOrderGoodsExample.createCriteria().andOrderIdEqualTo(orderId);
        mallOrderGoodsMapper.deleteByExample(mallOrderGoodsExample);
        mallOrderMapper.deleteByPrimaryKey(orderId);
    }

    @Override
    public HashMap<String, Object> countOrderStatusByUserId(Integer userId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        MallOrderExample mallOrderExample = new MallOrderExample();
        MallOrderExample.Criteria criteria = mallOrderExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<MallOrder> mallOrders = mallOrderMapper.selectByExample(mallOrderExample);
        Integer unrecv = 0;
        Integer uncomment = 0;
        Integer unpaid = 0;
        Integer unship = 0;
        for (MallOrder mallOrder : mallOrders) {
            if (mallOrder.getOrderStatus().intValue() == 101) {
                unpaid++;
            } else if (mallOrder.getOrderStatus().intValue() == 201) {
                unship++;
            } else if (mallOrder.getOrderStatus().intValue() == 301) {
                unrecv++;
            } else if (mallOrder.getOrderStatus().intValue() == 401 ||
                    mallOrder.getOrderStatus().intValue() == 402) {
                uncomment++;
            }
        }
        hashMap.put("unpaid", unpaid);
        hashMap.put("unship", unship);
        hashMap.put("unrecv", unrecv);
        hashMap.put("uncomment", uncomment);
        return hashMap;
    }

    public void refundOrder(Integer orderId) {
        MallOrder mallOrder = mallOrderMapper.selectByPrimaryKey(orderId);
        mallOrder.setOrderStatus((short) 202);
        mallOrderMapper.updateByPrimaryKey(mallOrder);
    }

    @Override
    public void confirmOrder(Integer orderId) {
        MallOrder mallOrder = mallOrderMapper.selectByPrimaryKey(orderId);
        mallOrder.setOrderStatus((short) 401);
        mallOrderMapper.updateByPrimaryKey(mallOrder);
    }

    @Override
    public WxId submitOrder(WxFromChart wxFromChart, String token) {
        //获取请求信息
        int addressId = wxFromChart.getAddressId();
        int cartId = wxFromChart.getCartId();
        int couponId = wxFromChart.getCouponId();
        int grouponLinkId = wxFromChart.getGrouponLinkId();
        int grouponRulesId = wxFromChart.getGrouponRulesId();
        String message = wxFromChart.getMessage();

        //获取userId
        Integer userId = UserTokenManager.getUserId(token);

        //分为立即购买和购物车购买(cartId 非0和0)

        //立即购买：cartId 找到一条cart信息，
        //购物车购买，使用userId并且选择为true找到多条cart消息。
        List<Cart> carts = new ArrayList<>();
        if(cartId!=0){
            carts.add(cartMapper.selectByPrimaryKey(cartId));
        }else {
            CartExample cartExample = new CartExample();
            cartExample.createCriteria().andUserIdEqualTo(userId).andCheckedEqualTo(true).andDeletedEqualTo(true);
            carts=cartMapper.selectByExample(cartExample);
        }

        //使用cart信息拼订单商品表.
        List<MallOrderGoods> orderGoods = insertOrderGoodsByCarts(carts);

        //根据订单商品表最终获得一条订单信息插入
        MallOrder resultOrder = new MallOrder();
        resultOrder.setMessage(message);
        resultOrder = setOrderAddress(resultOrder,addressId);
        resultOrder = setOrderCoupon(resultOrder,couponId);
        resultOrder = setOrdergroupon(resultOrder,grouponRulesId);
        resultOrder.setIntegralPrice(new BigDecimal(0));
        resultOrder = setOrderGoodsPrice(resultOrder,orderGoods);

        mallOrderMapper.insert(resultOrder);
        int resultOrderId = mallOrderMapper.lastInsert();

        //设置团购的，商品的相关订单id
        for(MallOrderGoods x:orderGoods){
            x.setOrderId(resultOrderId);
            mallOrderGoodsMapper.updateByPrimaryKey(x);
        }
        Groupon groupon = grouponMapper.selectByPrimaryKey(grouponLinkId);
        groupon.setOrderId(resultOrderId);
        grouponMapper.updateByPrimaryKey(groupon);

        WxId wxId = new WxId();
        wxId.setOrderId(resultOrderId);
        return wxId;
    }

    private MallOrder setOrderGoodsPrice(MallOrder resultOrder, List<MallOrderGoods> orderGoods) {
        int orderPrice=0;
        int actualPrice=0;
        int goodsTotalPrice=0;
        for(MallOrderGoods x:orderGoods) {
            goodsTotalPrice += x.getPrice().intValue();
        }
        actualPrice = goodsTotalPrice - resultOrder.getCouponPrice().intValue() /*- resultOrder.getGrouponPrice().intValue()*/;
        orderPrice = actualPrice;
        resultOrder.setActualPrice(new BigDecimal(actualPrice));
        resultOrder.setOrderPrice(new BigDecimal(orderPrice));
        return resultOrder;
    }

    private MallOrder setOrdergroupon(MallOrder resultOrder,  int grouponRulesId) {
        GrouponRules grouponRules = grouponRulesMapper.selectByPrimaryKey(grouponRulesId);
        if(grouponRules!=null){
            resultOrder.setGrouponPrice(grouponRules.getDiscount());
        }else {
            resultOrder.setGrouponPrice(new BigDecimal(0));
        }
        return resultOrder;
    }

    private MallOrder setOrderCoupon(MallOrder resultOrder, int couponId) {
        MallCoupon coupon = couponMapper.selectByPrimaryKey(couponId);
        resultOrder.setCouponPrice(coupon.getDiscount());
        return resultOrder;
    }

    private MallOrder setOrderAddress(MallOrder resultOrder, int addressId) {
        MallAddress mallAddress = addressMapper.selectByPrimaryKey(addressId);
        resultOrder.setConsignee(mallAddress.getName());
        resultOrder.setMobile(mallAddress.getMobile());
        resultOrder.setAddress(mallAddress.getAddress());
        return resultOrder;
    }

    private List<MallOrderGoods> insertOrderGoodsByCarts(List<Cart> carts) {
        List<MallOrderGoods> mallOrderGoods = new ArrayList<>();
        for(Cart x:carts){
            MallOrderGoods good = new MallOrderGoods();
            good.setGoodsId(x.getGoodsId());
            good.setGoodsName(x.getGoodsName());
            good.setGoodsSn(x.getGoodsSn());
            good.setProductId(x.getProductId());
            good.setNumber(x.getNumber());
            good.setPrice(x.getPrice());
            good.setSpecifications(x.getSpecifications().toString());
            good.setPicUrl(x.getPicUrl());
            good.setComment(0);
            good.setAddTime(new Date());
            good.setUpdateTime(new Date());
            good.setDeleted(false);
            mallOrderGoodsMapper.insert(good);
            mallOrderGoods.add(good);
        }
        return mallOrderGoods;
    }

    @Override
    public MallOrderGoods getCommentGoods(Integer orderId, Integer goodsId) {
        MallOrderGoodsExample example = new MallOrderGoodsExample();
        example.createCriteria().andOrderIdEqualTo(orderId).andGoodsIdEqualTo(goodsId);
        List<MallOrderGoods> mallOrderGoods = mallOrderGoodsMapper.selectByExample(example);
        if(mallOrderGoods.get(0)!=null){
            return mallOrderGoods.get(0);
        }
        return null;
    }
}

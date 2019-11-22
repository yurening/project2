package com.cskaoyan.service;
import com.cskaoyan.bean.generalize.Coupon;
import com.cskaoyan.bean.generalize.Groupon;
import com.cskaoyan.bean.generalize.GrouponRules;
import com.cskaoyan.bean.goods.Product;
import com.cskaoyan.bean.mall.address.MallAddress;
import com.cskaoyan.bean.mall.cart.MallCart;
import com.cskaoyan.bean.mall.cart.MallCartExample;
import com.cskaoyan.bean.mall.coupon.MallCoupon;
import com.cskaoyan.bean.mall.order.*;
import com.cskaoyan.bean.mall.system.MallSystem;
import com.cskaoyan.bean.mall.system.MallSystemExample;
import com.cskaoyan.bean.mall.wx_order.WxHandleOption;
import java.util.Date;

import com.cskaoyan.bean.mall.wx_order.*;

import com.cskaoyan.bean.mall.BaseListInfo;
import com.cskaoyan.bean.user.Cart;
import com.cskaoyan.bean.user.CartExample;
import com.cskaoyan.bean.user.User;
import com.cskaoyan.mapper.*;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
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
    MallCartMapper cartMapper;
    @Autowired
    MallCouponMapper couponMapper;
    @Autowired
    GrouponRulesMapper grouponRulesMapper;
    @Autowired
    GrouponMapper grouponMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    MallSystemMapper systemMapper;

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
        Integer userId = getUserID();

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

    private List<WxOrder> changeWxOrders(List<WxOrder> wxOrders ,Integer showType) {
        if(showType!=null&&showType!=0){
            int i = showType.intValue()*100+1;
            String orderStatusText = getWxOrderStatusText((short) i);
            List<WxOrder> newOrders = new ArrayList<>();
            for(WxOrder x:wxOrders){
                //i==101即未付款时候，检查是否超时，超时则系统取消
                if(i==101){
                    int orderId = x.getId();
                    MallOrder mallOrder = mallOrderMapper.selectByPrimaryKey(orderId);
                    //取出现在时间和订单修改时间对比。如果相差大于设定时间，则改为系统取消
                    Date nowDate = new Date();
                    Date updateTime = mallOrder.getUpdateTime();
                    MallSystemExample mallSystemExample = new MallSystemExample();
                    mallSystemExample.createCriteria().andKeyNameEqualTo("cskaoyan_mall_order_unpaid");
                    List<MallSystem> mallSystems = systemMapper.selectByExample(mallSystemExample);
                    String keyValue = mallSystems.get(0).getKeyValue();
                    int i1 = Integer.parseInt(keyValue) * 60 * 1000;
                    if(updateTime!=null&&i1 < (nowDate.getTime() - updateTime.getTime())){
                        mallOrder.setOrderStatus((short) 103);
                        x.setOrderStatusText("系统取消");
                        addNumber(mallOrder.getId());
                        mallOrder.setUpdateTime(nowDate);
                        mallOrderMapper.updateByPrimaryKey(mallOrder);
                        x.setHandleOption(new WxHandleOption((short) 103));
                    }
                    if("未付款".equals(x.getOrderStatusText())){
                        newOrders.add(x);
                    }
                }else {
                    String orderStatusText1 = x.getOrderStatusText();
                    if (orderStatusText1.equals(orderStatusText)) {
                        newOrders.add(x);
                    }
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
        addNumber(orderId);
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
    public WxId submitOrder(WxFromChart wxFromChart) {
        //获取cart,并逻辑删除
        int cartId = wxFromChart.getCartId();
        Integer userID = getUserID();
        List<MallCart> carts = new ArrayList<>();
        if(cartId!=0){
            carts.add(cartMapper.selectByPrimaryKey(cartId));
        }else {
            MallCartExample cartExample = new MallCartExample();
            cartExample.createCriteria().andUserIdEqualTo(userID).andCheckedEqualTo(true).andDeletedEqualTo(false);
            carts=cartMapper.selectByExample(cartExample);
            System.out.println(carts);
        }
        for(MallCart x:carts){
            x.setDeleted(true);
            cartMapper.updateByPrimaryKey(x);
        }


        MallOrder newOrder = new MallOrder();

        newOrder.setUserId(userID);
        newOrder.setOrderSn(Long.toString(new Date().getTime()));
        newOrder.setOrderStatus((short)101);

        //找地址
        MallAddress mallAddress = addressMapper.selectByPrimaryKey(wxFromChart.getAddressId());
        newOrder.setConsignee(mallAddress.getName());
        newOrder.setMobile(mallAddress.getMobile());
        newOrder.setAddress(mallAddress.getAddress());
        newOrder.setMessage(wxFromChart.getMessage());

        //找订单商品
        List<MallOrderGoods> orderGoods = new ArrayList<>();
        for(MallCart x:carts){
            MallOrderGoods mallOrderGoods = new MallOrderGoods();
            mallOrderGoods.setOrderId(0);
            mallOrderGoods.setGoodsId(x.getGoodsId());
            mallOrderGoods.setGoodsName(x.getGoodsName());
            mallOrderGoods.setGoodsSn(x.getGoodsSn());
            mallOrderGoods.setProductId(x.getProductId());
            mallOrderGoods.setNumber(x.getNumber());
            mallOrderGoods.setPrice(x.getPrice());
            mallOrderGoods.setSpecifications(x.getSpecifications());
            mallOrderGoods.setPicUrl(x.getPicUrl());
            mallOrderGoods.setComment(0);
            mallOrderGoods.setAddTime(new Date());
            mallOrderGoods.setUpdateTime(new Date());
            mallOrderGoods.setDeleted(false);
            mallOrderGoodsMapper.insert(mallOrderGoods);
            int i = mallOrderGoodsMapper.lastInsert();
            mallOrderGoods.setId(i);
            orderGoods.add(mallOrderGoods);


        }
        //设置新订单价格
        BigDecimal goodPrice = new BigDecimal(0);
        for(MallOrderGoods x:orderGoods){
            goodPrice.add(x.getPrice());
        }
        newOrder.setGoodsPrice(goodPrice);

        //找运费
        MallSystemExample mallSystemExample = new MallSystemExample();
        mallSystemExample.createCriteria().andKeyNameEqualTo("cskaoyan_mall_express_freight_value");
        List<MallSystem> mallSystems = systemMapper.selectByExample(mallSystemExample);
        MallSystem mallSystem = mallSystems.get(0);
        newOrder.setFreightPrice(BigDecimal.valueOf(Long.parseLong(mallSystem.getKeyValue())));

        //设置优惠
        int couponId = wxFromChart.getCouponId();
        newOrder.setCouponPrice(new BigDecimal("0"));
        if(couponId != 0 && couponId != -1){
            MallCoupon mallCoupon = couponMapper.selectByPrimaryKey(couponId);
            mallCoupon.setStatus((short) 1);
            couponMapper.updateByPrimaryKey(mallCoupon);
            newOrder.setCouponPrice(mallCoupon.getDiscount());
        }
        newOrder.setIntegralPrice(new BigDecimal("0"));

        //设置团购
        int grouponRulesId = wxFromChart.getGrouponRulesId();
        newOrder.setGrouponPrice(new BigDecimal("0"));
        if(grouponRulesId!=0 && grouponRulesId != -1){
            GrouponRules grouponRules = grouponRulesMapper.selectByPrimaryKey(grouponRulesId);
            newOrder.setGrouponPrice(grouponRules.getDiscount());
        }

        //设置订单实际价格
        BigDecimal orderPrice = newOrder.getGoodsPrice().subtract(newOrder.getCouponPrice()).subtract(newOrder.getGrouponPrice());
        newOrder.setOrderPrice(orderPrice);
        BigDecimal actualPrice = newOrder.getOrderPrice().add(newOrder.getFreightPrice());
        newOrder.setActualPrice(actualPrice);

        //设置支付
        newOrder.setPayId(Integer.toString(userID));
        newOrder.setPayTime(null);
        newOrder.setShipSn("");
        newOrder.setShipChannel("");
        newOrder.setShipTime(new Date());

        newOrder.setConfirmTime(null);
        newOrder.setComments((short)0);
        newOrder.setEndTime(null);
        newOrder.setAddTime(new Date());
        newOrder.setUpdateTime(new Date());
        newOrder.setDeleted(false);
        newOrder.setHandleOption(new WxHandleOption((short) 101));
        newOrder.setExpCode("");
        newOrder.setExpNo("");
        newOrder.setOrderStatusText("未支付");

        //插入订单
        mallOrderMapper.insert(newOrder);
        int i = mallOrderMapper.lastInsert();

        //将orderGoods的orderid设置
        for(MallOrderGoods x:orderGoods){
            x.setOrderId(i);
            mallOrderGoodsMapper.updateByPrimaryKey(x);
        }

        //新增团购
        if(grouponRulesId!=0 && grouponRulesId != -1){
            int i1 = grouponMapper.lastInsert();
            Groupon groupon = new Groupon();
            groupon.setOrderId(i);
            groupon.setGrouponId(i1+1);
            groupon.setRulesId(grouponRulesId);
            groupon.setUserId(userID);
            groupon.setCreatorUserId(userID);
            groupon.setAddTime(new Date());
            groupon.setUpdateTime(new Date());
            groupon.setShareUrl("");
            groupon.setPayed(false);
            groupon.setDeleted(false);
            grouponMapper.insert(groupon);
        }

        WxId wxId = new WxId();
        wxId.setOrderId(i);

        return wxId;
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

    private Integer getUserID(){
        User principal =(User) SecurityUtils.getSubject().getPrincipal();
        return principal.getId();
    }

    @Override
    public void addNumber(Integer orderId) {
        MallOrderGoodsExample e = new MallOrderGoodsExample();
        e.createCriteria().andOrderIdEqualTo(orderId);
        List<MallOrderGoods> mallOrderGoods = mallOrderGoodsMapper.selectByExample(e);
        for(MallOrderGoods x: mallOrderGoods){
            Integer productId = x.getProductId();
            Short number = x.getNumber();
            Product product = productMapper.selectByPrimaryKey(productId);
            product.setNumber(product.getNumber()+number);
            productMapper.updateByPrimaryKey(product);
        }
    }

    private void deleteProduct(List<MallOrderGoods> orderGoods) {
        for(MallOrderGoods x: orderGoods){
            Integer productId = x.getProductId();
            Short number = x.getNumber();
            Product product = productMapper.selectByPrimaryKey(productId);
            product.setNumber(product.getNumber()-number);
            productMapper.updateByPrimaryKey(product);
        }
    }

    @Override
    public OrderPay orderPayByTime(Integer id, int i) {
        OrderPay orderPay = new OrderPay();
        MallOrder mallOrder = mallOrderMapper.selectByPrimaryKey(id);
        Date date = new Date();
        Date addTime = mallOrder.getAddTime();
        if(addTime !=null&&(date.getTime()-addTime.getTime())>i){
            orderPay.setStatus(true);
        }else {
            orderPay.setStatus(true);
        }
        return  orderPay;
    }
}

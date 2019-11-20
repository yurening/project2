package com.cskaoyan.service;
import com.cskaoyan.bean.mall.order.MallOrderGoods;
import com.cskaoyan.bean.mall.order.MallOrderGoodsExample;
import com.cskaoyan.bean.mall.wx_order.WxGoods;

import com.cskaoyan.bean.mall.BaseListInfo;
import com.cskaoyan.bean.mall.order.MallOrder;
import com.cskaoyan.bean.mall.order.MallOrderExample;
import com.cskaoyan.bean.mall.wx_order.WxHandleOption;
import com.cskaoyan.bean.mall.wx_order.WxOrder;
import com.cskaoyan.bean.mall.wx_order.WxOrderDetail;
import com.cskaoyan.mapper.MallOrderGoodsMapper;
import com.cskaoyan.mapper.MallOrderMapper;
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
        wxOrderDetail.setOrderGoods(getWxGoodsByOrderId(orderId));


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
            if (mallOrder.getOrderStatus().intValue() == 101){
                unpaid++;
            } else if (mallOrder.getOrderStatus().intValue() == 201){
                unship++;
            } else if (mallOrder.getOrderStatus().intValue() == 301){
                unrecv++;
            } else if (mallOrder.getOrderStatus().intValue() == 401 ||
                        mallOrder.getOrderStatus().intValue() == 402){
                uncomment++;
            }
        }
        hashMap.put("unpaid",unpaid);
        hashMap.put("unship",unship);
        hashMap.put("unrecv",unrecv);
        hashMap.put("uncomment",uncomment);
        return hashMap;
    }
}

package com.cskaoyan.service;

import com.cskaoyan.bean.mall.BaseListInfo;
import com.cskaoyan.bean.mall.order.MallOrder;
import com.cskaoyan.bean.mall.order.MallOrderGoods;
import com.cskaoyan.bean.mall.wx_order.WxFromChart;
import com.cskaoyan.bean.mall.wx_order.WxId;
import com.cskaoyan.bean.mall.wx_order.WxOrder;
import com.cskaoyan.bean.mall.wx_order.WxOrderDetail;

import java.util.HashMap;

public interface OrderService {
    BaseListInfo<WxOrder> getWxOrderList(Integer showType, Integer page, Integer size, String token);

    WxOrderDetail getOrderDetail(Integer orderId);

    void cancelOrder(Integer orderId);

    void deleteOrder(Integer orderId);

    HashMap<String, Object> countOrderStatusByUserId(Integer userId);

    void refundOrder(Integer orderId);

    void confirmOrder(Integer orderId);

    WxId submitOrder(WxFromChart wxFromChart, String token);

    MallOrderGoods getCommentGoods(Integer orderId, Integer goodsId);
}

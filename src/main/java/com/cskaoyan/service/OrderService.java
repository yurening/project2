package com.cskaoyan.service;

import com.cskaoyan.bean.mall.BaseListInfo;
import com.cskaoyan.bean.mall.order.MallOrder;
import com.cskaoyan.bean.mall.wx_order.WxOrder;
import com.cskaoyan.bean.mall.wx_order.WxOrderDetail;

public interface OrderService {
    BaseListInfo<WxOrder> getWxOrderList(Integer showType, Integer page, Integer size, String token);

    WxOrderDetail getOrderDetail(Integer orderId);

    void cancelOrder(Integer orderId);

    void deleteOrder(Integer orderId);

    void refundOrder(Integer orderId);

    void confirmOrder(Integer orderId);
}

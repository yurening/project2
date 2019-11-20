package com.cskaoyan.wx_controller;


import com.cskaoyan.bean.mall.BaseListInfo;
import com.cskaoyan.bean.mall.BaseRespVo;
import com.cskaoyan.bean.mall.wx_order.WxFromChart;
import com.cskaoyan.bean.mall.wx_order.WxOrder;
import com.cskaoyan.bean.mall.wx_order.WxId;
import com.cskaoyan.bean.mall.wx_order.WxOrderDetail;
import com.cskaoyan.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("wx/order")
public class OrderController_wx {
    @Autowired
    OrderService orderService;

    @RequestMapping("list")
    public BaseRespVo getOrderList(Integer showType, Integer page, Integer size,@RequestHeader("X-cskaoyanmall-Admin-Token") String  token){

        BaseListInfo<WxOrder> baseListInfo= orderService.getWxOrderList(showType,page,size,token);

        return BaseRespVo.ok(baseListInfo);
    }

    @RequestMapping("detail")
    public BaseRespVo getWxOrderDetail(Integer orderId){
        WxOrderDetail orderDetail = orderService.getOrderDetail(orderId);
        return BaseRespVo.ok(orderDetail);
    }

    @RequestMapping("prepay")
    public BaseRespVo orderPrepay(Integer orderId){
        return BaseRespVo.fail(724,"订单不能支付");
    }

    @RequestMapping("cancel")
    public BaseRespVo cancelOrder(@RequestBody WxId wxOrder){
        orderService.cancelOrder(wxOrder.getOrderId());
        return BaseRespVo.ok(null);
    }

    @RequestMapping("delete")
    public BaseRespVo deleteOrder(@RequestBody WxId wxOrder){
        orderService.deleteOrder(wxOrder.getOrderId());
        return BaseRespVo.ok(null);
    }

    @RequestMapping("refund")
    public BaseRespVo refundOrder(@RequestBody WxId wxId){
        orderService.refundOrder(wxId.getOrderId());
        return BaseRespVo.ok(null);
    }

    @RequestMapping("confirm")
    public BaseRespVo confirmOrder(@RequestBody WxId wxId){
        orderService.confirmOrder(wxId.getOrderId());
        return BaseRespVo.ok(null);
    }

    @RequestMapping("submit")
    public BaseRespVo submitOrder(@RequestBody WxFromChart wxFromChart,@RequestHeader("X-cskaoyanmall-Admin-Token") String token){
        WxId wxId = orderService.submitOrder(wxFromChart,token);
        return BaseRespVo.ok(wxId);
    }

}

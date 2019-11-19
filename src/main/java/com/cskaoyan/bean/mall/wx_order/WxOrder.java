package com.cskaoyan.bean.mall.wx_order;

import java.util.List;

public class WxOrder {

    /**
     * orderStatusText : 未付款
     * isGroupin : false
     * orderSn : 20191119768474
     * actualPrice : 42
     * goodsList : [{"number":1,"picUrl":"http://192.168.2.100:8081/wx/storage/fetch/wdc87ltjnhp5gx1elcab.jpg","id":632,"goodsName":"12121"}]
     * id : 501
     * handleOption : {"cancel":true,"delete":false,"pay":true,"comment":false,"confirm":false,"refund":false,"rebuy":false}
     */

    private String orderStatusText;
    private boolean isGroupin;
    private String orderSn;
    private int actualPrice;
    private int id;
    private WxHandleOption handleOption;
    private List<WxGoods> goodsList;

    public String getOrderStatusText() {
        return orderStatusText;
    }

    public void setOrderStatusText(String orderStatusText) {
        this.orderStatusText = orderStatusText;
    }

    public boolean isIsGroupin() {
        return isGroupin;
    }

    public void setIsGroupin(boolean isGroupin) {
        this.isGroupin = isGroupin;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public int getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(int actualPrice) {
        this.actualPrice = actualPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WxHandleOption getHandleOption() {
        return handleOption;
    }

    public void setHandleOption(WxHandleOption handleOption) {
        this.handleOption = handleOption;
    }

    public List<WxGoods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<WxGoods> goodsList) {
        this.goodsList = goodsList;
    }


}

package com.cskaoyan.bean.mall.wx_order;

public class WxGoods {
    /**
     * number : 1
     * picUrl : http://192.168.2.100:8081/wx/storage/fetch/wdc87ltjnhp5gx1elcab.jpg
     * id : 632
     * goodsName : 12121
     */

    private int number;
    private String picUrl;
    private int id;
    private String goodsName;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}


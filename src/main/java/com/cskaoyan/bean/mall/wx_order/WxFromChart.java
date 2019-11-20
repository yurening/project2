package com.cskaoyan.bean.mall.wx_order;

public class WxFromChart {

    /**
     * cartId : 0
     * addressId : 69
     * couponId : 0
     * message :
     * grouponRulesId : 0
     * grouponLinkId : 0
     */

    private int cartId;
    private int addressId;
    private int couponId;
    private String message;
    private int grouponRulesId;
    private int grouponLinkId;

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getGrouponRulesId() {
        return grouponRulesId;
    }

    public void setGrouponRulesId(int grouponRulesId) {
        this.grouponRulesId = grouponRulesId;
    }

    public int getGrouponLinkId() {
        return grouponLinkId;
    }

    public void setGrouponLinkId(int grouponLinkId) {
        this.grouponLinkId = grouponLinkId;
    }
}

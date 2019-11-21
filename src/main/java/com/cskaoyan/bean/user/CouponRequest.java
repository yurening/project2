package com.cskaoyan.bean.user;

import lombok.Data;

@Data
public class CouponRequest {
    short status;
    int page;
    int size;
    int couponId;
    int cartId;
    int groupResultId;
    String code;
    int grouponRulesId;
}

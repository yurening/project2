package com.cskaoyan.bean.mall.wx_order;

import com.cskaoyan.bean.mall.order.MallOrder;
import lombok.Data;

import java.util.List;
@Data
public class WxOrderDetail {
    List<WxGoods> orderGoods;
    MallOrder orderInfo;
}

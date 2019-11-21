package com.cskaoyan.bean.mall.wx_order;

import com.cskaoyan.bean.mall.order.MallOrder;
import com.cskaoyan.bean.mall.order.MallOrderGoods;
import lombok.Data;

import java.util.List;
@Data
public class WxOrderDetail {
    List<MallOrderGoods> orderGoods;
    MallOrder orderInfo;
}

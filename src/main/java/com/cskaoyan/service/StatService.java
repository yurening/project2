package com.cskaoyan.service;

import com.cskaoyan.bean.stat.GoodsCount;
import com.cskaoyan.bean.stat.OrderCount;
import com.cskaoyan.bean.stat.UserCount;

import java.util.List;

public interface StatService {
    List<UserCount> getUserDayCountList();

    List<OrderCount> getOrderDayCountList();

    List<GoodsCount> getGoodsDayCountList();
}


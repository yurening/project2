package com.cskaoyan.mapper;

import com.cskaoyan.bean.stat.GoodsCount;
import com.cskaoyan.bean.stat.OrderCount;
import com.cskaoyan.bean.stat.UserCount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StatMapper {

    List<UserCount> getUserDayCount();

    List<OrderCount> getOrderDayCount();

    List<GoodsCount> getGoodsDayCount();
}

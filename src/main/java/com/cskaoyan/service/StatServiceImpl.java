/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/17
 * Time: 16:01
 */
package com.cskaoyan.service;

import com.cskaoyan.bean.stat.GoodsCount;
import com.cskaoyan.bean.stat.OrderCount;
import com.cskaoyan.bean.stat.UserCount;
import com.cskaoyan.mapper.StatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatServiceImpl implements StatService {

    @Autowired
    StatMapper statMapper;

    @Override
    public List<UserCount> getUserDayCountList() {
        List<UserCount> list = statMapper.getUserDayCount();
        return list;
    }

    @Override
    public List<OrderCount> getOrderDayCountList() {
        List<OrderCount> list = statMapper.getOrderDayCount();
        for (OrderCount orderCount : list) {
            orderCount.setPcr(orderCount.getAmount()/orderCount.getCustomers());
        }
        return list;
    }

    @Override
    public List<GoodsCount> getGoodsDayCountList() {
        List<GoodsCount> list = statMapper.getGoodsDayCount();
        return list;
    }
}

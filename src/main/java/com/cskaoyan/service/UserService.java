package com.cskaoyan.service;

import com.cskaoyan.bean.generalize.Coupon;
import com.cskaoyan.bean.generalize.Groupon;
import com.cskaoyan.bean.generalize.GrouponRules;
import com.cskaoyan.bean.user.*;
import com.cskaoyan.bean.user.groupon.GrouponDetail;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UserService {
    //user
    long countUserByExample(UserExample example);
    List<User> selectUserByExample(UserRequest userRequest,UserExample example);
    //address
    public List<Address> selectAddress(AddressRequest addressRequest,AddressExample addressExample);
    long countAddressByExample(AddressExample example);

    //collect
    long countCollectByExample(CollectExample example);

    List<Collect> selectCollectByExample(CollectRequest collectRequest,CollectExample example);

    //footprint
    long countFootprintByExample(FootPrintExample example);

    List<FootPrint> selectFootprintByExample(FootPrintRequest footPrintRequest,FootPrintExample example);

    //history
    long countHistoryByExample(HistoryExample example);
    List<History> selectHistoryByExample(HistoryRequest historyRequest,HistoryExample example);

    //feedback
    long countFeedbackByExample(FeedbackExample example);
    List<Feedback> selectFeedbackByExample(FeedBackRequest feedBackRequest,FeedbackExample example);

    User getUserByUsername(String username);

    Map<String,Object> selectSearchIndex();

    List<String> searchHelper(String keyword);

    int searchClearHistory(HttpServletRequest request);

    //groupon
    Map selectGroupon(UserRequest userRequest);

    GouponMy.DataBeanX grouponMy(int showType);

    GrouponDetail.DataBean grouponDetail(int grouponId);

    //coupon
    Map selectCoupon(UserRequest userRequest);

    ReturnData couponMyList(CouponRequest couponRequest);

    int couponReceive(CouponRequest couponRequest);

    ReturnData couponSelectList(CouponRequest couponRequest);

    int couponExchange(CouponRequest couponRequest, HttpServletRequest request);
}

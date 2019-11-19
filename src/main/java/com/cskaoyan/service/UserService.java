package com.cskaoyan.service;

import com.cskaoyan.bean.user.*;

import java.util.List;

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
}

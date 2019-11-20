package com.cskaoyan.mapper;

import com.cskaoyan.bean.user.Collect;
import com.cskaoyan.bean.user.CollectExample;
import com.cskaoyan.bean.user.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserMapper {
    //user
    long countUserByExample(UserExample example);
    List<User> selectUserByExample(UserExample example);

    //address
    List<Address> selectAddressByExample(AddressExample example);

    long countAddressByExample(AddressExample example);

    String selectNameById(@Param("id")int id);

    //collect
    long countCollectByExample(CollectExample example);

    List<Collect> selectCollectByExample(CollectExample example);

    //footprint
    long countFootprintByExample(FootPrintExample example);

    List<FootPrint> selectFootprintByExample(FootPrintExample example);

    //history
    long countHistoryByExample(HistoryExample example);
    List<History> selectHistoryByExample(HistoryExample example);

    //feedback
    long countFeedbackByExample(FeedbackExample example);
    List<Feedback> selectFeedbackByExample(FeedbackExample example);

    User getUserByUsername(String username);
}

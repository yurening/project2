package com.cskaoyan.mapper;

import com.cskaoyan.bean.Wx_register;
import com.cskaoyan.bean.user.FootPrint;
import com.cskaoyan.bean.user.FootPrintExample;
import com.cskaoyan.bean.user.Feedback;
import com.cskaoyan.bean.user.FeedbackExample;
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

    void updateLoginTime(Integer id);

    Boolean registerInsertUser(@Param("wxRegister")Wx_register wxRegister,@Param("avatar")String avatar);

    User getUserByMobile(String mobile);

    void resetPasswordBymolibe(@Param("password")String password, @Param("mobile")String mobile);
}

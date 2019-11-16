package com.cskaoyan.service;

import com.cskaoyan.bean.user.*;
import com.cskaoyan.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;

    @Override
    public List<ReqParamFromDb> selectUserList(RequestList requestList) {
        PageHelper.startPage(requestList.getPage(),requestList.getLimit());
        List<ReqParamFromDb> lists = userMapper.selectUserList(requestList);
        return lists;
    }

    @Override
    public int selectTotal() {
        return userMapper.selectTotal();
    }

//address
    public List<Address> selectAddress(AddressRequest addressRequest,AddressExample addressExample){
        PageHelper.startPage(addressRequest.getPage(),addressRequest.getLimit());
        List<Address> lists = userMapper.selectAddressByExample(addressExample);
        for (Address list : lists) {
            String province = userMapper.selectNameById(list.getProvinceId());
            String city = userMapper.selectNameById(list.getCityId());
            String area = userMapper.selectNameById(list.getAreaId());
            list.setProvince(province);
            list.setCity(city);
            list.setArea(area);
        }
        return lists;
    }

    @Override
    public long countAddressByExample(AddressExample example) {
        long l = userMapper.countAddressByExample(example);
        return l;
    }

    @Override
    public long countCollectByExample(CollectExample example) {
        return userMapper.countCollectByExample(example);
    }

    @Override
    public List<Collect> selectCollectByExample(CollectRequest collectRequest, CollectExample example) {
        PageHelper.startPage(collectRequest.getPage(),collectRequest.getLimit());
        List<Collect> collects = userMapper.selectCollectByExample(example);
        return collects;
    }

    @Override
    public long countFootprintByExample(FootPrintExample example) {
        return userMapper.countFootprintByExample(example);
    }

    @Override
    public List<FootPrint> selectFootprintByExample(FootPrintRequest footPrintRequest,FootPrintExample example) {
        PageHelper.startPage(footPrintRequest.getPage(),footPrintRequest.getLimit());
        List<FootPrint> footPrints = userMapper.selectFootprintByExample(example);
        return footPrints;
    }

    //history
    @Override
    public long countHistoryByExample(HistoryExample example) {
        return userMapper.countHistoryByExample(example);
    }

    @Override
    public List<History> selectHistoryByExample(HistoryRequest historyRequest, HistoryExample example) {
        PageHelper.startPage(historyRequest.getPage(),historyRequest.getLimit());
        List<History> histories = userMapper.selectHistoryByExample(example);
        return histories;
    }

    @Override
    public long countFeedbackByExample(FeedbackExample example) {
        return userMapper.countFeedbackByExample(example);
    }

    @Override
    public List<Feedback> selectFeedbackByExample(FeedBackRequest feedBackRequest, FeedbackExample example) {
        PageHelper.startPage(feedBackRequest.getPage(),feedBackRequest.getLimit());
        return userMapper.selectFeedbackByExample(example);
    }


}

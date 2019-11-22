package com.cskaoyan.service;

import com.cskaoyan.bean.wx.FeedBack;

import java.util.HashMap;

public interface OtherService {
    void feedBackSubmit(FeedBack feedBack, String userName,Integer userId);

    HashMap<String, Object> footprintList(Integer page, Integer size, Integer userId);

    void footprintDelete(Integer id);
}

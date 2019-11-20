package com.cskaoyan.service;

import com.cskaoyan.bean.user.Feedback;

import java.util.HashMap;

public interface OtherService {
    void feedbackSubmit(Feedback feedback, Integer userId);

    HashMap<String, Object> footprintList(Integer page, Integer size, Integer userId);

    void footprintDelete(Integer id);
}

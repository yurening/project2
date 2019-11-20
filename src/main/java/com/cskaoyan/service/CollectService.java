package com.cskaoyan.service;

import com.cskaoyan.bean.user.Collect;

import java.util.HashMap;

public interface CollectService {
    HashMap<String, Object> collectList(Integer type, Integer page, Integer size,Integer userId);

    HashMap<String, Object> addOrDelete(Collect collect, Integer userId);
}

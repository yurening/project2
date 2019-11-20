package com.cskaoyan.service;

import com.cskaoyan.bean.goods.ResponseType;

public interface TopicService {
    ResponseType getTopicDetail(Integer id);
    ResponseType getTopicRelated(Integer id);
}

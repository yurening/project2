package com.cskaoyan.wx_controller;

import com.cskaoyan.bean.goods.ResponseType;
import com.cskaoyan.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("wx")
public class TopicController_wx {
    @Autowired
    TopicService topicService;
    @RequestMapping("topic/detail")
    public ResponseType topicDetail(Integer id){
        ResponseType topicDetail = topicService.getTopicDetail(id);
        return topicDetail;
    }

    @RequestMapping("topic/related")
    public ResponseType topicRelated(Integer id){
        ResponseType topicRelated = topicService.getTopicRelated(id);
        return topicRelated;
    }
}

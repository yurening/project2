package com.cskaoyan.service;

import com.cskaoyan.bean.generalize.Topic;
import com.cskaoyan.bean.goods.ResponseType;
import com.cskaoyan.bean.goods.TopicExample;
import com.cskaoyan.mapper.TopicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    TopicMapper topicMapper;

    @Override
    public ResponseType getTopicDetail(Integer id) {
        com.cskaoyan.bean.generalize.TopicExample topicExample = new com.cskaoyan.bean.generalize.TopicExample();
        topicExample.createCriteria().andIdEqualTo(id);
        List<Topic> topics = topicMapper.selectByExampleWithBLOBs(topicExample);
        Topic topic = topics.get(0);
        Map map = new HashMap();
        map.put("topic",topic);
        ResponseType responseType = new ResponseType();
        responseType.setData(map);
        responseType.setErrmsg("成功");
        responseType.setErrno(0);
        return responseType;
    }

    @Override
    public ResponseType getTopicRelated(Integer id) {
        com.cskaoyan.bean.generalize.TopicExample topicExample = new com.cskaoyan.bean.generalize.TopicExample();
        List<Topic> topics = (List<Topic>) topicMapper.selectByExample(topicExample);
        ResponseType responseType = new ResponseType();
        responseType.setData(topics);
        responseType.setErrmsg("成功");
        responseType.setErrno(0);
        return responseType;
    }
}

package com.cskaoyan.service;

import com.cskaoyan.bean.user.Feedback;
import com.cskaoyan.bean.user.FootPrint;
import com.cskaoyan.bean.user.FootPrintExample;
import com.cskaoyan.bean.user.FootprintForList;
import com.cskaoyan.mapper.FeedbackMapper;
import com.cskaoyan.mapper.FootPrintMapper;
import com.cskaoyan.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class OtherServiceImpl implements OtherService {

    @Autowired
    FeedbackMapper feedbackMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    FootPrintMapper footPrintMapper;

    @Override
    public void feedbackSubmit(Feedback feedback, Integer userId) {
        feedback.setAddTime(new Date());
        feedback.setDeleted(false);
        feedback.setUserId(userId);
        feedback.setUpdateTime(new Date());
        feedback.setStatus(0);
        String s = userMapper.selectNameById(userId);
        feedback.setUsername(s);
        feedbackMapper.insert(feedback);
    }

    @Override
    public HashMap<String, Object> footprintList(Integer page, Integer size, Integer userId) {
        FootPrintExample footPrintExample = new FootPrintExample();
        FootPrintExample.Criteria criteria = footPrintExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        long l = footPrintMapper.countByExample(footPrintExample);
        int totalPages = (Math.toIntExact(l) / size) + 1;
        PageHelper.startPage(page,size);
        List<FootprintForList> footprintForLists = footPrintMapper.footPrintList(userId);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("footprintList",footprintForLists);
        hashMap.put("totalPages",totalPages);
        return hashMap;
    }

    @Override
    public void footprintDelete(Integer id) {
        FootPrintExample footPrintExample = new FootPrintExample();
        FootPrintExample.Criteria criteria = footPrintExample.createCriteria();
        criteria.andIdEqualTo(id);
        footPrintMapper.deleteByExample(footPrintExample);
    }
}

package com.cskaoyan.controller;

import com.cskaoyan.bean.user.FootPrint;
import com.cskaoyan.bean.user.FootPrintExample;
import com.cskaoyan.bean.user.Feedback;
import com.cskaoyan.bean.user.FeedbackExample;
import com.cskaoyan.bean.user.Collect;
import com.cskaoyan.bean.user.CollectExample;
import com.cskaoyan.bean.user.*;
import com.cskaoyan.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;


    @RequestMapping("admin/user/list")
    @RequiresPermissions(value = {"admin:user:list"})
    public BaseReqVo list(UserRequest userRequest){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if(userRequest.getUsername()!=null&&userRequest.getUsername()!=""){
            criteria.andUsernameLike("%"+userRequest.getUsername()+"%");
        }
        if(userRequest.getMobile()!=null&&userRequest.getMobile()!=""){
            criteria.andMobileLike(userRequest.getMobile()+"%");
        }
        userExample.setOrderByClause("add_time desc");
        List<User> list = userService.selectUserByExample(userRequest, userExample);
        long total = userService.countUserByExample(userExample);
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",list);
        objectBaseReqVo.setData(map);
        objectBaseReqVo.setErrmsg("成功");
        objectBaseReqVo.setErrno(0);
        return objectBaseReqVo;
    }

    @RequestMapping("admin/address/list")
    @RequiresPermissions(value = {"admin:address:list"})
    public BaseReqVo address(AddressRequest addressRequest){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        AddressExample addressExample = new AddressExample();
        AddressExample.Criteria criteria = addressExample.createCriteria();
        if(addressRequest.getName()!=null&&addressRequest.getName()!=""){
            criteria.andNameLike("%"+addressRequest.getName()+"%");
        }
        if(addressRequest.getUserId()!=null&&addressRequest.getUserId()!=""){
            criteria.andUserIdEqualTo(new Integer(addressRequest.getUserId()));
        }
        addressExample.setOrderByClause("add_time desc");
        List<Address> list = userService.selectAddress(addressRequest, addressExample);
        long total = userService.countAddressByExample(addressExample);
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",list);
        objectBaseReqVo.setData(map);
        objectBaseReqVo.setErrmsg("成功");
        objectBaseReqVo.setErrno(0);
        return objectBaseReqVo;
    }

    @RequestMapping("admin/collect/list")
    @RequiresPermissions(value = {"admin:collect:list"})
    public BaseReqVo collect(CollectRequest collectRequest){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        CollectExample collectExample = new CollectExample();
        collectExample.setOrderByClause("add_time desc");
        CollectExample.Criteria criteria = collectExample.createCriteria();
        if(collectRequest.getUserId()!=null&&collectRequest.getUserId()!=""){
            criteria.andUserIdEqualTo(new Integer(collectRequest.getUserId()));
        }
        if(collectRequest.getValueId()!=null&&collectRequest.getValueId()!=""){
            criteria.andValueIdEqualTo(new Integer(collectRequest.getValueId()));

        }
        long total = userService.countCollectByExample(collectExample);
        List<Collect> collects = userService.selectCollectByExample(collectRequest, collectExample);
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",collects);
        objectBaseReqVo.setData(map);
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }

    @RequestMapping("admin/footprint/list")
    @RequiresPermissions(value = {"admin:footprint:list"})
    public BaseReqVo footprint(FootPrintRequest footPrintRequest){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        FootPrintExample footPrintExample = new FootPrintExample();
        footPrintExample.setOrderByClause("add_time desc");
        FootPrintExample.Criteria criteria = footPrintExample.createCriteria();
        if(footPrintRequest.getUserId()!=null&&footPrintRequest.getUserId()!=""){
            criteria.andUserIdEqualTo(new Integer(footPrintRequest.getUserId()));
        }
        if(footPrintRequest.getGoodsId()!=null&&footPrintRequest.getGoodsId()!=""){
            criteria.andGoodsIdEqualTo(new Integer(footPrintRequest.getGoodsId()));
        }
        long total = userService.countFootprintByExample(footPrintExample);
        List<FootPrint> footPrints = userService.selectFootprintByExample(footPrintRequest, footPrintExample);
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",footPrints);
        objectBaseReqVo.setData(map);
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }


    @RequestMapping("admin/history/list")
    @RequiresPermissions(value = {"admin:history:list"})
    public BaseReqVo history(HistoryRequest historyRequest){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        HistoryExample historyExample = new HistoryExample();
        historyExample.setOrderByClause("add_time desc");
        HistoryExample.Criteria criteria = historyExample.createCriteria();
        if(historyRequest.getUserId()!=null&&historyRequest.getUserId()!=""){
            criteria.andUserIdEqualTo(new Integer(historyRequest.getUserId()));
        }
        if(historyRequest.getKeyword()!=null&&historyRequest.getKeyword()!=""){
            criteria.andKeywordLike("%"+historyRequest.getKeyword()+"%");
        }
        long total = userService.countHistoryByExample(historyExample);
        List<History> histories = userService.selectHistoryByExample(historyRequest, historyExample);
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",histories);
        objectBaseReqVo.setData(map);
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }

    @RequestMapping("admin/feedback/list")
    @RequiresPermissions(value = {"admin:feedback:list"})
    public BaseReqVo feedback(FeedBackRequest feedBackRequest){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        FeedbackExample feedbackExample = new FeedbackExample();
        feedbackExample.setOrderByClause("add_time desc");
        FeedbackExample.Criteria criteria = feedbackExample.createCriteria();
        if(feedBackRequest.getUsername()!=null&&feedBackRequest.getUsername()!=""){
            criteria.andUsernameLike(feedBackRequest.getUsername());
        }
        if(feedBackRequest.getId()!=null&&feedBackRequest.getId()!=""){
            criteria.andIdEqualTo(new Integer(feedBackRequest.getId()));
        }
        long total = userService.countFeedbackByExample(feedbackExample);
        List<Feedback> feedbacks = userService.selectFeedbackByExample(feedBackRequest, feedbackExample);
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",feedbacks);
        objectBaseReqVo.setData(map);
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }
}

package com.cskaoyan.controller;

import com.cskaoyan.bean.user.*;
import com.cskaoyan.mapper.UserMapper;
import com.cskaoyan.service.UserService;
import com.cskaoyan.utils.TransferDateUtils;
import com.github.pagehelper.PageInfo;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    @RequestMapping("admin/user/list")
    public BaseReqVo list(RequestList requestList){
        List<ReqParamFromDb> lists = userService.selectUserList(requestList);
        BaseReqVo baseReqVo = new BaseReqVo();
        List<ReqParam> paramList = new ArrayList<>();
        for (ReqParamFromDb list : lists) {
            ReqParam reqParam = new ReqParam();
            reqParam.setId(list.getId());
            reqParam.setUsername(list.getUsername());
            reqParam.setPassword(list.getPassword());
            reqParam.setGender(list.getGender());
            reqParam.setBirthday(TransferDateUtils.date2String(list.getBirthday()));
            reqParam.setLastLoginTime(TransferDateUtils.date2String(list.getLastLoginTime()));
            reqParam.setLastLoginIp(list.getLastLoginIp());
            reqParam.setUserLevel(list.getUserlevel());
            reqParam.setNickname(list.getNickname());
            reqParam.setMobile(list.getMobile());
            reqParam.setAvatar(list.getAvatar());
            reqParam.setWeixinOpenid(list.getWeixinOpenid());
            reqParam.setStatus(list.getStatus());
            reqParam.setAddTime(TransferDateUtils.date2String(list.getAddTime()));
            reqParam.setUpdateTime(TransferDateUtils.date2String(list.getUpdateTime()));
            if(list.getDeleted()==0) {
                reqParam.setDeleted(false);
            }else if(list.getDeleted()==1){
                reqParam.setDeleted(true);
            }
            paramList.add(reqParam);
        }
        long total = userService.selectTotal();
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",paramList);
        BaseReqVo baseReqVo1 = new BaseReqVo();
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/address/list")
    public BaseReqVo address(AddressRequest addressRequest){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        AddressExample addressExample = new AddressExample();
        AddressExample.Criteria criteria = addressExample.createCriteria();
        if(addressRequest.getName()!=null&&addressRequest.getName()!=""){
            criteria.andNameEqualTo(addressRequest.getName());
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

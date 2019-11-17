package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseReqVo;
import com.cskaoyan.bean.generalize.*;
import com.cskaoyan.service.GeneralizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * 推广模块所有相关接口
 */
@RestController
@RequestMapping("admin")
public class GeneralizeController {

    @Autowired
    GeneralizeService generalizeService;

    @RequestMapping("ad/list")
    public BaseReqVo queryAd(Integer page, Integer limit, String name, String content, String sort, String order) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String, Object> hashMap = generalizeService.queryAd(page, limit, name, content, sort, order);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(hashMap);
        return baseReqVo;
    }

    @RequestMapping("ad/create")
    public BaseReqVo adCreate(@RequestBody Ad ad) {
        Ad ad1 = generalizeService.adCreate(ad);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(ad1);
        return baseReqVo;
    }

    @RequestMapping("ad/update")
    public BaseReqVo adUpdate(@RequestBody Ad ad) {
        Ad ad1 = generalizeService.adUpdate(ad);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setData(ad1);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("ad/delete")
    public BaseReqVo adDelete(@RequestBody Ad ad) {
        generalizeService.adDelete(ad);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("coupon/list")
    public BaseReqVo couponList(Integer page, Integer limit, String name,
                                Integer type, Integer status, String sort, String order) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String, Object> hashMap = generalizeService.queryCoupon(page, limit, name, type, status, sort, order);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(hashMap);
        return baseReqVo;
    }

    @RequestMapping("coupon/read")
    public BaseReqVo couponRead(Integer id) {
        Coupon coupon = generalizeService.selectCouponById(id);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setData(coupon);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("coupon/listuser")
    public BaseReqVo couponListuser(Integer page, Integer limit
            , Integer couponId, String sort, String order, Integer userId, Integer status) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String, Object> hashMap =
                generalizeService.queryUserByCouponId(page, limit, couponId, sort, order, userId, status);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(hashMap);
        return baseReqVo;
    }

    @RequestMapping("coupon/create")
    public BaseReqVo couponCreate(@RequestBody Coupon coupon) {
        String regex = "^[0-9]+$";
        boolean matches = coupon.getMin().matches(regex);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        if (matches == false){
            baseReqVo.setErrno(500);
            baseReqVo.setErrmsg("参数错误");
            return baseReqVo;
        }
        matches = coupon.getDiscount().matches(regex);
        if (matches == false){
            baseReqVo.setErrno(500);
            baseReqVo.setErrmsg("参数错误");
            return baseReqVo;
        }
        matches = coupon.getLimit().matches(regex);
        if (matches == false){
            baseReqVo.setErrno(500);
            baseReqVo.setErrmsg("参数错误");
            return baseReqVo;
        }
        matches = coupon.getTotal().matches(regex);
        if (matches == false){
            baseReqVo.setErrno(500);
            baseReqVo.setErrmsg("参数错误");
            return baseReqVo;
        }
        matches = coupon.getDays().matches(regex);
        if (matches == false){
            baseReqVo.setErrno(500);
            baseReqVo.setErrmsg("参数错误");
            return baseReqVo;
        }
        Coupon c = generalizeService.couponCreate(coupon);
        baseReqVo.setData(c);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("coupon/update")
    public BaseReqVo coupinUpdate(@RequestBody Coupon coupon) {
        String regex = "^[0-9]+$";
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        boolean matches = coupon.getMin().matches(regex);
        if (matches == false){
            baseReqVo.setErrno(500);
            baseReqVo.setErrmsg("参数错误");
            return baseReqVo;
        }
        matches = coupon.getDiscount().matches(regex);
        if (matches == false){
            baseReqVo.setErrno(500);
            baseReqVo.setErrmsg("参数错误");
            return baseReqVo;
        }
        matches = coupon.getLimit().matches(regex);
        if (matches == false){
            baseReqVo.setErrno(500);
            baseReqVo.setErrmsg("参数错误");
            return baseReqVo;
        }
        matches = coupon.getTotal().matches(regex);
        if (matches == false){
            baseReqVo.setErrno(500);
            baseReqVo.setErrmsg("参数错误");
            return baseReqVo;
        }
        matches = coupon.getDays().matches(regex);
        if (matches == false){
            baseReqVo.setErrno(500);
            baseReqVo.setErrmsg("参数错误");
            return baseReqVo;
        }
        Coupon c = generalizeService.couponUpdate(coupon);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(c);
        return baseReqVo;
    }

    @RequestMapping("coupon/delete")
    public BaseReqVo couponDelete(@RequestBody Coupon coupon) {
        generalizeService.couponDelete(coupon);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("topic/list")
    public BaseReqVo topicList(Integer page, Integer limit
            , String title, String subtitle, String sort, String order) {
        HashMap<String, Object> hashMap = generalizeService.topicList(page, limit, title, subtitle, sort, order);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setData(hashMap);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("topic/create")
    public BaseReqVo topicCreate(@RequestBody Topic topic) {
        String regex = "^[0-9]+$";
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        boolean matches = topic.getPrice().matches(regex);
        if (matches == false){
            baseReqVo.setErrno(500);
            baseReqVo.setErrmsg("参数值不对");
            return baseReqVo;
        }
        Topic t = generalizeService.topicCreate(topic);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(t);
        return baseReqVo;
    }

    @RequestMapping("topic/update")
    public BaseReqVo topicUpdate(@RequestBody Topic topic) {
        String regex = "^[0-9]+$";
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        boolean matches = topic.getPrice().matches(regex);
        if (matches == false){
            baseReqVo.setErrno(500);
            baseReqVo.setErrmsg("参数值不对");
            return baseReqVo;
        }
        Topic t = generalizeService.topicUpdate(topic);
        baseReqVo.setData(t);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("topic/delete")
    public BaseReqVo topicDelete(@RequestBody Topic topic) {
        generalizeService.topicDelete(topic);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    /**
     * 这个其实是规则
     * @param page
     * @param limit
     * @param goodsId
     * @param sort
     * @param order
     * @return
     */
    @RequestMapping("groupon/list")
    public BaseReqVo grouponList(Integer page
            , Integer limit, Integer goodsId, String sort, String order) {
        HashMap<String, Object> hashMap = generalizeService.grouponList(page, limit, goodsId, sort, order);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(hashMap);
        return baseReqVo;
    }

    /**
     * 这个也是规则
     * @param grouponRules
     * @return
     */
    @RequestMapping("groupon/create")
    public BaseReqVo grouponCreate(@RequestBody GrouponRules grouponRules) {
        GrouponRules g = generalizeService.grouponRulesCreate(grouponRules);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        if (g == null){
            baseReqVo.setErrmsg("查无此商品");
            baseReqVo.setErrno(500);
            return baseReqVo;
        }
        baseReqVo.setData(g);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("groupon/update")
    public BaseReqVo grouponUpdate(@RequestBody GrouponRules grouponRules){
        GrouponRules g = generalizeService.grouponRulesUpdate(grouponRules);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        if (g == null){
            baseReqVo.setErrmsg("查无此商品");
            baseReqVo.setErrno(500);
            return baseReqVo;
        }
        baseReqVo.setData(g);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("groupon/delete")
    public BaseReqVo grouponDelete(@RequestBody GrouponRules grouponRules){
        generalizeService.grouponDelete(grouponRules);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    /**
     * 这个才是活动
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @param goodsId
     * @return
     */
    @RequestMapping("groupon/listRecord")
    public BaseReqVo grouponListRecord(Integer page, Integer limit
            , String sort, String order,Integer goodsId) {
        HashMap<String,Object> hashMap = generalizeService.grouponListRecord(page, limit, sort, order,goodsId);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(hashMap);
        return baseReqVo;
    }


}

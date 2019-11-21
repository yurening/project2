package com.cskaoyan.wx_controller;

import com.cskaoyan.bean.BaseReqVo;
import com.cskaoyan.bean.generalize.Coupon;
import com.cskaoyan.bean.mall.region.MallRegion;
import com.cskaoyan.bean.user.CouponRequest;
import com.cskaoyan.bean.user.UserRequest;
import com.cskaoyan.needdelete.BaseRespVo;
import com.cskaoyan.needdelete.UserTokenManager;
import com.cskaoyan.service.MallService;
import com.cskaoyan.service.OrderService;
import com.cskaoyan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController_wx {

    @Autowired
    UserService userService;

    @Autowired
    MallService mallService;

    @Autowired
    OrderService orderService;

    @RequestMapping("wx/search/index")
    public BaseReqVo searchIndex(){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        objectBaseReqVo.setData(userService.selectSearchIndex());
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }

    @RequestMapping("wx/search/helper")
    public BaseReqVo searchHelper(String keyword){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        objectBaseReqVo.setData(userService.searchHelper(keyword));
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }

    @RequestMapping("wx/search/clearhistory")
    public BaseReqVo searchClearHistory(ServletRequest servletRequest){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        userService.searchClearHistory(request);
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }

    //groupon
    @RequestMapping("wx/groupon/list")
    public BaseReqVo grouponlist(UserRequest userRequest){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        objectBaseReqVo.setData(userService.selectGroupon(userRequest));
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }

    @RequestMapping("wx/groupon/my")
    public BaseReqVo grouponMy(int showType,ServletRequest servletRequest){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        objectBaseReqVo.setData(userService.grouponMy(showType));
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }

    @RequestMapping("wx/groupon/detail")
    public BaseReqVo grouponDetail(int grouponId){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        objectBaseReqVo.setData(userService.grouponDetail(grouponId));
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }

    //coupon
    @RequestMapping("wx/coupon/list")
    public BaseReqVo couponList(UserRequest userRequest){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        List<Coupon> coupons = userService.selectCoupon(userRequest);
        int size = coupons.size();
        Map<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("data",coupons);
        objectObjectHashMap.put("count",size);
        objectBaseReqVo.setData(objectObjectHashMap);
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }

    @RequestMapping("wx/coupon/mylist")
    public BaseReqVo couponMyList(CouponRequest couponRequest){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        objectBaseReqVo.setData(userService.couponMyList(couponRequest));
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }

    @RequestMapping("wx/coupon/receive")
    public BaseReqVo couponReceive(@RequestBody CouponRequest couponRequest){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        if(userService.couponReceive(couponRequest)==1){
            objectBaseReqVo.setErrno(0);
            objectBaseReqVo.setErrmsg("成功");
        }else if(userService.couponReceive(couponRequest)==0) {
            objectBaseReqVo.setErrno(507);
            objectBaseReqVo.setErrmsg("您已经领取过了");
        }else if(userService.couponReceive(couponRequest)==2) {
            objectBaseReqVo.setErrno(507);
            objectBaseReqVo.setErrmsg("优惠券已领完");
        }else if(userService.couponReceive(couponRequest)==3) {
            objectBaseReqVo.setErrno(507);
            objectBaseReqVo.setErrmsg("优惠券已过期");
        }

        return objectBaseReqVo;
    }

    @RequestMapping("wx/coupon/selectlist")
    public BaseReqVo couponSelectList(CouponRequest couponRequest){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        if(couponRequest.getGroupResultId()!=0){
            objectBaseReqVo.setErrno(0);
            objectBaseReqVo.setErrmsg("成功");
            return objectBaseReqVo;
        }
        objectBaseReqVo.setData(userService.couponSelectList(couponRequest));
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }

    @RequestMapping("wx/coupon/exchange")
    public BaseReqVo couponExchange(@RequestBody CouponRequest couponRequest, ServletRequest servletRequest){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        int i = userService.couponExchange(couponRequest,request);
        if(i==1){
            objectBaseReqVo.setErrno(0);
            objectBaseReqVo.setErrmsg("成功");
        }else if(i==0) {
            objectBaseReqVo.setErrno(507);
            objectBaseReqVo.setErrmsg("您已经领取过了");
        }else if(i==2) {
            objectBaseReqVo.setErrno(507);
            objectBaseReqVo.setErrmsg("优惠券已领完");
        }else if(i==3) {
            objectBaseReqVo.setErrno(507);
            objectBaseReqVo.setErrmsg("优惠券已过期");
        }

        return objectBaseReqVo;
    }




    @RequestMapping("wx/region/list")
    public BaseReqVo regionList(Integer pid){
        List<MallRegion> mallRegionList =  mallService.regionListByPid(pid);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(mallRegionList);
        return baseReqVo;
    }

    @GetMapping("wx/user/index")
    public Object list(HttpServletRequest request) {
        //前端写了一个token放在请求头中
        //*************************
        //获得请求头
        String tokenKey = request.getHeader("5cn9hnzh0lgki9n69bxjegsafqzocpq2");
        Integer userId = UserTokenManager.getUserId(tokenKey);

        //通过请求头获得userId，进而可以获得一切关于user的信息
        //**************************
        if (userId == null) {
            return BaseRespVo.fail();
        }
        HashMap<String,Object> orderStatusByUserId = orderService.countOrderStatusByUserId(userId);
        HashMap<String,Object> data = new HashMap<>();
        //***********************************
        //根据userId查询订单信息
        data.put("order", orderStatusByUserId);
        //***********************************
        return BaseRespVo.ok(data);
    }

}


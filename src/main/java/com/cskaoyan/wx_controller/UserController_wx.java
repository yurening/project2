package com.cskaoyan.wx_controller;

import com.cskaoyan.bean.BaseReqVo;
import com.cskaoyan.bean.user.CouponRequest;
import com.cskaoyan.bean.user.UserRequest;
import com.cskaoyan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController_wx {

    @Autowired
    UserService userService;
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

    //groupon
    @RequestMapping("wx/groupon/list")
    public BaseReqVo grouponMy(UserRequest userRequest){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        objectBaseReqVo.setData(userService.selectGroupon(userRequest));
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }

    //coupon
    @RequestMapping("wx/coupon/list")
    public BaseReqVo couponList(UserRequest userRequest){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        objectBaseReqVo.setData(userService.selectCoupon(userRequest));
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
        }else {
            objectBaseReqVo.setErrno(507);
            objectBaseReqVo.setErrmsg("领取失败");
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
    public BaseReqVo couponExchange(@RequestBody CouponRequest couponRequest){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        int i = userService.couponExchange(couponRequest);
        if(i==0){
            objectBaseReqVo.setErrno(507);
            objectBaseReqVo.setErrmsg("领取失败");
            return objectBaseReqVo;
        }
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }

    public static class DataBean {
        /**
         * cartTotal : {"goodsCount":22,"checkedGoodsCount":0,"goodsAmount":1820,"checkedGoodsAmount":0}
         * cartList : [{"id":999,"userId":8,"goodsId":1181041,"goodsSn":"123456","goodsName":"小熊蛋糕","productId":326,"price":50,"number":2,"specifications":["五英寸"],"updateChecked":false,"picUrl":"http://192.168.2.100:8081/wx/storage/fetch/re67dfr11xlzogkt5oyf.jpg","addTime":"2019-11-19 08:58:25","updateTime":"2019-11-19 22:38:17","deleted":false},{"id":1000,"userId":8,"goodsId":1181147,"goodsSn":"6666666","goodsName":"小猪佩奇","productId":709,"price":671,"number":2,"specifications":["200","222"],"updateChecked":false,"picUrl":"http://192.168.2.100:8081/wx/storage/fetch/dhg9j5xmzfck4xpyu18z.jpeg","addTime":"2019-11-19 08:59:48","updateTime":"2019-11-19 22:38:17","deleted":false},{"id":1077,"userId":8,"goodsId":1181112,"goodsSn":"12","goodsName":"12121","productId":716,"price":21,"number":18,"specifications":["12"],"updateChecked":false,"picUrl":"http://192.168.2.100:8081/wx/storage/fetch/wdc87ltjnhp5gx1elcab.jpg","addTime":"2019-11-19 22:38:45","updateTime":"2019-11-19 22:41:04","deleted":false}]
         */

        private CartTotalBean cartTotal;
        private List<CartListBean> cartList;

        public CartTotalBean getCartTotal() {
            return cartTotal;
        }

        public void setCartTotal(CartTotalBean cartTotal) {
            this.cartTotal = cartTotal;
        }

        public List<CartListBean> getCartList() {
            return cartList;
        }

        public void setCartList(List<CartListBean> cartList) {
            this.cartList = cartList;
        }

        public static class CartTotalBean {
            /**
             * goodsCount : 22
             * checkedGoodsCount : 0
             * goodsAmount : 1820
             * checkedGoodsAmount : 0
             */

            private int goodsCount;
            private int checkedGoodsCount;
            private int goodsAmount;
            private int checkedGoodsAmount;

            public int getGoodsCount() {
                return goodsCount;
            }

            public void setGoodsCount(int goodsCount) {
                this.goodsCount = goodsCount;
            }

            public int getCheckedGoodsCount() {
                return checkedGoodsCount;
            }

            public void setCheckedGoodsCount(int checkedGoodsCount) {
                this.checkedGoodsCount = checkedGoodsCount;
            }

            public int getGoodsAmount() {
                return goodsAmount;
            }

            public void setGoodsAmount(int goodsAmount) {
                this.goodsAmount = goodsAmount;
            }

            public int getCheckedGoodsAmount() {
                return checkedGoodsAmount;
            }

            public void setCheckedGoodsAmount(int checkedGoodsAmount) {
                this.checkedGoodsAmount = checkedGoodsAmount;
            }
        }

        public static class CartListBean {
            /**
             * id : 999
             * userId : 8
             * goodsId : 1181041
             * goodsSn : 123456
             * goodsName : 小熊蛋糕
             * productId : 326
             * price : 50
             * number : 2
             * specifications : ["五英寸"]
             * updateChecked : false
             * picUrl : http://192.168.2.100:8081/wx/storage/fetch/re67dfr11xlzogkt5oyf.jpg
             * addTime : 2019-11-19 08:58:25
             * updateTime : 2019-11-19 22:38:17
             * deleted : false
             */

            private int id;
            private int userId;
            private int goodsId;
            private String goodsSn;
            private String goodsName;
            private int productId;
            private int price;
            private int number;
            private boolean checked;
            private String picUrl;
            private String addTime;
            private String updateTime;
            private boolean deleted;
            private List<String> specifications;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsSn() {
                return goodsSn;
            }

            public void setGoodsSn(String goodsSn) {
                this.goodsSn = goodsSn;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public boolean isChecked() {
                return checked;
            }

            public void setChecked(boolean checked) {
                this.checked = checked;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public boolean isDeleted() {
                return deleted;
            }

            public void setDeleted(boolean deleted) {
                this.deleted = deleted;
            }

            public List<String> getSpecifications() {
                return specifications;
            }

            public void setSpecifications(List<String> specifications) {
                this.specifications = specifications;
            }
        }
    }
}


package com.cskaoyan.bean.wx_index;

import com.cskaoyan.bean.user.Cart;

import java.util.List;

public class CartIndex {
    /**
     * cartTotal : {"goodsCount":22,"checkedGoodsCount":0,"goodsAmount":1820,"checkedGoodsAmount":0}
     * cartList : [{"id":999,"userId":8,"goodsId":1181041,"goodsSn":"123456","goodsName":"小熊蛋糕","productId":326,"price":50,"number":2,"specifications":["五英寸"],"updateChecked":false,"picUrl":"http://192.168.2.100:8081/wx/storage/fetch/re67dfr11xlzogkt5oyf.jpg","addTime":"2019-11-19 08:58:25","updateTime":"2019-11-19 22:38:17","deleted":false},{"id":1000,"userId":8,"goodsId":1181147,"goodsSn":"6666666","goodsName":"小猪佩奇","productId":709,"price":671,"number":2,"specifications":["200","222"],"updateChecked":false,"picUrl":"http://192.168.2.100:8081/wx/storage/fetch/dhg9j5xmzfck4xpyu18z.jpeg","addTime":"2019-11-19 08:59:48","updateTime":"2019-11-19 22:38:17","deleted":false},{"id":1077,"userId":8,"goodsId":1181112,"goodsSn":"12","goodsName":"12121","productId":716,"price":21,"number":18,"specifications":["12"],"updateChecked":false,"picUrl":"http://192.168.2.100:8081/wx/storage/fetch/wdc87ltjnhp5gx1elcab.jpg","addTime":"2019-11-19 22:38:45","updateTime":"2019-11-19 22:41:04","deleted":false}]
     */

    private CartTotalBean cartTotal;
    private List<Cart> cartList;

    public CartTotalBean getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(CartTotalBean cartTotal) {
        this.cartTotal = cartTotal;
    }

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public static class CartTotalBean {
        /**
         * goodsCount : 22
         * checkedGoodsCount : 0
         * goodsAmount : 1820
         * checkedGoodsAmount : 0
         */

        private Integer goodsCount;
        private Integer checkedGoodsCount;
        private Double goodsAmount;
        private Double checkedGoodsAmount;

        public Integer getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(Integer goodsCount) {
            this.goodsCount = goodsCount;
        }

        public Integer getCheckedGoodsCount() {
            return checkedGoodsCount;
        }

        public void setCheckedGoodsCount(Integer checkedGoodsCount) {
            this.checkedGoodsCount = checkedGoodsCount;
        }

        public Double getGoodsAmount() {
            return goodsAmount;
        }

        public void setGoodsAmount(Double goodsAmount) {
            this.goodsAmount = goodsAmount;
        }

        public Double getCheckedGoodsAmount() {
            return checkedGoodsAmount;
        }

        public void setCheckedGoodsAmount(Double checkedGoodsAmount) {
            this.checkedGoodsAmount = checkedGoodsAmount;
        }
    }
}


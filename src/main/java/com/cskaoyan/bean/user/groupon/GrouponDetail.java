package com.cskaoyan.bean.user.groupon;

import com.cskaoyan.bean.generalize.Groupon;
import com.cskaoyan.bean.generalize.GrouponRules;

import java.util.List;

public class GrouponDetail {

    /**
     * errno : 0
     * data : {"creator":{"nickname":"dr lan","avatar":""},"groupon":{"id":1,"orderId":1,"grouponId":0,"rulesId":1,"userId":1,"creatorUserId":1,"addTime":"2019-07-04 10:25:48","updateTime":"2019-07-05 10:25:54","shareUrl":"https://cskaoyan.oss-cn-beijing.aliyuncs.com/04d01c73f3dd4f9da5709e1c0fb1a231","payed":true,"deleted":false},"joiners":[{"nickname":"dr lan","avatar":""}],"orderInfo":{"consignee":"ll","address":"北京市 市辖区 西城区 aaaaaaaaaa","addTime":"2019-08-20 02:47:19","orderSn":"20190820807352","actualPrice":119,"mobile":"13260061505","orderStatusText":"已取消(系统)","goodsPrice":129,"id":1,"freightPrice":0,"handleOption":{"cancel":false,"delete":true,"pay":false,"comment":false,"confirm":false,"refund":false,"rebuy":false}},"orderGoods":[{"number":1,"picUrl":"http://yanxuan.nosdn.127.net/203cb83d93606865e3ddde57b69b9e9a.png","orderId":1,"goodsId":1152008,"goodsSpecificationValues":["标准"],"id":1,"goodsName":"魔兽世界 部落 护腕 一只","retailPrice":29},{"number":1,"picUrl":"http://192.168.2.100:8081/wx/storage/fetch/pmhbgnm35y5d51pmxaha.png","orderId":1,"goodsId":1181005,"goodsSpecificationValues":["规格值1","规格值2","规格值3"],"id":2,"goodsName":"张三","retailPrice":100}],"rules":{"id":1,"goodsId":1039051,"goodsName":"多功能午睡枕","picUrl":"http://yanxuan.nosdn.127.net/c8ca0600fa7ba11ca8be6a3173dd38c9.png","discount":20,"discountMember":20,"addTime":"2018-11-08 08:41:44","updateTime":"2018-11-08 08:41:44","expireTime":"2019-12-30 19:00:00","deleted":false},"linkGrouponId":1}
     * errmsg : 成功
     */

    private int errno;
    private DataBean data;
    private String errmsg;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public static class DataBean {
        /**
         * creator : {"nickname":"dr lan","avatar":""}
         * groupon : {"id":1,"orderId":1,"grouponId":0,"rulesId":1,"userId":1,"creatorUserId":1,"addTime":"2019-07-04 10:25:48","updateTime":"2019-07-05 10:25:54","shareUrl":"https://cskaoyan.oss-cn-beijing.aliyuncs.com/04d01c73f3dd4f9da5709e1c0fb1a231","payed":true,"deleted":false}
         * joiners : [{"nickname":"dr lan","avatar":""}]
         * orderInfo : {"consignee":"ll","address":"北京市 市辖区 西城区 aaaaaaaaaa","addTime":"2019-08-20 02:47:19","orderSn":"20190820807352","actualPrice":119,"mobile":"13260061505","orderStatusText":"已取消(系统)","goodsPrice":129,"id":1,"freightPrice":0,"handleOption":{"cancel":false,"delete":true,"pay":false,"comment":false,"confirm":false,"refund":false,"rebuy":false}}
         * orderGoods : [{"number":1,"picUrl":"http://yanxuan.nosdn.127.net/203cb83d93606865e3ddde57b69b9e9a.png","orderId":1,"goodsId":1152008,"goodsSpecificationValues":["标准"],"id":1,"goodsName":"魔兽世界 部落 护腕 一只","retailPrice":29},{"number":1,"picUrl":"http://192.168.2.100:8081/wx/storage/fetch/pmhbgnm35y5d51pmxaha.png","orderId":1,"goodsId":1181005,"goodsSpecificationValues":["规格值1","规格值2","规格值3"],"id":2,"goodsName":"张三","retailPrice":100}]
         * rules : {"id":1,"goodsId":1039051,"goodsName":"多功能午睡枕","picUrl":"http://yanxuan.nosdn.127.net/c8ca0600fa7ba11ca8be6a3173dd38c9.png","discount":20,"discountMember":20,"addTime":"2018-11-08 08:41:44","updateTime":"2018-11-08 08:41:44","expireTime":"2019-12-30 19:00:00","deleted":false}
         * linkGrouponId : 1
         */

        private CreatorBean creator;
        private Groupon groupon;
        private OrderInfoBean orderInfo;
        private GrouponRules rules;
        private int linkGrouponId;
        private List<JoinersBean> joiners;
        private List<OrderGoodsBean> orderGoods;

        public CreatorBean getCreator() {
            return creator;
        }

        public void setCreator(CreatorBean creator) {
            this.creator = creator;
        }

        public Groupon getGroupon() {
            return groupon;
        }

        public void setGroupon(Groupon groupon) {
            this.groupon = groupon;
        }

        public OrderInfoBean getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(OrderInfoBean orderInfo) {
            this.orderInfo = orderInfo;
        }

        public GrouponRules getRules() {
            return rules;
        }

        public void setRules(GrouponRules rules) {
            this.rules = rules;
        }

        public int getLinkGrouponId() {
            return linkGrouponId;
        }

        public void setLinkGrouponId(int linkGrouponId) {
            this.linkGrouponId = linkGrouponId;
        }

        public List<JoinersBean> getJoiners() {
            return joiners;
        }

        public void setJoiners(List<JoinersBean> joiners) {
            this.joiners = joiners;
        }

        public List<OrderGoodsBean> getOrderGoods() {
            return orderGoods;
        }

        public void setOrderGoods(List<OrderGoodsBean> orderGoods) {
            this.orderGoods = orderGoods;
        }

        public static class CreatorBean {
            /**
             * nickname : dr lan
             * avatar :
             */

            private String nickname;
            private String avatar;

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
        }

        public static class GrouponBean {
            /**
             * id : 1
             * orderId : 1
             * grouponId : 0
             * rulesId : 1
             * userId : 1
             * creatorUserId : 1
             * addTime : 2019-07-04 10:25:48
             * updateTime : 2019-07-05 10:25:54
             * shareUrl : https://cskaoyan.oss-cn-beijing.aliyuncs.com/04d01c73f3dd4f9da5709e1c0fb1a231
             * payed : true
             * deleted : false
             */

            private int id;
            private int orderId;
            private int grouponId;
            private int rulesId;
            private int userId;
            private int creatorUserId;
            private String addTime;
            private String updateTime;
            private String shareUrl;
            private boolean payed;
            private boolean deleted;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public int getGrouponId() {
                return grouponId;
            }

            public void setGrouponId(int grouponId) {
                this.grouponId = grouponId;
            }

            public int getRulesId() {
                return rulesId;
            }

            public void setRulesId(int rulesId) {
                this.rulesId = rulesId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getCreatorUserId() {
                return creatorUserId;
            }

            public void setCreatorUserId(int creatorUserId) {
                this.creatorUserId = creatorUserId;
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

            public String getShareUrl() {
                return shareUrl;
            }

            public void setShareUrl(String shareUrl) {
                this.shareUrl = shareUrl;
            }

            public boolean isPayed() {
                return payed;
            }

            public void setPayed(boolean payed) {
                this.payed = payed;
            }

            public boolean isDeleted() {
                return deleted;
            }

            public void setDeleted(boolean deleted) {
                this.deleted = deleted;
            }
        }

        public static class OrderInfoBean {
            /**
             * consignee : ll
             * address : 北京市 市辖区 西城区 aaaaaaaaaa
             * addTime : 2019-08-20 02:47:19
             * orderSn : 20190820807352
             * actualPrice : 119.0
             * mobile : 13260061505
             * orderStatusText : 已取消(系统)
             * goodsPrice : 129.0
             * id : 1
             * freightPrice : 0.0
             * handleOption : {"cancel":false,"delete":true,"pay":false,"comment":false,"confirm":false,"refund":false,"rebuy":false}
             */

            private String consignee;
            private String address;
            private String addTime;
            private String orderSn;
            private double actualPrice;
            private String mobile;
            private String orderStatusText;
            private double goodsPrice;
            private int id;
            private double freightPrice;
            private HandleOptionBean handleOption;

            public String getConsignee() {
                return consignee;
            }

            public void setConsignee(String consignee) {
                this.consignee = consignee;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public String getOrderSn() {
                return orderSn;
            }

            public void setOrderSn(String orderSn) {
                this.orderSn = orderSn;
            }

            public double getActualPrice() {
                return actualPrice;
            }

            public void setActualPrice(double actualPrice) {
                this.actualPrice = actualPrice;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getOrderStatusText() {
                return orderStatusText;
            }

            public void setOrderStatusText(String orderStatusText) {
                this.orderStatusText = orderStatusText;
            }

            public double getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(double goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public double getFreightPrice() {
                return freightPrice;
            }

            public void setFreightPrice(double freightPrice) {
                this.freightPrice = freightPrice;
            }

            public HandleOptionBean getHandleOption() {
                return handleOption;
            }

            public void setHandleOption(HandleOptionBean handleOption) {
                this.handleOption = handleOption;
            }

            public static class HandleOptionBean {
                /**
                 * cancel : false
                 * delete : true
                 * pay : false
                 * comment : false
                 * confirm : false
                 * refund : false
                 * rebuy : false
                 */

                private boolean cancel;
                private boolean delete;
                private boolean pay;
                private boolean comment;
                private boolean confirm;
                private boolean refund;
                private boolean rebuy;

                public boolean isCancel() {
                    return cancel;
                }

                public void setCancel(boolean cancel) {
                    this.cancel = cancel;
                }

                public boolean isDelete() {
                    return delete;
                }

                public void setDelete(boolean delete) {
                    this.delete = delete;
                }

                public boolean isPay() {
                    return pay;
                }

                public void setPay(boolean pay) {
                    this.pay = pay;
                }

                public boolean isComment() {
                    return comment;
                }

                public void setComment(boolean comment) {
                    this.comment = comment;
                }

                public boolean isConfirm() {
                    return confirm;
                }

                public void setConfirm(boolean confirm) {
                    this.confirm = confirm;
                }

                public boolean isRefund() {
                    return refund;
                }

                public void setRefund(boolean refund) {
                    this.refund = refund;
                }

                public boolean isRebuy() {
                    return rebuy;
                }

                public void setRebuy(boolean rebuy) {
                    this.rebuy = rebuy;
                }
            }
        }

        public static class RulesBean {
            /**
             * id : 1
             * goodsId : 1039051
             * goodsName : 多功能午睡枕
             * picUrl : http://yanxuan.nosdn.127.net/c8ca0600fa7ba11ca8be6a3173dd38c9.png
             * discount : 20
             * discountMember : 20
             * addTime : 2018-11-08 08:41:44
             * updateTime : 2018-11-08 08:41:44
             * expireTime : 2019-12-30 19:00:00
             * deleted : false
             */

            private int id;
            private int goodsId;
            private String goodsName;
            private String picUrl;
            private int discount;
            private int discountMember;
            private String addTime;
            private String updateTime;
            private String expireTime;
            private boolean deleted;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public int getDiscount() {
                return discount;
            }

            public void setDiscount(int discount) {
                this.discount = discount;
            }

            public int getDiscountMember() {
                return discountMember;
            }

            public void setDiscountMember(int discountMember) {
                this.discountMember = discountMember;
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

            public String getExpireTime() {
                return expireTime;
            }

            public void setExpireTime(String expireTime) {
                this.expireTime = expireTime;
            }

            public boolean isDeleted() {
                return deleted;
            }

            public void setDeleted(boolean deleted) {
                this.deleted = deleted;
            }
        }

        public static class JoinersBean {
            /**
             * nickname : dr lan
             * avatar :
             */

            private String nickname;
            private String avatar;

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
        }

        public static class OrderGoodsBean {
            /**
             * number : 1
             * picUrl : http://yanxuan.nosdn.127.net/203cb83d93606865e3ddde57b69b9e9a.png
             * orderId : 1
             * goodsId : 1152008
             * goodsSpecificationValues : ["标准"]
             * id : 1
             * goodsName : 魔兽世界 部落 护腕 一只
             * retailPrice : 29.0
             */

            private int number;
            private String picUrl;
            private int orderId;
            private int goodsId;
            private int id;
            private String goodsName;
            private double retailPrice;
            private List<String> goodsSpecificationValues;

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public double getRetailPrice() {
                return retailPrice;
            }

            public void setRetailPrice(double retailPrice) {
                this.retailPrice = retailPrice;
            }

            public List<String> getGoodsSpecificationValues() {
                return goodsSpecificationValues;
            }

            public void setGoodsSpecificationValues(List<String> goodsSpecificationValues) {
                this.goodsSpecificationValues = goodsSpecificationValues;
            }
        }
    }
}

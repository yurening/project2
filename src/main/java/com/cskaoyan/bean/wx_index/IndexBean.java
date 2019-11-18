package com.cskaoyan.bean.wx_index;

import com.cskaoyan.bean.goods.Goods;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
  public  class IndexBean {
        private List<NewGoodsListBean> newGoodsList;
        private List<CouponListBean> couponList;
        private List<ChannelBean> channel;
        private List<GrouponListBean> grouponList;
        private List<BannerBean> banner;
        private List<BrandListBean> brandList;
        private List<HotGoodsListBean> hotGoodsList;
        private List<TopicListBean> topicList;
        private List<FloorGoodsListBean> floorGoodsList;

        public List<NewGoodsListBean> getNewGoodsList() {
            return newGoodsList;
        }

        public void setNewGoodsList(List<NewGoodsListBean> newGoodsList) {
            this.newGoodsList = newGoodsList;
        }

        public List<CouponListBean> getCouponList() {
            return couponList;
        }

        public void setCouponList(List<CouponListBean> couponList) {
            this.couponList = couponList;
        }

        public List<ChannelBean> getChannel() {
            return channel;
        }

        public void setChannel(List<ChannelBean> channel) {
            this.channel = channel;
        }

        public List<GrouponListBean> getGrouponList() {
            return grouponList;
        }

        public void setGrouponList(List<GrouponListBean> grouponList) {
            this.grouponList = grouponList;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<BrandListBean> getBrandList() {
            return brandList;
        }

        public void setBrandList(List<BrandListBean> brandList) {
            this.brandList = brandList;
        }

        public List<HotGoodsListBean> getHotGoodsList() {
            return hotGoodsList;
        }

        public void setHotGoodsList(List<HotGoodsListBean> hotGoodsList) {
            this.hotGoodsList = hotGoodsList;
        }

        public List<TopicListBean> getTopicList() {
            return topicList;
        }

        public void setTopicList(List<TopicListBean> topicList) {
            this.topicList = topicList;
        }

        public List<FloorGoodsListBean> getFloorGoodsList() {
            return floorGoodsList;
        }

        public void setFloorGoodsList(List<FloorGoodsListBean> floorGoodsList) {
            this.floorGoodsList = floorGoodsList;
        }

        public static class NewGoodsListBean {
            /**
             * id : 1181171
             * name : hhhhhhh
             * brief : 11
             * picUrl : http://192.168.2.100:8081/wx/storage/fetch/n11z1s8wr0nlzsa2oniu.jpg
             * isNew : true
             * isHot : true
             * counterPrice : 123.0
             * retailPrice : 123.0
             */

            private int id;
            private String name;
            private String brief;
            private String picUrl;
            private boolean isNew;
            private boolean isHot;
            private BigDecimal counterPrice;
            private BigDecimal retailPrice;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getBrief() {
                return brief;
            }

            public void setBrief(String brief) {
                this.brief = brief;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public boolean isIsNew() {
                return isNew;
            }

            public void setIsNew(boolean isNew) {
                this.isNew = isNew;
            }

            public boolean isIsHot() {
                return isHot;
            }

            public void setIsHot(boolean isHot) {
                this.isHot = isHot;
            }

            public BigDecimal getCounterPrice() {
                return counterPrice;
            }

            public void setCounterPrice(BigDecimal counterPrice) {
                this.counterPrice = counterPrice;
            }

            public BigDecimal getRetailPrice() {
                return retailPrice;
            }

            public void setRetailPrice(BigDecimal retailPrice) {
                this.retailPrice = retailPrice;
            }
        }

        public static class CouponListBean {
            /**
             * id : 130
             * name : 11111
             * desc :
             * tag :
             * discount : 0.0
             * min : 0.0
             * days : 0
             * startTime : 2019-11-12 00:00:00
             * endTime : 2019-11-29 00:00:00
             */

            private int id;
            private String name;
            private String desc;
            private String tag;
            private String discount;
            private String min;
            private String days;
            private Date startTime;
            private Date endTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getDiscount() {
                return discount;
            }

            public void setDiscount(String discount) {
                this.discount = discount;
            }

            public String getMin() {
                return min;
            }

            public void setMin(String min) {
                this.min = min;
            }

            public String getDays() {
                return days;
            }

            public void setDays(String days) {
                this.days = days;
            }

            public Date getStartTime() {
                return startTime;
            }

            public void setStartTime(Date startTime) {
                this.startTime = startTime;
            }

            public Date getEndTime() {
                return endTime;
            }

            public void setEndTime(Date endTime) {
                this.endTime = endTime;
            }
        }

        public static class ChannelBean {
            /**
             * id : 1036094
             * name : hello
             * iconUrl : http://192.168.2.100:8081/wx/storage/fetch/52k6zfb9x6rwtfsel9c8.jpg
             */

            private int id;
            private String name;
            private String iconUrl;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }
        }

        public static class GrouponListBean {
            /**
             * groupon_price : 77.0
             * goods : {"id":1129015,"name":"阿瓦提长绒棉超柔弱捻浴巾","brief":"瞬吸亲肤，0掉毛率","picUrl":"http://yanxuan.nosdn.127.net/fc11a482efeece9630548d8b350e7f54.png","counterPrice":109,"retailPrice":89}
             * groupon_member : 12
             */

            private BigDecimal groupon_price;
            private GoodsBean goods;
            private int groupon_member;

            public BigDecimal getGroupon_price() {
                return groupon_price;
            }

            public void setGroupon_price(BigDecimal groupon_price) {
                this.groupon_price = groupon_price;
            }

            public GoodsBean getGoods() {
                return goods;
            }

            public void setGoods(GoodsBean goods) {
                this.goods = goods;
            }

            public int getGroupon_member() {
                return groupon_member;
            }

            public void setGroupon_member(int groupon_member) {
                this.groupon_member = groupon_member;
            }

            public static class GoodsBean {
                /**
                 * id : 1129015
                 * name : 阿瓦提长绒棉超柔弱捻浴巾
                 * brief : 瞬吸亲肤，0掉毛率
                 * picUrl : http://yanxuan.nosdn.127.net/fc11a482efeece9630548d8b350e7f54.png
                 * counterPrice : 109.0
                 * retailPrice : 89.0
                 */

                private int id;
                private String name;
                private String brief;
                private String picUrl;
                private BigDecimal counterPrice;
                private BigDecimal retailPrice;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getBrief() {
                    return brief;
                }

                public void setBrief(String brief) {
                    this.brief = brief;
                }

                public String getPicUrl() {
                    return picUrl;
                }

                public void setPicUrl(String picUrl) {
                    this.picUrl = picUrl;
                }

                public BigDecimal getCounterPrice() {
                    return counterPrice;
                }

                public void setCounterPrice(BigDecimal counterPrice) {
                    this.counterPrice = counterPrice;
                }

                public BigDecimal getRetailPrice() {
                    return retailPrice;
                }

                public void setRetailPrice(BigDecimal retailPrice) {
                    this.retailPrice = retailPrice;
                }
            }
        }

        public static class BannerBean {
            /**
             * id : 1
             * name : 合作 谁是你的菜
             * link :
             * url : http://yanxuan.nosdn.127.net/65091eebc48899298171c2eb6696fe27.jpg
             * position : 1
             * content : 合作 谁是你的菜
             * enabled : true
             * addTime : 2018-01-31 19:00:00
             * updateTime : 2018-01-31 19:00:00
             * deleted : false
             */

            private int id;
            private String name;
            private String link;
            private String url;
            private int position;
            private String content;
            private boolean enabled;
            private Date addTime;
            private Date updateTime;
            private boolean deleted;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getPosition() {
                return position;
            }

            public void setPosition(int position) {
                this.position = position;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public Date getAddTime() {
                return addTime;
            }

            public void setAddTime(Date addTime) {
                this.addTime = addTime;
            }

            public Date getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(Date updateTime) {
                this.updateTime = updateTime;
            }

            public boolean isDeleted() {
                return deleted;
            }

            public void setDeleted(boolean deleted) {
                this.deleted = deleted;
            }
        }

        public static class BrandListBean {
            /**
             * id : 1046069
             * name : aaa
             * desc : aaa
             * picUrl : http://192.168.2.100:8081/wx/storage/fetch/75bdt7x57a5ylr7usg0p.jpg
             * floorPrice : 1.0
             */

            private int id;
            private String name;
            private String desc;
            private String picUrl;
            private BigDecimal floorPrice;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public BigDecimal getFloorPrice() {
                return floorPrice;
            }

            public void setFloorPrice(BigDecimal floorPrice) {
                this.floorPrice = floorPrice;
            }
        }

        public static class HotGoodsListBean {
            /**
             * id : 1181171
             * name : hhhhhhh
             * brief : 11
             * picUrl : http://192.168.2.100:8081/wx/storage/fetch/n11z1s8wr0nlzsa2oniu.jpg
             * isNew : true
             * isHot : true
             * counterPrice : 123.0
             * retailPrice : 123.0
             */

            private int id;
            private String name;
            private String brief;
            private String picUrl;
            private boolean isNew;
            private boolean isHot;
            private BigDecimal counterPrice;
            private BigDecimal retailPrice;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getBrief() {
                return brief;
            }

            public void setBrief(String brief) {
                this.brief = brief;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public boolean isIsNew() {
                return isNew;
            }

            public void setIsNew(boolean isNew) {
                this.isNew = isNew;
            }

            public boolean isIsHot() {
                return isHot;
            }

            public void setIsHot(boolean isHot) {
                this.isHot = isHot;
            }

            public BigDecimal getCounterPrice() {
                return counterPrice;
            }

            public void setCounterPrice(BigDecimal counterPrice) {
                this.counterPrice = counterPrice;
            }

            public BigDecimal getRetailPrice() {
                return retailPrice;
            }

            public void setRetailPrice(BigDecimal retailPrice) {
                this.retailPrice = retailPrice;
            }
        }

        public static class TopicListBean {
            /**
             * id : 375
             * title : 真实伤害皮肤
             * subtitle : 阿卡丽带资入团！！！
             * price : 79.0
             * readCount : 100
             * picUrl : http://192.168.2.100:8081/wx/storage/fetch/i57i17jvberhvd1ptv53.jpg
             */

            private int id;
            private String title;
            private String subtitle;
            private String price;
            private String readCount;
            private String picUrl;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getReadCount() {
                return readCount;
            }

            public void setReadCount(String readCount) {
                this.readCount = readCount;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }
        }

        public static class FloorGoodsListBean {
            /**
             * name : hello
             * goodsList : []
             * id : 1036094
             */

            private String name;
            private int id;
            private List<Goods> goodsList;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public List<Goods> getGoodsList() {
                return goodsList;
            }

            public void setGoodsList(List<Goods> goodsList) {
                this.goodsList = goodsList;
            }
        }
    }
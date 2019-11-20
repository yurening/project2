package com.cskaoyan.bean.mall.address;

public class AddressInfo {

    /**
     * isDefault : false
     * detailedAddress : 北京市市辖区西城区 发生发顺丰as规范化股份
     * name : 大丰收
     * mobile : 17654332456
     * id : 64
     */

    private boolean isDefault;
    private String detailedAddress;
    private String name;
    private String mobile;
    private int id;

    public boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

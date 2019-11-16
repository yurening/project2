package com.cskaoyan.bean.user;

import lombok.Data;

@Data
public class ReqParam {
    int id;
    String username;
    String password;
    int gender;
    String birthday;
    String lastLoginTime;
    String lastLoginIp;
    int userLevel;
    String nickname;
    String mobile;
    String avatar;
    String weixinOpenid;
    int status;
    String addTime;
    String updateTime;
    boolean deleted;
}

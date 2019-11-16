package com.cskaoyan.bean.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.sql.Date;

@Data
public class ReqParamFromDb {
    int id;
    String username;
    String password;
    int gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date birthday;
    Date lastLoginTime;
    String lastLoginIp;
    int userlevel;
    String nickname;
    String mobile;
    String avatar;
    String weixinOpenid;
    int status;
    Date addTime;
    Date updateTime;
    int deleted;
}

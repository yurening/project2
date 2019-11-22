/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/22
 * Time: 10:40
 */
package com.cskaoyan.bean;

import lombok.Data;

import java.util.Map;

@Data
public class Wx_wx_login {
    private String code;
    private Map<String,Object> userInfo;
}

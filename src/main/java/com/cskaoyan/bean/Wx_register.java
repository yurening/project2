/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/21
 * Time: 16:58
 */
package com.cskaoyan.bean;

import lombok.Data;

@Data
public class Wx_register {
    private String username;
    private String password;
    private String mobile;
    private String code;
    private String wxCode;
}

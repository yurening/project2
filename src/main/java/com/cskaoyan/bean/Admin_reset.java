/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/22
 * Time: 17:38
 */
package com.cskaoyan.bean;

import lombok.Data;

@Data
public class Admin_reset {
    private String oldPassword;
    private String newPassword;
    private String newPassword2;
}

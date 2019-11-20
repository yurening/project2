package com.cskaoyan.service;

import com.cskaoyan.bean.Admin;

import java.util.List;

public interface AuthService {

    Admin getUsernameByUsername(String username);

    String getRoleNameById(String roleId);

    List<String> getPermsNameByRoleId(String roleId);

    int getGoodsTotal();

    int getUserTotal();

    int getProductTotal();

    int getOrderTotal();

    List<String> getPermsMethodNameByRoleId(String roleId);
}

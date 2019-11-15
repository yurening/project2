/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/15
 * Time: 17:26
 */
package com.cskaoyan.service;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper mapper;

    @Override
    public Admin getUsernameByUsername(String username) {
        return mapper.selectByUsername(username);
    }

    @Override
    public String getRoleNameById(String roleId) {
        return mapper.getRoleNameById(roleId);
    }

    @Override
    public List<String> getPermsNameByRoleId(String roleId) {
        return mapper.getPermsNameByRoleId(roleId);
    }

    @Override
    public int getGoodsTotal() {
        return mapper.getGoodsTotal();
    }

    @Override
    public int getUserTotal() {
        return mapper.getUserTotal();
    }

    @Override
    public int getProductTotal() {
        return mapper.getProductTotal();
    }

    @Override
    public int getOrderTotal() {
        return mapper.getOrderTotal();
    }
}

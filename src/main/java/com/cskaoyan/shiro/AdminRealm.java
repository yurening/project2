package com.cskaoyan.shiro;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.mapper.AuthMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class AdminRealm extends AuthorizingRealm {

    @Autowired
    AuthMapper authMapper;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();

        Admin admin = authMapper.selectByUsername(username);
        String passwordFromDb = admin.getPassword();
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(admin, passwordFromDb, getName());

        return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Admin primaryPrincipal = (Admin) principalCollection.getPrimaryPrincipal();
        String[] role_ids = primaryPrincipal.getRole_ids();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<String> permissions = new ArrayList<>();
        boolean permission = Arrays.toString(role_ids).contains("1");
        if (permission){
            permissions.add("*");
        }else {
            for (String roleid: role_ids) {
                List<String> perms = authMapper.getPermsNameByRoleId(roleid);
                permissions.addAll(perms);
            }
            permissions.add("*"); //先拥有全部权限
        }
//      authorizationInfo.addStringPermission("user:query");
        authorizationInfo.addStringPermissions(permissions);
        return authorizationInfo;
    }

}

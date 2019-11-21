/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/19
 * Time: 17:34
 */
package com.cskaoyan.shiro;

import com.cskaoyan.bean.user.User;
import com.cskaoyan.mapper.UserMapper;
import com.cskaoyan.utils.AuthUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WxRealm extends AuthorizingRealm {

    @Autowired
    UserMapper userMapper;
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        User user = userMapper.getUserByUsername(username);
        String passwordFromDb = null;
        try {
            passwordFromDb =user.getPassword();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, passwordFromDb, getName());
        return authenticationInfo;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User primaryPrincipal = (User) principalCollection.getPrimaryPrincipal();
        String username = primaryPrincipal.getUsername();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<String> permissions = new ArrayList<>();
        permissions.add("*");
//        authorizationInfo.addStringPermission("user:query");
        authorizationInfo.addStringPermissions(permissions);
        return authorizationInfo;
    }
}

/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/19
 * Time: 17:24
 */
package com.cskaoyan.shiro;

import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

@Data
public class AuthToken extends UsernamePasswordToken {
    String type;

    public AuthToken(String username, String password, String type) {
        super(username, password);
        this.type = type;
    }
}

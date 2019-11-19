/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/18
 * Time: 20:10
 */
package com.cskaoyan.wx_controller;

import com.cskaoyan.bean.BaseReqVo;
import com.cskaoyan.bean.user.User;
import com.cskaoyan.service.UserService;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("wx/auth")
public class AuthController_wx {

    @Autowired
    UserService userService;

    @RequestMapping("login")
    public BaseReqVo login(@RequestBody User user) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        String username = user.getUsername();
        User user1 = userService.getUserByUsername(username);
        if (user1 == null) {
            baseReqVo.setErrno(402);
            baseReqVo.setErrmsg("用户不存在,请确认后重新输入");
            return baseReqVo;
        }
        else if (user1.getPassword().equals(user1.getPassword())) {
            HashMap<String, String> map = new HashMap<>();
            map.put("userInfo",username);
            map.put("token",username);
            baseReqVo.setErrno(0);
            baseReqVo.setData(map);
            baseReqVo.setErrmsg("成功");
            return baseReqVo;
        }else {
            baseReqVo.setErrno(605);
            baseReqVo.setErrmsg("账号或密码输入有误,请确认后重新输入");
            return baseReqVo;
        }
    }

    @RequestMapping("logout")
    public BaseReqVo logout() {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrno(0); //501在退出后会返回到登录选择页面
        baseReqVo.setData(null);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**注册
     * @request
     *{
     *     "username": "1312232",
     *     "password": "123456",
     *     "mobile": "13112515393",
     *     "code": "12312",
     *     "wxCode": "the code is a mock one"
     * }
     * @response
     * {"errno":703,"errmsg":"验证码错误"}
     *
     * */
    @RequestMapping("register")
    public BaseReqVo register() {
        return null;
    }
    /**忘记密码
     * @request
     * {
     *     "mobile": "13112515393",
     *     "code": "2131",
     *     "password": "123456"
     * }
     * @response
     * {"errno":703,"errmsg":"验证码错误"}
     *
     * */
    @RequestMapping("reset")
    public BaseReqVo reset() {
        return null;
    }

    /**
     * 发送短信验证码
     * @request
     * {"mobile":"13112515393"}
     * @response
     * {"errno":701,"errmsg":"小程序后台验证码服务不支持"}
     * */
    @RequestMapping("regCaptcha")
    public BaseReqVo regCaptcha() {
        return null;
    }

    /**
     * @request
     * {
     *     "code": "the code is a mock one",
     *     "userInfo": {
     *         "nickName": "jjjj",
     *         "avatarUrl": "http://wx.qlogo.cn/mmopen/vi_32/n7fZGMXt6vClwuhKBE7tvkaiaNlZo4jKplGW2fQxpOXibuBI9smQiaGHcgwGQWsZoXcW0tDXXcMHvnGOXaG4zCBPw/132",
     *         "gender": 1,
     *         "province": "Guangdong",
     *         "city": "Chaozhou"
     *     }
     * }
     *
     * @response
     * {"errno":-1,"errmsg":"错误"}
     * */
    @RequestMapping("login_by_weixin")
    public BaseReqVo login_by_weixin() {
        return null;
    }
}

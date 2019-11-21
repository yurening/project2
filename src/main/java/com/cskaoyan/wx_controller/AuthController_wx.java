/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/18
 * Time: 20:10
 */
package com.cskaoyan.wx_controller;

import com.cskaoyan.bean.BaseReqVo;
import com.cskaoyan.bean.Wx_register;
import com.cskaoyan.bean.user.User;
import com.cskaoyan.service.UserService;
import com.cskaoyan.shiro.AuthToken;
import com.cskaoyan.utils.AuthUtils;
import com.cskaoyan.utils.RandomUtils;
import com.cskaoyan.utils.SmsUtils;
import com.cskaoyan.utils.TransferDateUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("wx/auth")
public class AuthController_wx {

    @Autowired
    UserService userService;

    @Autowired
            @Qualifier("mymap")
    Map<String, Object> mymap;


    /**@request
     * {
     *     "username": "wx",
     *     "password": "admin123"
     * }
     * @Response
     * {
     *     "errno": 0,
     *     "data": {
     *         "userInfo": {
     *             "nickName": "wx",
     *             "avatarUrl": ""
     *         },
         *     "tokenExpire": "2019-11-20T02:57:54.969",
     *         "token": "7zgz6kn8em6vkiwg5nvnj1i44n06t596"
     *     },
     *     "errmsg": "成功"
     * }
     * */
    @RequestMapping("login")
    public BaseReqVo login(@RequestBody User user) throws Exception {
        AuthToken authenticationToken = new AuthToken(user.getUsername(), AuthUtils.encrypt(user.getPassword()),"wx");
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(authenticationToken);
        } catch (AuthenticationException e) {
            return BaseReqVo.fail(508,"账号或密码错误");
            //e.printStackTrace();
        }
        User userLogin = (User) subject.getPrincipal();
        HashMap<String, String> userInfoMap = new HashMap<>();
        userInfoMap.put("nickName",userLogin.getNickname());
        userInfoMap.put("avatarUrl",userLogin.getAvatar());

        Serializable sessionId = subject.getSession().getId();
        LocalDateTime date = LocalDateTime.now();
        date.plusDays(1);
        userService.updateLoginTime(userLogin.getId());

        HashMap<String, Object> map = new HashMap<>();
        map.put("userInfo",userInfoMap);
        map.put("tokenExpire",date);
        map.put("token",sessionId);

        return BaseReqVo.ok(map);
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
    public BaseReqVo register(@RequestBody Wx_register wxRegister) throws Exception {
        String phoneCode = (String) mymap.get("code");
        String code = wxRegister.getCode();
        String username = wxRegister.getUsername();
        String password = wxRegister.getPassword();
        String mobile = wxRegister.getMobile();
        if (!code.equals(phoneCode)) {
            return BaseReqVo.fail(508,"验证码输入有误");
        }else if (userService.getUserByUsername(wxRegister.getUsername()) != null) {
            return BaseReqVo.fail(508,"该账号已存在");
        }else if (userService.getUserByMobile(mobile) != null ) {
            return BaseReqVo.fail(508,"该手机已注册");
        }
        wxRegister.setPassword(AuthUtils.encrypt(password));
        String randomAvatar = RandomUtils.getRandomAvater();
        Boolean state = userService.registerInsertUser(wxRegister,randomAvatar);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return login(user);
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
    public BaseReqVo reset(@RequestBody Wx_register wxRegister) {
        String phoneCode = (String) mymap.get("code");
        String code = wxRegister.getCode();
        String mobile = wxRegister.getMobile();
        if (userService.getUserByMobile(mobile) == null) {
            return BaseReqVo.fail(508,"该手机暂未注册！");
        } else if (!code.equals(phoneCode)) {
            return BaseReqVo.fail(508,"验证码输入有误");
        }
        userService.resetPasswordBymolibe(wxRegister.getPassword(),mobile);
        return BaseReqVo.ok(null);
    }

    /**
     * 发送短信验证码
     * @request
     * {"mobile":"13112515393"}
     * @response
     * {"errno":0,data:"","errmsg":"小程序后台验证码服务不支持"}
     * */
    @RequestMapping("regCaptcha")
    public BaseReqVo regCaptcha(@RequestBody Map<String,String> map,HttpServletRequest request) {
        String s = map.get("mobile");
        String code = SmsUtils.regCaptchaTool(s);
        mymap.put("code",code);
        Session session = SecurityUtils.getSubject().getSession();
        return BaseReqVo.ok(session.getId());
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
        return BaseReqVo.ok("123");
    }

    @RequestMapping("bindPhone")
    public BaseReqVo bindPhone() {
        return BaseReqVo.ok("123");
    }
}

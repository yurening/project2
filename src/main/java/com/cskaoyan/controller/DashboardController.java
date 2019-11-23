/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/15
 * Time: 21:37
 */
package com.cskaoyan.controller;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.bean.Admin_reset;
import com.cskaoyan.bean.BaseReqVo;
import com.cskaoyan.service.AuthService;
import com.cskaoyan.shiro.AuthToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
public class DashboardController {

    @Autowired
    AuthService authService;

    /**
     * @Respones
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"goodsTotal": 275,
     * 		"userTotal": 22,
     * 		"productTotal": 287,
     * 		"orderTotal": 474
     *        },
     * 	"errmsg": "成功"
     * }
     * */
    @RequestMapping("admin/dashboard")
    @RequiresPermissions("admin:category:read")
    @ResponseBody
    public BaseReqVo dashboard() {
//        Subject subject = SecurityUtils.getSubject();
//        Admin admin = (Admin) subject.getPrincipal();
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String, Integer> map = new HashMap<>();
        int goodsTotal = authService.getGoodsTotal();
        int userTotal = authService.getUserTotal();
        int productTotal = authService.getProductTotal();
        int orderTotal = authService.getOrderTotal();
        map.put("goodsTotal",goodsTotal);
        map.put("userTotal",userTotal);
        map.put("productTotal",productTotal);
        map.put("orderTotal",orderTotal);

        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("admin/redirect")
    public String redirect(HttpServletRequest request, HttpServletResponse response) {
//        return BaseReqVo.fail(507,"权限不足,请联系超级管理员");
        return "redirect:http://localhost:9527/login";
    }

//    @RequestMapping("admin/profile/password")
//    public BaseReqVo password(@RequestBody Admin_reset adminReset) {
//        Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
//        if (!admin.getPassword().equals(adminReset.getOldPassword())) {
//            return BaseReqVo.fail(605,"原密码不正确,请重新输入");
//        }else {
//            authService.resetAdminPassword(admin);
//            return BaseReqVo.ok(null);
//        }
//    }
}

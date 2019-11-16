/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/15
 * Time: 21:37
 */
package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseReqVo;
import com.cskaoyan.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("admin/")
public class DashboardController {

    @Autowired
    AdminService adminService;

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
    @RequestMapping("dashboard")
    public BaseReqVo dashboard() {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String, Integer> map = new HashMap<>();
        int goodsTotal = adminService.getGoodsTotal();
        int userTotal = adminService.getUserTotal();
        int productTotal = adminService.getProductTotal();
        int orderTotal = adminService.getOrderTotal();
        map.put("goodsTotal",goodsTotal);
        map.put("userTotal",userTotal);
        map.put("productTotal",productTotal);
        map.put("orderTotal",orderTotal);

        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }
}
/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/15
 * Time: 22:38
 */
package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseReqVo;
import com.cskaoyan.bean.config.SystemBean;
import com.cskaoyan.service.AuthService;
import com.cskaoyan.service.ConfigService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/config/")
public class ConfigController {

    @Autowired
    ConfigService configService;

    /**
     * @Respones
     *{
     * 	"errno": 0,
     * 	"data": {
     * 		"litemall_mall_phone": "11111",
     * 		"litemall_mall_address": "湖北武汉市",
     * 		"litemall_mall_name": "光谷3店",
     * 		"litemall_mall_qq": "010111"
     *        },
     * 	"errmsg": "成功"
     * }
     * */
    @GetMapping("mall")
    @RequiresPermissions(value = {"admin:config:mall:list"}, logical = Logical.OR)
    public BaseReqVo mallGet() {
        SystemBean system = configService.getSystemMall();
        if (system == null){
            return null;
        }
        return returnTool(system);
    }

    /**
     * @Request
     * {
     * 	"litemall_mall_phone": "1111111",
     * 	"litemall_mall_address": "湖北武汉",
     * 	"litemall_mall_name": "光谷2店",
     * 	"litemall_mall_qq": "020111"
     * }
     *
     * @Respones
     * {
     *  "errno": 0,
     * 	"errmsg": "成功"
     * }
     */
    @PostMapping("mall")
    @RequiresPermissions(value = {"admin:config:mall:updateConfigs"}, logical = Logical.OR)
    public BaseReqVo mallPost(@RequestBody SystemBean systemBean) {
        int state = configService.updateSystemMall(systemBean);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        switch (state) {
            case 1: baseReqVo = returnTool(null);break;
            case 2: baseReqVo.setErrno(507);baseReqVo.setErrmsg("请填写商场名称,谢谢");break;
            case 3: baseReqVo.setErrno(507);baseReqVo.setErrmsg("请填写商场地址,谢谢");break;
            case 4: baseReqVo.setErrno(507);baseReqVo.setErrmsg("请填写联系电话,谢谢");break;
            case 5: baseReqVo.setErrno(507);baseReqVo.setErrmsg("请填写联系QQ,谢谢");break;
            case -1: baseReqVo.setErrno(505);break;
        }
        return baseReqVo;
    }

    /**
     * @Respones
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"litemall_express_freight_min": "25",
     * 		"litemall_express_freight_value": "5"
     *        },
     * 	"errmsg": "成功"
     * }
     * */
    @GetMapping("express")
    @RequiresPermissions(value = {"admin:config:express:list"}, logical = Logical.OR)
    public BaseReqVo expressGet() {
        SystemBean system = configService.getSystemExpress();
        if (system == null){
            return null;
        }
        return returnTool(system);
    }

     /**
     * @request
     * {
     * 	"litemall_express_freight_min": "20",
     * 	"litemall_express_freight_value": "5"
     * }
     *
     * @Respones
     * {
     * 	"errno": 0,
     * 	"errmsg": "成功"
     * }
     * */
    @PostMapping("express")
    @RequiresPermissions(value = {"admin:config:express:updateConfigs"}, logical = Logical.OR)
    public BaseReqVo expressPost(@RequestBody SystemBean systemBean) {
        int state = configService.updateSystemExpress(systemBean);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        switch (state) {
            case 1: baseReqVo = returnTool(null);break;
            case 2: baseReqVo.setErrno(508);baseReqVo.setErrmsg("金额只可为数字,谢谢");break;
            case 3: baseReqVo.setErrno(508);baseReqVo.setErrmsg("金额不可为负,谢谢");break;
            case -1: baseReqVo.setErrno(505);break;
        }
        return baseReqVo;
    }

    /**
     * @Respones
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"litemall_order_comment": "6",
     * 		"litemall_order_unpaid": "450",
     * 		"litemall_order_unconfirm": "6"
     *        },
     * 	"errmsg": "成功"
     * }
     * */
    @GetMapping("order")
    @RequiresPermissions(value = {"admin:config:order:list"}, logical = Logical.OR)
    public BaseReqVo orderGet() {
        SystemBean system = configService.getSystemOrder();
        if (system == null){
            return null;
        }
        return returnTool(system);
    }

    /**
     * @request
     * {
     * 	"litemall_order_comment": "6",
     * 	"litemall_order_unpaid": "450",
     * 	"litemall_order_unconfirm": "6"
     * }
     *
     * @Respones
     * {
     * 	"errno": 0,
     * 	"errmsg": "成功"
     * }
     * */
    @PostMapping("order")
    @RequiresPermissions(value = {"admin:config:order:updateConfigs"}, logical = Logical.OR)
    public BaseReqVo orderPost(@RequestBody SystemBean systemBean) {
        int state = configService.updateSystemOrder(systemBean);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        switch (state) {
            case 1: baseReqVo = returnTool(null);break;
            case 2: baseReqVo.setErrno(509);baseReqVo.setErrmsg("请填写订单自动取消时间,谢谢");break;
            case 3: baseReqVo.setErrno(509);baseReqVo.setErrmsg("请填写自动确认收货时间,谢谢");break;
            case 4: baseReqVo.setErrno(509);baseReqVo.setErrmsg("请填写取消评价资格时间,谢谢");break;
            case 5: baseReqVo.setErrno(509);baseReqVo.setErrmsg("天数只可为数字,谢谢");break;
            case 6: baseReqVo.setErrno(509);baseReqVo.setErrmsg("分钟数只可为数字,谢谢");break;
            case 7: baseReqVo.setErrno(509);baseReqVo.setErrmsg("天数不可为负,谢谢");break;
            case 8: baseReqVo.setErrno(509);baseReqVo.setErrmsg("分钟数不可为负,谢谢");break;
            case -1: baseReqVo.setErrno(505);break;
        }
        return baseReqVo;
    }

    /**
     * @Respones
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"litemall_wx_index_new": "10",
     * 		"litemall_wx_catlog_goods": "4",
     * 		"litemall_wx_catlog_list": "4",
     * 		"litemall_wx_share": "false",
     * 		"litemall_wx_index_brand": "4",
     * 		"litemall_wx_index_hot": "4",
     * 		"litemall_wx_index_topic": "4"
     *        },
     * 	"errmsg": "成功"
     * }
     * */
    @GetMapping("wx")
    @RequiresPermissions(value = {"admin:config:wx:list"}, logical = Logical.OR)
    public BaseReqVo wxGet() {
        SystemBean system = configService.getSystemWx();
        if (system == null){
            return null;
        }
        return returnTool(system);
    }

    /**
     * @request
     * {
     * 	"litemall_wx_index_new": "10",
     * 	"litemall_wx_catlog_goods": "4",
     * 	"litemall_wx_catlog_list": "4",
     * 	"litemall_wx_share": "false",
     * 	"litemall_wx_index_brand": "4",
     * 	"litemall_wx_index_hot": "4",
     * 	"litemall_wx_index_topic": "4"
     * }
     *
     * @Respones
     * {
     * 	"errno": 0,
     * 	"errmsg": "成功"
     * }
     * */
    @PostMapping("wx")
    @RequiresPermissions(value = {"admin:config:wx:updateConfigs"}, logical = Logical.OR)
    public BaseReqVo wxPost(@RequestBody SystemBean systemBean) {
        int state = configService.updateSystemWx(systemBean);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        switch (state) {
            case 1: baseReqVo = returnTool(null);break;
            case 2: baseReqVo.setErrno(509);baseReqVo.setErrmsg("请输入新品首发栏目商品显示数量,谢谢");break;
            case 3: baseReqVo.setErrno(509);baseReqVo.setErrmsg("请输入人气推荐栏目商品显示数量,谢谢");break;
            case 4: baseReqVo.setErrno(509);baseReqVo.setErrmsg("请输入品牌制造商直供栏目品牌商显示数量,谢谢");break;
            case 5: baseReqVo.setErrno(509);baseReqVo.setErrmsg("请输入专题精选栏目显示数量,谢谢");break;
            case 6: baseReqVo.setErrno(509);baseReqVo.setErrmsg("请输入分类栏目显示数量,谢谢");break;
            case 7: baseReqVo.setErrno(509);baseReqVo.setErrmsg("请输入分类栏目商品显示数量,谢谢");break;
            case 8: baseReqVo.setErrno(509);baseReqVo.setErrmsg("请输入新品首发栏目商品显示数量,谢谢");break;
            case 9: baseReqVo.setErrno(509);baseReqVo.setErrmsg("数量只可为整数,谢谢");break;
            case 10: baseReqVo.setErrno(509);baseReqVo.setErrmsg("数量不可为负,谢谢");break;
            case -1: baseReqVo.setErrno(505);break;
        }
        return baseReqVo;
    }

    private BaseReqVo returnTool(SystemBean system) {
        BaseReqVo<SystemBean> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrno(0);
        baseReqVo.setData(system);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }
}

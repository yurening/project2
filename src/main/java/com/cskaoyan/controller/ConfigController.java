/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/15
 * Time: 22:38
 */
package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseReqVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/config/")
public class ConfigController {


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
    @RequestMapping(value = "mall",method = RequestMethod.GET)
    public BaseReqVo mallGet() {
        return null;
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
    @RequestMapping(value = "mall",method = RequestMethod.POST)
    public BaseReqVo mallPost() {
        return null;
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
    @RequestMapping(value = "express",method = RequestMethod.GET)
    public BaseReqVo expressGet() {
        return null;
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
    @RequestMapping(value = "mall",method = RequestMethod.POST)
    public BaseReqVo expressPost() {
        return null;
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
    @RequestMapping(value = "order",method = RequestMethod.GET)
    public BaseReqVo orderGet() {
        return null;
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
    @RequestMapping(value = "mall",method = RequestMethod.POST)
    public BaseReqVo orderPost() {
        return null;
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
    @RequestMapping(value = "wx",method = RequestMethod.GET)
    public BaseReqVo wxGet() {
        return null;
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
    @RequestMapping(value = "wx",method = RequestMethod.POST)
    public BaseReqVo wxPost() {
        return null;
    }
}

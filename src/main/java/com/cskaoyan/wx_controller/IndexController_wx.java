package com.cskaoyan.wx_controller;

import com.cskaoyan.bean.BaseReqVo;
import com.cskaoyan.bean.wx_index.IndexBean;
import com.cskaoyan.service.GeneralizeService;
import com.cskaoyan.service.GoodsService;
import com.cskaoyan.service.MallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController_wx {
    @Autowired
    GoodsService goodsService;
    @Autowired
    GeneralizeService generalizeService;
    @Autowired
    MallService mallService;

    @RequestMapping("wx/home/index")
    public BaseReqVo index() {
        IndexBean indexBean = new IndexBean();
        indexBean.setNewGoodsList(goodsService.getNewGoodsList());
        indexBean.setHotGoodsList(goodsService.getHotGoodsList());
        indexBean.setCouponList(generalizeService.getCouponList());
        indexBean.setChannel(goodsService.getChannel());
        indexBean.setGrouponList(generalizeService.getGrouponList());
        indexBean.setBanner(generalizeService.getBanner());
        indexBean.setBrandList(mallService.getBrandList());
        indexBean.setTopicList(generalizeService.getTopicList());
        indexBean.setFloorGoodsList(goodsService.getFloorGoodsList());
        BaseReqVo<IndexBean> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(indexBean);
        return baseReqVo;
    }
}

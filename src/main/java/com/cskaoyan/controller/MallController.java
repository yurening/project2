package com.cskaoyan.controller;

import java.util.ArrayList;
import java.util.List;


import com.cskaoyan.bean.mall.BaseReqVo;
import com.cskaoyan.bean.mall.InfoData;
import com.cskaoyan.bean.mall.Login;
import com.cskaoyan.bean.mall.region.MallRegionI;
import com.cskaoyan.service.MallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MallController {
    @Autowired
    MallService mallService;

    @RequestMapping("admin/region/list")
    public BaseReqVo getRegionList(){
        List<MallRegionI> allRegions= mallService.getAllRegion();
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setData(allRegions);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

}

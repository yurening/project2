package com.cskaoyan.wx_controller;

import com.cskaoyan.bean.BaseReqVo;
import com.cskaoyan.bean.mall.category.MallCatagoryL1Info;
import com.cskaoyan.bean.mall.category.MallCategory;
import com.cskaoyan.bean.wx_index.CatalogIndex;
import com.cskaoyan.bean.wx_index.HomeIndex;
import com.cskaoyan.service.GeneralizeService;
import com.cskaoyan.service.GoodsService;
import com.cskaoyan.service.MallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("wx")
public class IndexController_wx {
    @Autowired
    GoodsService goodsService;
    @Autowired
    GeneralizeService generalizeService;
    @Autowired
    MallService mallService;

    @RequestMapping("home/index")
    public BaseReqVo homeIndex() {
        HomeIndex homeIndex = new HomeIndex();
        homeIndex.setNewGoodsList(goodsService.getNewGoodsList());
        homeIndex.setHotGoodsList(goodsService.getHotGoodsList());
        homeIndex.setCouponList(generalizeService.getCouponList());
        homeIndex.setChannel(goodsService.getChannel());
        homeIndex.setGrouponList(generalizeService.getGrouponList());
        homeIndex.setBanner(generalizeService.getBanner());
        homeIndex.setBrandList(mallService.getBrandList());
        homeIndex.setTopicList(generalizeService.getTopicList());
        homeIndex.setFloorGoodsList(goodsService.getFloorGoodsList());
        BaseReqVo<HomeIndex> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(homeIndex);
        return baseReqVo;
    }

    @RequestMapping("catalog/index")
    public BaseReqVo catalogIndex() {
        BaseReqVo<CatalogIndex> baseReqVo = new BaseReqVo<>();
        CatalogIndex catalogIndex = new CatalogIndex();
        List<MallCategory> allCategory = mallService.getAllCategory();
        if (allCategory == null) {
            baseReqVo.setErrno(507);
            baseReqVo.setErrmsg("暂无分类");
            return baseReqVo;
        }
        catalogIndex.setCurrentCategory(allCategory.get(0));
        catalogIndex.setCurrentSubCategory(allCategory.get(0).getChildren());
        for (MallCategory category : allCategory) {
            category.setChildren(null);
        }
        catalogIndex.setCategoryList(allCategory);
        baseReqVo.setData(catalogIndex);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("catalog/current")
    public BaseReqVo current(Integer id) {
        BaseReqVo<CatalogIndex> baseReqVo = new BaseReqVo<>();
        MallCategory category = mallService.getCategoryById(id);
        if (category == null) {
            baseReqVo.setErrno(507);
            baseReqVo.setErrmsg("当前分类错误");
            return baseReqVo;
        }
        CatalogIndex catalogIndex = new CatalogIndex();
        catalogIndex.setCurrentCategory(category);
        catalogIndex.setCurrentSubCategory(category.getChildren());
        category.setChildren(null);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(catalogIndex);
        return baseReqVo;
    }
}

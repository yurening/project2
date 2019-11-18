package com.cskaoyan.controller;

import java.util.List;

import com.cskaoyan.bean.mall.BaseListInfo;
import com.cskaoyan.bean.mall.BaseReqVo;
import com.cskaoyan.bean.mall.brand.MallBrand;
import com.cskaoyan.bean.mall.category.MallCatagoryL1Info;
import com.cskaoyan.bean.mall.category.MallCategory;
import com.cskaoyan.bean.mall.issue.MallIssue;
import com.cskaoyan.bean.mall.keyword.MallKeyword;
import com.cskaoyan.bean.mall.order.MallOrder;
import com.cskaoyan.bean.mall.order.MallOrderDetails;
import com.cskaoyan.bean.mall.region.MallRegion;
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
        List<MallRegion> allRegions= mallService.getAllRegion();
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setData(allRegions);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/brand/list")
    public BaseReqVo getBrandList(Integer page,Integer limit,String sort,String order,String name,Integer id){
        BaseReqVo baseReqVo = new BaseReqVo();
        BaseListInfo<MallBrand> allBrandsInfo=mallService.getAllBrandByplso(page,limit,sort,order,name,id);
        baseReqVo.setData(allBrandsInfo);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }


    @RequestMapping("admin/brand/create")
    public BaseReqVo creatBrand(@RequestBody MallBrand mallBrand){
        BaseReqVo baseReqVo = new BaseReqVo();
        MallBrand newBrand= mallService.creatBrand(mallBrand);
        baseReqVo.setData(newBrand);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/brand/update")
    public BaseReqVo updateBrand(@RequestBody MallBrand mallBrand){
        BaseReqVo baseReqVo = new BaseReqVo();
        mallService.updateBrandByPrimaryKey(mallBrand);
        baseReqVo.setData(mallBrand);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/brand/delete")
    public BaseReqVo deleteBrand(@RequestBody MallBrand mallBrand){
        BaseReqVo baseReqVo = new BaseReqVo();
        mallService.deleteBrand(mallBrand);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/order/list")
    public BaseReqVo getOrderList(Integer page,Integer limit,String sort,String order,String orderSn,Integer userId,Short[] orderStatusArray){
        BaseReqVo baseReqVo = new BaseReqVo();
        BaseListInfo<MallOrder> baseListInfo = mallService.checkOrderList(page, limit, sort, order, userId, orderSn, orderStatusArray);
        baseReqVo.setData(baseListInfo);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/order/detail")
    public BaseReqVo getOrderDetail(Integer id){
        MallOrderDetails mallOrderDetails = mallService.checkOrderDetails(id);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setData(mallOrderDetails);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/category/list")
    public BaseReqVo getCategoryList(){
        List<MallCategory> allCategory = mallService.getAllCategory();
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setData(allCategory);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/category/l1")
    public BaseReqVo getL1CategoryList(){
        List<MallCatagoryL1Info> l1Category = mallService.getL1Category();
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setData(l1Category);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/category/create")
    public BaseReqVo createCategory(@RequestBody MallCategory mallCategory){
        MallCategory newCategory = mallService.insertCategory(mallCategory);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setData(newCategory);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/category/delete")
    public BaseReqVo deleteCategory(@RequestBody MallCategory mallCategory){
        mallService.deleteCategory(mallCategory);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/category/update")
    public BaseReqVo updateCategory(@RequestBody MallCategory mallCategory){
        mallService.updateCategory(mallCategory);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/issue/list")
    public BaseReqVo getIssueList(Integer page,Integer limit,String sort,String order,String question){
        BaseReqVo baseReqVo = new BaseReqVo();
        BaseListInfo<MallIssue> baseListInfo = mallService.checkIssueList(page, limit, sort, order, question);
        baseReqVo.setData(baseListInfo);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }


    @RequestMapping("admin/issue/create")
    public BaseReqVo createIssue(@RequestBody MallIssue mallIssue){
        MallIssue newIssue = mallService.insertIssue(mallIssue);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setData(newIssue);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/issue/delete")
    public BaseReqVo deleteIssue(@RequestBody MallIssue mallIssue){
        mallService.deleteIssue(mallIssue);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/issue/update")
    public BaseReqVo updateIssue(@RequestBody MallIssue mallIssue){
        MallIssue newIssue = mallService.updateIssue(mallIssue);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setData(newIssue);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/keyword/list")
    public BaseReqVo getKeywordList(Integer page,Integer limit,String sort,String order,String keyword,String url){
        BaseReqVo baseReqVo = new BaseReqVo();
        BaseListInfo<MallKeyword> baseListInfo = mallService.checkKeywordList(page, limit, sort, order, keyword,url);
        baseReqVo.setData(baseListInfo);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/keyword/create")
    public BaseReqVo createKeyword(@RequestBody MallKeyword mallKeyword){
        MallKeyword newKeyword = mallService.insertKeyword(mallKeyword);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setData(newKeyword);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/keyword/delete")
    public BaseReqVo deleteKeyword(@RequestBody MallKeyword mallKeyword){
        mallService.deleteKeyword(mallKeyword);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/keyword/update")
    public BaseReqVo updateKeyword(@RequestBody MallKeyword mallKeyword){
        MallKeyword newKeyword = mallService.updateKeyword(mallKeyword);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setData(newKeyword);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
}

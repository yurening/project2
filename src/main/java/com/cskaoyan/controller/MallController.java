package com.cskaoyan.controller;

import java.util.List;

import com.cskaoyan.bean.mall.BaseListInfo;
import com.cskaoyan.bean.mall.BaseRespVo;
import com.cskaoyan.bean.mall.brand.MallBrand;
import com.cskaoyan.bean.mall.category.MallCatagoryL1Info;
import com.cskaoyan.bean.mall.category.MallCategory;
import com.cskaoyan.bean.mall.issue.MallIssue;
import com.cskaoyan.bean.mall.keyword.MallKeyword;
import com.cskaoyan.bean.mall.order.MallOrder;
import com.cskaoyan.bean.mall.order.MallOrderDetails;
import com.cskaoyan.bean.mall.region.MallRegion;
import com.cskaoyan.service.MallService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MallController {
    @Autowired
    MallService mallService;


    @RequestMapping("admin/region/list")
    public BaseRespVo getRegionList(){
        List<MallRegion> allRegions= mallService.getAllRegion();
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setData(allRegions);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        return baseRespVo;
    }

    @RequiresPermissions("admin:brand:list")
    @RequestMapping("admin/brand/list")
    public BaseRespVo getBrandList(Integer page, Integer limit, String sort, String order, String name, Integer id){
        BaseRespVo baseRespVo = new BaseRespVo();
        BaseListInfo<MallBrand> allBrandsInfo=mallService.getAllBrandByplso(page,limit,sort,order,name,id);
        baseRespVo.setData(allBrandsInfo);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        return baseRespVo;
    }


    @RequiresPermissions("admin:brand:update")
    @RequestMapping("admin/brand/create")
    public BaseRespVo creatBrand(@RequestBody MallBrand mallBrand){
        BaseRespVo baseRespVo = new BaseRespVo();
        MallBrand newBrand= mallService.creatBrand(mallBrand);
        baseRespVo.setData(newBrand);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        return baseRespVo;
    }

    @RequiresPermissions("admin:brand:update")
    @RequestMapping("admin/brand/update")
    public BaseRespVo updateBrand(@RequestBody MallBrand mallBrand){
        BaseRespVo baseRespVo = new BaseRespVo();
        mallService.updateBrandByPrimaryKey(mallBrand);
        baseRespVo.setData(mallBrand);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        return baseRespVo;
    }

    @RequiresPermissions("admin:brand:delete")
    @RequestMapping("admin/brand/delete")
    public BaseRespVo deleteBrand(@RequestBody MallBrand mallBrand){
        BaseRespVo baseRespVo = new BaseRespVo();
        mallService.deleteBrand(mallBrand);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        return baseRespVo;
    }

    @RequiresPermissions("admin:order:list")
    @RequestMapping("admin/order/list")
    public BaseRespVo getOrderList(Integer page, Integer limit, String sort, String order, String orderSn, Integer userId, Short[] orderStatusArray){
        BaseRespVo baseRespVo = new BaseRespVo();
        BaseListInfo<MallOrder> baseListInfo = mallService.checkOrderList(page, limit, sort, order, userId, orderSn, orderStatusArray);
        baseRespVo.setData(baseListInfo);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        return baseRespVo;
    }

    @RequiresPermissions("admin:order:read")
    @RequestMapping("admin/order/detail")
    public BaseRespVo getOrderDetail(Integer id){
        MallOrderDetails mallOrderDetails = mallService.checkOrderDetails(id);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setData(mallOrderDetails);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        return baseRespVo;
    }

    @RequiresPermissions("admin:category:list")
    @RequestMapping("admin/category/list")
    public BaseRespVo getCategoryList(){
        List<MallCategory> allCategory = mallService.getAllCategory();
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setData(allCategory);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        return baseRespVo;
    }



    @RequestMapping("admin/category/l1")
    public BaseRespVo getL1CategoryList(){
        List<MallCatagoryL1Info> l1Category = mallService.getL1Category();
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setData(l1Category);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        return baseRespVo;
    }

    @RequiresPermissions("admin:category:create")
    @RequestMapping("admin/category/create")
    public BaseRespVo createCategory(@RequestBody MallCategory mallCategory){
        BaseRespVo baseRespVo = new BaseRespVo();
        if(mallCategory.getLevel().equals("L2")&&mallCategory.getPid()==0){
            baseRespVo.setData(null);
            baseRespVo.setErrmsg("二级目录必须要有一级目录");
            baseRespVo.setErrno(507);
        }else{
            MallCategory newCategory = mallService.insertCategory(mallCategory);
            baseRespVo.setData(newCategory);
            baseRespVo.setErrmsg("成功");
            baseRespVo.setErrno(0);
        }
        return baseRespVo;
    }

    @RequiresPermissions("admin:category:delete")
    @RequestMapping("admin/category/delete")
    public BaseRespVo deleteCategory(@RequestBody MallCategory mallCategory){
        mallService.deleteCategory(mallCategory);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        return baseRespVo;
    }

    @RequiresPermissions("admin:category:update")
    @RequestMapping("admin/category/update")
    public BaseRespVo updateCategory(@RequestBody MallCategory mallCategory){
        BaseRespVo baseRespVo = new BaseRespVo();
        if(mallCategory.getLevel().equals("L2")&&mallCategory.getPid()==0){
            baseRespVo.setData(null);
            baseRespVo.setErrmsg("二级目录必须要有一级目录");
            baseRespVo.setErrno(507);
        }else {
            mallService.updateCategory(mallCategory);
            baseRespVo.setErrmsg("成功");
            baseRespVo.setErrno(0);
        }
        return baseRespVo;
    }

    @RequiresPermissions("admin:issue:list")
    @RequestMapping("admin/issue/list")
    public BaseRespVo getIssueList(Integer page, Integer limit, String sort, String order, String question){
        BaseRespVo baseRespVo = new BaseRespVo();
        BaseListInfo<MallIssue> baseListInfo = mallService.checkIssueList(page, limit, sort, order, question);
        baseRespVo.setData(baseListInfo);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        return baseRespVo;
    }


    @RequiresPermissions("admin:issue:create")
    @RequestMapping("admin/issue/create")
    public BaseRespVo createIssue(@RequestBody MallIssue mallIssue){
        MallIssue newIssue = mallService.insertIssue(mallIssue);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setData(newIssue);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        return baseRespVo;
    }

    @RequiresPermissions("admin:issue:delete")
    @RequestMapping("admin/issue/delete")
    public BaseRespVo deleteIssue(@RequestBody MallIssue mallIssue){
        mallService.deleteIssue(mallIssue);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        return baseRespVo;
    }

    @RequiresPermissions("admin:issue:update")
    @RequestMapping("admin/issue/update")
    public BaseRespVo updateIssue(@RequestBody MallIssue mallIssue){
        MallIssue newIssue = mallService.updateIssue(mallIssue);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setData(newIssue);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        return baseRespVo;
    }

    @RequiresPermissions("admin:keyword:list")
    @RequestMapping("admin/keyword/list")
    public BaseRespVo getKeywordList(Integer page, Integer limit, String sort, String order, String keyword, String url){
        BaseRespVo baseRespVo = new BaseRespVo();
        BaseListInfo<MallKeyword> baseListInfo = mallService.checkKeywordList(page, limit, sort, order, keyword,url);
        baseRespVo.setData(baseListInfo);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        return baseRespVo;
    }

    @RequiresPermissions("admin:keyword:create")
    @RequestMapping("admin/keyword/create")
    public BaseRespVo createKeyword(@RequestBody MallKeyword mallKeyword){
        MallKeyword newKeyword = mallService.insertKeyword(mallKeyword);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setData(newKeyword);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        return baseRespVo;
    }

    @RequiresPermissions("admin:keyword:delete")
    @RequestMapping("admin/keyword/delete")
    public BaseRespVo deleteKeyword(@RequestBody MallKeyword mallKeyword){
        mallService.deleteKeyword(mallKeyword);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        return baseRespVo;
    }

    @RequiresPermissions("admin:keyword:update")
    @RequestMapping("admin/keyword/update")
    public BaseRespVo updateKeyword(@RequestBody MallKeyword mallKeyword){
        MallKeyword newKeyword = mallService.updateKeyword(mallKeyword);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setData(newKeyword);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        return baseRespVo;
    }
}

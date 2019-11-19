package com.cskaoyan.service;

import com.cskaoyan.bean.mall.BaseListInfo;
import com.cskaoyan.bean.mall.brand.MallBrand;
import com.cskaoyan.bean.mall.category.MallCatagoryL1Info;
import com.cskaoyan.bean.mall.category.MallCategory;
import com.cskaoyan.bean.mall.issue.MallIssue;
import com.cskaoyan.bean.mall.keyword.MallKeyword;
import com.cskaoyan.bean.mall.order.MallOrder;
import com.cskaoyan.bean.mall.order.MallOrderDetails;
import com.cskaoyan.bean.mall.region.MallRegion;
import com.cskaoyan.bean.wx_index.CatalogIndex;
import com.cskaoyan.bean.wx_index.HomeIndex;

import java.util.List;

public interface MallService {
    List<MallRegion> getAllRegion();

    BaseListInfo<MallBrand> getAllBrandByplso(Integer page, Integer limit, String sort, String order,String name,Integer id);

    MallBrand creatBrand(MallBrand mallBrand);

    void deleteBrand(MallBrand mallBrand);

    void updateBrandByPrimaryKey(MallBrand mallBrand);

    BaseListInfo<MallOrder> checkOrderList(Integer page, Integer limit, String sort, String order, Integer userId, String orderSn, Short[] orderStatusArray);

    MallOrderDetails checkOrderDetails(Integer id);

    List<MallCategory> getAllCategory();

    List<MallCatagoryL1Info> getL1Category();

    MallCategory insertCategory(MallCategory mallCategory);

    void deleteCategory(MallCategory mallCategory);

    void updateCategory(MallCategory mallCategory);

    BaseListInfo<MallIssue> checkIssueList(Integer page, Integer limit, String sort, String order, String question);

    MallIssue insertIssue(MallIssue mallIssue);

    void deleteIssue(MallIssue mallIssue);

    MallIssue updateIssue(MallIssue mallIssue);

    BaseListInfo<MallKeyword> checkKeywordList(Integer page, Integer limit, String sort, String order, String keyword, String url);

    MallKeyword insertKeyword(MallKeyword mallKeyword);

    void deleteKeyword(MallKeyword mallKeyword);

    MallKeyword updateKeyword(MallKeyword mallKeyword);

    List<HomeIndex.BrandListBean> getBrandList();

    MallCategory getCategoryById(Integer id);
}

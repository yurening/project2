package com.cskaoyan.service;

import com.cskaoyan.bean.mall.brand.AllBrandsInfo;
import com.cskaoyan.bean.mall.brand.CreatBrand;
import com.cskaoyan.bean.mall.brand.MallBrand;
import com.cskaoyan.bean.mall.region.MallRegionI;

import java.util.List;

public interface MallService {
    List<MallRegionI> getAllRegion();

    AllBrandsInfo getAllBrandByplso(Integer page, Integer limit, String sort, String order,String name,Integer id);

    MallBrand creatBrand(CreatBrand creatBrand);

    void deleteBrand(MallBrand mallBrand);
}

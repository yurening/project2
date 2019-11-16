package com.cskaoyan.bean.mall.brand;

import lombok.Data;

import java.util.List;

@Data
public class AllBrandsInfo {
    List<MallBrand> items;
    Integer total;
}

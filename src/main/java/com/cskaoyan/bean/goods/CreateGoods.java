package com.cskaoyan.bean.goods;

import lombok.Data;

import java.util.List;

@Data
public class CreateGoods {
    private Integer[] categoryIds;
    private Goods goods;
    private List<Specification> specifications;
    private List<Product> products;
    private List<Attribute> attributes;
    /*Specification[] specifications;
    Product[] products;
    Attribute[] attributes;*/
}

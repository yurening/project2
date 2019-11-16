package com.cskaoyan.bean.goods;

import lombok.Data;

import java.util.List;

@Data
public class CategoryResp {
    private Integer value ;
    private String label;
    private List<CategoryResp> children;
}

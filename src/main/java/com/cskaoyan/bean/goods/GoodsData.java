package com.cskaoyan.bean.goods;

import lombok.Data;

import java.util.List;

@Data
public class GoodsData {
    private Long total;
    private List<Goods> items;
}

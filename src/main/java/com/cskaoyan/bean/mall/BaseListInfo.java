package com.cskaoyan.bean.mall;

import lombok.Data;

import java.util.List;

@Data
public class BaseListInfo<T> {
    List<T> items;
    Integer total;
}

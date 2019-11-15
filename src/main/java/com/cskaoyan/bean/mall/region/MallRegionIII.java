package com.cskaoyan.bean.mall.region;

import lombok.Data;

import java.util.List;
@Data
public class MallRegionIII {
    Integer id;
    String name;
    final Byte type = 3;
    Integer code;
}

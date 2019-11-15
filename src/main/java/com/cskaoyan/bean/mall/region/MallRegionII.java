package com.cskaoyan.bean.mall.region;

import lombok.Data;

import java.util.List;
@Data
public class MallRegionII {
    Integer id;
    String name;
    final Byte type = 2;
    Integer code;
    List<MallRegionIII> children;

}

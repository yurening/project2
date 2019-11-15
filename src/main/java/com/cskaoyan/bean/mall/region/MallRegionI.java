package com.cskaoyan.bean.mall.region;

import lombok.Data;

import java.util.List;

@Data
public class MallRegionI {
    Integer id;
    String name;
    final Byte type = 1;
    Integer code;
    List<MallRegionII> children;
}

package com.cskaoyan.bean.mall;

import lombok.Data;

@Data
public class BaseReqVo<T> {
    T Data;
    String errmsg;
    Integer errno;
}

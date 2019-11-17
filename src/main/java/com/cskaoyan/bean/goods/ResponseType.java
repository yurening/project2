package com.cskaoyan.bean.goods;

import lombok.Data;

@Data
public class ResponseType<T> {
    private int errno;
    private String errmsg;
    private T data;
}

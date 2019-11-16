package com.cskaoyan.bean.user;

import lombok.Data;

@Data
public class CollectRequest {
    int page;
    int limit;
    String sort;
    String order;
    String userId;
    String valueId;
}

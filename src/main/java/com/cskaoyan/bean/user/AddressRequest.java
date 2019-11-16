package com.cskaoyan.bean.user;

import lombok.Data;

@Data
public class AddressRequest {
    int page;
    int limit;
    String sort;
    String order;
    String name;
    String userId;
}

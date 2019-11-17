package com.cskaoyan.bean.user;

import lombok.Data;

@Data
public class UserRequest {
    int page;
    int limit;
    String sort;
    String order;
    String username;
    String mobile;
}

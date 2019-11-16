package com.cskaoyan.bean.user;

import lombok.Data;

@Data
public class FeedBackRequest {
    int page;
    int limit;
    String sort;
    String order;
    String username;
    String id;
}

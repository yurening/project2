package com.cskaoyan.bean.user;

import lombok.Data;

@Data
public class HistoryRequest {
    int page;
    int limit;
    String sort;
    String order;
    String userId;
    String keyword;
}

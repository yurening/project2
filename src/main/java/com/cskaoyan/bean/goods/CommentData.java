package com.cskaoyan.bean.goods;

import lombok.Data;

import java.util.List;

@Data
public class CommentData {
    private Long total;
    private List<Comment> items;
}

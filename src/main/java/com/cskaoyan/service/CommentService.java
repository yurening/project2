package com.cskaoyan.service;

import com.cskaoyan.bean.goods.Comment;
import com.cskaoyan.bean.goods.ResponseType;

public interface CommentService {
    ResponseType getAllComments(Integer page, Integer limit,
                             String order, String sort,
                             Integer userId, Integer valueId);
    ResponseType deleteByLogic(Comment comment);
    ResponseType getCommentCount(Integer valueId,Integer type);
    ResponseType getCommentsList(Integer valueId,Integer type,Integer size,Integer page,Integer showType);

    void commentOrderGoods(Comment comment);
}

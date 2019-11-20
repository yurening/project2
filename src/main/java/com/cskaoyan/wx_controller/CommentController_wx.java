package com.cskaoyan.wx_controller;

import com.cskaoyan.bean.goods.Comment;
import com.cskaoyan.bean.goods.ResponseType;
import com.cskaoyan.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("wx")
public class CommentController_wx {
    @Autowired
    CommentService commentService;

    @RequestMapping("comment/count")
    public ResponseType commentCount(Integer valueId,Integer type){
        ResponseType commentCount = commentService.getCommentCount(valueId, type);
        return commentCount;
    }

    @RequestMapping("comment/list")
    public ResponseType commentList(Integer valueId,Integer type,Integer size,Integer page,Integer showType){
        ResponseType commentsList = commentService.getCommentsList(valueId, type, size, page, showType);
        return commentsList;
    }

    @RequestMapping("comment/post")
    public ResponseType commentPost(@RequestBody Comment comment){
        ResponseType responseType = commentService.addComment(comment);
        return responseType;
    }
}

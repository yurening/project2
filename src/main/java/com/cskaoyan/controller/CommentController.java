package com.cskaoyan.controller;

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
@RequestMapping("admin")
public class CommentController {
    @Autowired
    CommentService commentService;
    @RequestMapping("comment/list")
    public ResponseType showComments(Integer page,Integer limit,
                                  String order,String sort,
                                  Integer userId,Integer valueId){
        ResponseType allComments = commentService.getAllComments(page, limit, order, sort, userId, valueId);
        return allComments;
    }

    @RequestMapping("comment/delete")
    public ResponseType deleteComments(@RequestBody Comment comment){
        ResponseType responseType = commentService.deleteByLogic(comment);
        return responseType;
    }
}

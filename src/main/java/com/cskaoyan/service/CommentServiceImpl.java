package com.cskaoyan.service;

import com.cskaoyan.bean.goods.*;
import com.cskaoyan.mapper.CommentMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentMapper commentMapper;
    @Override
    public ResponseType getAllComments(Integer page, Integer limit, String order, String sort, Integer userId, Integer valueId) {
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria().andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if (!StringUtils.isEmpty(valueId)){
            criteria.andValueIdEqualTo(valueId);
        }
        //获取条目数
        long commentSize = commentMapper.countByExample(commentExample);
        //分页
        PageHelper.startPage(page,limit);
        String str = sort + " " + order;
        commentExample.setOrderByClause(str);
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        //封装data
        long total = commentSize;
        CommentData commentData = new CommentData();
        commentData.setItems(comments);
        commentData.setTotal(total);
        //封装ResponseType
        ResponseType responseType = new ResponseType();
        responseType.setData(commentData);
        responseType.setErrno(0);
        responseType.setErrmsg("成功");
        return responseType;
    }

    @Override
    public ResponseType deleteByLogic(Comment comment) {
        Integer id = comment.getId();
        Comment comment1 = commentMapper.selectByPrimaryKey(id);
        comment1.setDeleted(true);
        commentMapper.updateByPrimaryKey(comment1);

        ResponseType responseType = new ResponseType();
        responseType.setErrmsg("成功");
        responseType.setErrno(0);
        responseType.setData(null);
        return responseType;
    }


}

package com.cskaoyan.service;

import com.cskaoyan.bean.goods.*;
import com.cskaoyan.bean.mall.order.MallOrderGoods;
import com.cskaoyan.bean.user.User;
import com.cskaoyan.bean.user.UserExample;
import com.cskaoyan.mapper.CommentMapper;
import com.cskaoyan.mapper.MallOrderGoodsMapper;
import com.cskaoyan.mapper.UserMapper;
import com.cskaoyan.utils.TransferDateUtils;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.util.*;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    MallOrderGoodsMapper orderGoodsMapper;

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

    @Override
    public ResponseType getCommentCount(Integer valueId, Integer type) {
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria().andTypeEqualTo(type).andValueIdEqualTo(valueId);
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        int totalSize = comments.size();
        criteria.andHasPictureEqualTo(true);
        long picSize = commentMapper.countByExample(commentExample);
        Map map = new HashMap<>();
        map.put("hasPicCount",picSize);
        map.put("allCount",totalSize);

        ResponseType responseType = new ResponseType();
        responseType.setErrmsg("成功");
        responseType.setErrno(0);
        responseType.setData(map);
        return responseType;
    }

    @Override
    public ResponseType getCommentsList(Integer valueId, Integer type, Integer size, Integer page, Integer showType) {
        PageHelper.startPage(page,size);
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria().andTypeEqualTo(type).andValueIdEqualTo(valueId);
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        List list = new ArrayList();
        for (Comment comment : comments) {
            Integer userId = comment.getUserId();
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(userId);
            List<User> users = userMapper.selectUserByExample(userExample);
            User user = users.get(0);
            Map map = new HashMap();
            map.put("nickName",user.getNickname());
            map.put("avatarUrl",user.getAvatar());
            String[] picUrls = comment.getPicUrls();
            String content = comment.getContent();
            Date date = comment.getAddTime();
            String s = TransferDateUtils.date2String(date);
            Map addList = new HashMap();
            addList.put("userInfo",map);
            addList.put("addTime",s);
            addList.put("picList",picUrls);
            addList.put("content",content);

            list.add(addList);
        }
        Map returnMap = new HashMap();
        returnMap.put("data",list);
        returnMap.put("count",comments.size());
        returnMap.put("currentPage",page);

        ResponseType responseType = new ResponseType();
        responseType.setErrmsg("成功");
        responseType.setErrno(0);
        responseType.setData(returnMap);
        return responseType;
    }

    @Override
    public void commentOrderGoods(Comment comment) {
        MallOrderGoods mallOrderGoods = orderGoodsMapper.selectByPrimaryKey(comment.getOrderGoodsId());
        comment.setValueId(mallOrderGoods.getGoodsId());
        comment.setUserId(getUserID());
        /*comment.setUserId(1);*/
        comment.setType((byte) 3);
        Date addTime = new Date();
        comment.setAddTime(addTime);
        comment.setUpdateTime(addTime);
        commentMapper.insert(comment);
        Integer lastInsert = commentMapper.lastInser();

        mallOrderGoods.setUpdateTime(addTime);
        mallOrderGoods.setComment(lastInsert);
        orderGoodsMapper.updateByPrimaryKey(mallOrderGoods);
    }

    private Integer getUserID(){
        User principal =(User) SecurityUtils.getSubject().getPrincipal();
        return principal.getId();
    }

    public ResponseType addComment(Comment comment) {
        //先給定死
        comment.setUserId(1);
        //添加時間
        comment.setAddTime(new Date());
        int insert = commentMapper.insert(comment);
        //已經獲得最後插入的那個id
        Integer userId = comment.getUserId();
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(userId);
        List<User> users = userMapper.selectUserByExample(userExample);
        User user = users.get(0);
        String username = user.getUsername();
        ResponseType responseType = new ResponseType();
        responseType.setData(comment);
        responseType.setErrmsg("成功");
        responseType.setErrno(0);
        return responseType;
    }

}

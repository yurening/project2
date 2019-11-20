package com.cskaoyan.service;

import com.cskaoyan.bean.goods.Goods;
import com.cskaoyan.bean.goods.GoodsExample;
import com.cskaoyan.bean.goods.GoodsForCollect;
import com.cskaoyan.bean.user.Collect;
import com.cskaoyan.bean.user.CollectExample;
import com.cskaoyan.mapper.CollectMapper;
import com.cskaoyan.mapper.GoodsMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class CollectSerivceImpl implements CollectService{

    @Autowired
    CollectMapper collectMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public HashMap<String, Object> collectList(Integer type, Integer page, Integer size,Integer userId) {
        CollectExample collectExample = new CollectExample();
        CollectExample.Criteria criteria = collectExample.createCriteria();
        criteria.andTypeEqualTo(type.byteValue());
        criteria.andUserIdEqualTo(userId);
        long total = collectMapper.countByExample(collectExample);
        PageHelper.startPage(page,size);
        List<Collect> collects = collectMapper.selectByExample(collectExample);
        int totalPages = (Math.toIntExact(total) / size) + 1;
        /*List<Integer> goodsIds = new ArrayList<>();
        for (Collect collect : collects) {
            goodsIds.add(collect.getValueId());
        }
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria goodsExampleCriteria = goodsExample.createCriteria();
        goodsExampleCriteria.andIdIn(goodsIds);
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        List<GoodsForCollect> goodsForCollectList = new ArrayList<>();
        for (Goods goods : goodsList) {
            GoodsForCollect goodsForCollect = new GoodsForCollect();
            goodsForCollect.setBrief(goods.getBrief());
            goodsForCollect.setName(goods.getName());
            goodsForCollect.setPicUrl(goods.getPicUrl());
            goodsForCollect.setRetailPrice(goods.getRetailPrice().intValue());
            goodsForCollect.setValueId(goods.getId());
            goodsForCollect.setType(0);
        }
        */
        List<GoodsForCollect> goodsForCollectList = collectMapper.selectGoodsForCollect(type, userId);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("collectList",goodsForCollectList);
        hashMap.put("totalPages",totalPages);
        return hashMap;
    }

    @Override
    public HashMap<String, Object> addOrDelete(Collect collect, Integer userId) {
        HashMap<String,Object> hashMap = new HashMap<>();
        CollectExample collectExample = new CollectExample();
        CollectExample.Criteria criteria = collectExample.createCriteria();
        criteria.andValueIdEqualTo(collect.getValueId());
        criteria.andUserIdEqualTo(userId);
        List<Collect> collects = collectMapper.selectByExample(collectExample);
        if (collects.size() == 0){
            collect.setAddTime(new Date());
            collect.setDeleted(false);
            collect.setUpdateTime(new Date());
            collect.setType((byte) 0);
            collect.setUserId(userId);
            collectMapper.insert(collect);
            hashMap.put("type","add");
        } else {
            Collect c = collects.get(0);
            if (c.getType().intValue() == 0){
                c.setType((byte) 1);
                c.setUpdateTime(new Date());
                collectMapper.updateByPrimaryKey(c);
                hashMap.put("type","delete");
            } else {
                c.setType((byte) 0);
                c.setUpdateTime(new Date());
                collectMapper.updateByPrimaryKey(c);
                hashMap.put("type","add");
            }
        }
        return hashMap;
    }
}

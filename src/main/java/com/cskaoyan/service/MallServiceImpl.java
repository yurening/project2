package com.cskaoyan.service;
import java.math.BigDecimal;
import java.util.Date;

import com.cskaoyan.bean.mall.brand.AllBrandsInfo;
import com.cskaoyan.bean.mall.brand.CreatBrand;
import com.cskaoyan.bean.mall.brand.MallBrand;
import com.cskaoyan.bean.mall.brand.MallBrandExample;
import com.cskaoyan.bean.mall.region.*;
import com.cskaoyan.mapper.MallBrandMapper;
import com.cskaoyan.mapper.MallRegionMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MallServiceImpl implements MallService {

    @Autowired
    MallRegionMapper mallRegionMapper;
    @Autowired
    MallBrandMapper mallBrandMapper;

    @Override
    public List<MallRegionI> getAllRegion() {
        MallRegionExample mallRegionExample = new MallRegionExample();
        //先查出所有的一级地区
        //然后找每个一级地区对应的二级地区，
            //再找每个二级地区对应的三级地区
        mallRegionExample.createCriteria().andTypeEqualTo((byte)1);
        List<MallRegion> mallRegions = mallRegionMapper.selectByExample(mallRegionExample);
        List<MallRegionI> regionIS = new ArrayList<>();
        //把数据封装为I级
        for(MallRegion x : mallRegions){
            MallRegionI mallRegionI = new MallRegionI();
            mallRegionI.setId(x.getId());
            mallRegionI.setName(x.getName());
            mallRegionI.setCode(x.getCode());
            //根据id获得二级孩子
            mallRegionI.setChildren(getChildrenII(x.getId()));
            regionIS.add(mallRegionI);
        }
        return regionIS;
    }

    private List<MallRegionII> getChildrenII(Integer id) {
        //查询所有pid=id的地区，返回他们
        MallRegionExample example = new MallRegionExample();
        example.createCriteria().andPidEqualTo(id);
        List<MallRegion> mallRegions = mallRegionMapper.selectByExample(example);
        List<MallRegionII> regionIIS = new ArrayList<>();
        for(MallRegion x:mallRegions){
            MallRegionII mallRegionII = new MallRegionII();
            mallRegionII.setId(x.getId());
            mallRegionII.setCode(x.getCode());
            mallRegionII.setName(x.getName());
            mallRegionII.setChildren(getChildrenIII(x.getId()));
            regionIIS.add(mallRegionII);
        }
        return regionIIS;
    }

    private List<MallRegionIII> getChildrenIII(Integer id) {
        //查询所有pid=id的地区，返回他们
        MallRegionExample example = new MallRegionExample();
        example.createCriteria().andPidEqualTo(id);
        List<MallRegion> mallRegions = mallRegionMapper.selectByExample(example);
        List<MallRegionIII> mallRegionIIIS=new ArrayList<>();
        for(MallRegion x:mallRegions){
            MallRegionIII mallRegionIII = new MallRegionIII();
            mallRegionIII.setId(x.getId());
            mallRegionIII.setName(x.getName());
            mallRegionIII.setCode(x.getCode());
            mallRegionIIIS.add(mallRegionIII);
        }
        return mallRegionIIIS;
    }

    @Override
    public AllBrandsInfo getAllBrandByplso(Integer page, Integer limit, String sort, String order,String name,Integer id) {
        PageHelper.startPage(page,limit);
        String newName = "%"+name+"%";
        MallBrandExample example = new MallBrandExample();
        example.setOrderByClause(sort+" "+order);
        if(id!=null&&name==null){
            example.createCriteria().andIdEqualTo(id);
        }
        if(name!=null&&id==null){
            example.createCriteria().andNameLike(newName);
        }
        if(id==null&&name==null){
            example.createCriteria().andIdIsNotNull();
        }
        if(id!=null&&name!=null){
            example.createCriteria().andIdEqualTo(id).andNameLike(newName);
        }

        List<MallBrand> mallBrands = mallBrandMapper.selectByExample(example);
        PageInfo<MallBrand> pageInfo=new PageInfo<>(mallBrands);
        long total = pageInfo.getTotal();

        AllBrandsInfo allBrandsInfo = new AllBrandsInfo();
        allBrandsInfo.setItems(mallBrands);
        allBrandsInfo.setTotal((int) total);

        return allBrandsInfo;
    }

    @Override
    public MallBrand creatBrand(CreatBrand creatBrand) {
        MallBrandExample example = new MallBrandExample();

        MallBrand mallBrand = new MallBrand();
        mallBrand.setName(creatBrand.getName());
        mallBrand.setDesc(creatBrand.getDesc());
        mallBrand.setPicUrl(creatBrand.getPicUrl());
        mallBrand.setFloorPrice(new BigDecimal(creatBrand.getDesc()));
        mallBrand.setAddTime(new Date());
        mallBrand.setUpdateTime(new Date());
        mallBrand.setDeleted(false);
        mallBrandMapper.insert(mallBrand);

        /*example.createCriteria().andPicUrlEqualTo(creatBrand.getPicUrl()).andNameEqualTo(creatBrand.getName());*/
        example.createCriteria().andIdEqualTo(mallBrandMapper.getlastInsert());
        List<MallBrand> mallBrands = mallBrandMapper.selectByExample(example);
        return mallBrands.get(0);
    }

    @Override
    public void deleteBrand(MallBrand mallBrand) {
        MallBrandExample example = new MallBrandExample();
        example.createCriteria().andIdEqualTo(mallBrand.getId());
        mallBrandMapper.deleteByExample(example);
    }
}

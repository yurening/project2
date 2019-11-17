package com.cskaoyan.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cskaoyan.bean.mall.BaseListInfo;
import com.cskaoyan.bean.mall.brand.AllBrandsInfo;
import com.cskaoyan.bean.mall.brand.CreatBrand;
import com.cskaoyan.bean.mall.brand.MallBrand;
import com.cskaoyan.bean.mall.brand.MallBrandExample;
import com.cskaoyan.bean.mall.category.MallCatagoryL1Info;
import com.cskaoyan.bean.mall.category.MallCategory;
import com.cskaoyan.bean.mall.category.MallCategoryExample;
import com.cskaoyan.bean.mall.issue.MallIssue;
import com.cskaoyan.bean.mall.issue.MallIssueExample;
import com.cskaoyan.bean.mall.keyword.MallKeyword;
import com.cskaoyan.bean.mall.keyword.MallKeywordExample;
import com.cskaoyan.bean.mall.order.*;
import com.cskaoyan.bean.mall.region.*;
import com.cskaoyan.bean.user.User;
import com.cskaoyan.bean.user.UserExample;
import com.cskaoyan.mapper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javafx.scene.input.DataFormat;
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
    @Autowired
    MallOrderMapper mallOrderMapper;
    @Autowired
    MallOrderGoodsMapper mallOrderGoodsMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    MallCategoryMapper mallCategoryMapper;
    @Autowired
    MallIssueMapper mallIssueMapper;
    @Autowired
    MallKeywordMapper mallKeywordMapper;


    @Override
    public List<MallRegionI> getAllRegion() {
        MallRegionExample mallRegionExample = new MallRegionExample();
        //先查出所有的一级地区
        //然后找每个一级地区对应的二级地区，
        //再找每个二级地区对应的三级地区
        mallRegionExample.createCriteria().andTypeEqualTo((byte) 1);
        List<MallRegion> mallRegions = mallRegionMapper.selectByExample(mallRegionExample);
        List<MallRegionI> regionIS = new ArrayList<>();
        //把数据封装为I级
        for (MallRegion x : mallRegions) {
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
        for (MallRegion x : mallRegions) {
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
        List<MallRegionIII> mallRegionIIIS = new ArrayList<>();
        for (MallRegion x : mallRegions) {
            MallRegionIII mallRegionIII = new MallRegionIII();
            mallRegionIII.setId(x.getId());
            mallRegionIII.setName(x.getName());
            mallRegionIII.setCode(x.getCode());
            mallRegionIIIS.add(mallRegionIII);
        }
        return mallRegionIIIS;
    }

    @Override
    public AllBrandsInfo getAllBrandByplso(Integer page, Integer limit, String sort, String order, String name, Integer id) {
        PageHelper.startPage(page, limit);
        String newName = "%" + name + "%";
        MallBrandExample example = new MallBrandExample();
        example.setOrderByClause(sort + " " + order);
        if (id != null && name == null) {
            example.createCriteria().andIdEqualTo(id);
        }
        if (name != null && id == null) {
            example.createCriteria().andNameLike(newName);
        }
        if (id == null && name == null) {
            example.createCriteria().andIdIsNotNull();
        }
        if (id != null && name != null) {
            example.createCriteria().andIdEqualTo(id).andNameLike(newName);
        }

        List<MallBrand> mallBrands = mallBrandMapper.selectByExample(example);
        PageInfo<MallBrand> pageInfo = new PageInfo<>(mallBrands);
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
        mallBrand.setFloorPrice(new BigDecimal(creatBrand.getFloorPrice()));
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

    @Override
    public void updateBrandByPrimaryKey(MallBrand mallBrand) {
        mallBrandMapper.updateByPrimaryKey(mallBrand);
    }

    @Override
    public BaseListInfo<MallOrder> checkOrderList(Integer page, Integer limit, String sort, String order, Integer userId, String orderSn, Short[] orderStatusArray) {
        PageHelper.startPage(page, limit);

        BaseListInfo<MallOrder> baseListInfo = new BaseListInfo<>();
        MallOrderExample example = getOrderExample(userId, orderSn, orderStatusArray);
        example.setOrderByClause(sort + " " + order);

        List<MallOrder> mallOrders = mallOrderMapper.selectByExample(example);
        baseListInfo.setItems(mallOrders);

        PageInfo<MallOrder> pageInfo = new PageInfo<>(mallOrders);
        baseListInfo.setTotal((int) pageInfo.getTotal());

        return baseListInfo;
    }

    private MallOrderExample getOrderExample(Integer userId, String orderSn, Short[] orderStatusArray) {
        MallOrderExample example = new MallOrderExample();

        example.createCriteria();
        List<MallOrderExample.Criteria> oredCriteria = example.getOredCriteria();

        if (orderStatusArray != null) {
            for (Short x : orderStatusArray) {
                System.out.println(x);
                oredCriteria.add(example.createCriteria().andOrderStatusEqualTo(x));
            }
        }
        for (MallOrderExample.Criteria x : oredCriteria) {
            /*System.out.println(x);*/
            if (userId != null) {
                x.andUserIdEqualTo(userId);
            }
            if (orderSn != null && !("".equals(orderSn.trim()))) {
                x.andOrderSnEqualTo(orderSn);
            }
        }


        return example;
    }

    @Override
    public MallOrderDetails checkOrderDetails(Integer id) {
        MallOrderDetails mallOrderDetails = new MallOrderDetails();

        MallOrderExample example = new MallOrderExample();
        example.createCriteria().andIdEqualTo(id);
        List<MallOrder> mallOrders = mallOrderMapper.selectByExample(example);
        MallOrder order = mallOrders.get(0);
        mallOrderDetails.setOrder(order);

        MallOrderGoodsExample mallOrderGoodsExample = new MallOrderGoodsExample();
        mallOrderGoodsExample.createCriteria().andOrderIdEqualTo(id);
        List<MallOrderGoods> mallOrderGoods = mallOrderGoodsMapper.selectByExample(mallOrderGoodsExample);
        mallOrderDetails.setMallOrderGoods(mallOrderGoods.get(0));

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(order.getUserId());
        List<User> users = userMapper.selectUserByExample(userExample);
        mallOrderDetails.setUser(users.get(0));

        return mallOrderDetails;
    }

    @Override
    public List<MallCategory> getAllCategory() {
        MallCategoryExample mallCategoryExample = new MallCategoryExample();
        mallCategoryExample.createCriteria().andLevelEqualTo("L1");
        List<MallCategory> mallCategories = mallCategoryMapper.selectByExample(mallCategoryExample);
        for(MallCategory x:mallCategories){
            MallCategoryExample example2 = new MallCategoryExample();
            example2.createCriteria().andLevelEqualTo("L2").andPidEqualTo(x.getId());
            x.setChildren(mallCategoryMapper.selectByExample(example2));
        }
        return  mallCategories;
    }

    @Override
    public List<MallCatagoryL1Info> getL1Category() {
        List<MallCatagoryL1Info> mallCatagoryL1Infos = new ArrayList<>();

        MallCategoryExample mallCategoryExample = new MallCategoryExample();
        mallCategoryExample.createCriteria().andLevelEqualTo("L1");
        List<MallCategory> mallCategories = mallCategoryMapper.selectByExample(mallCategoryExample);
        for(MallCategory x: mallCategories){
            MallCatagoryL1Info mallCatagoryL1Info = new MallCatagoryL1Info();
            mallCatagoryL1Info.setValue(x.getId());
            mallCatagoryL1Info.setLabel(x.getName());

            mallCatagoryL1Infos.add(mallCatagoryL1Info);
        }
        return mallCatagoryL1Infos;
    }

    @Override
    public MallCategory insertCategory(MallCategory mallCategory) {
        Date time = new Date();
        mallCategory.setAddTime(time);
        mallCategory.setUpdateTime(time);
        mallCategoryMapper.insert(mallCategory);
        return mallCategory;
    }

    @Override
    public void deleteCategory(MallCategory mallCategory) {
        mallCategoryMapper.deleteByPrimaryKey(mallCategory.getId());
    }

    @Override
    public void updateCategory(MallCategory mallCategory) {
        mallCategory.setUpdateTime(new Date());
        mallCategoryMapper.updateByPrimaryKey(mallCategory);
    }

    @Override
    public BaseListInfo<MallIssue> checkIssueList(Integer page, Integer limit, String sort, String order, String question) {
        PageHelper.startPage(page, limit);

        BaseListInfo<MallIssue> baseListInfo = new BaseListInfo<>();
        MallIssueExample example = new MallIssueExample();
        example.setOrderByClause(sort + " " + order);

        if(question!=null&&!question.trim().equals("")){
            String questions = "%"+question+"%";
            example.createCriteria().andQuestionLike(questions);
        }
        List<MallIssue> mallIssues = mallIssueMapper.selectByExample(example);

        baseListInfo.setItems(mallIssues);
        PageInfo<MallIssue> pageInfo = new PageInfo<>(mallIssues);
        baseListInfo.setTotal((int) pageInfo.getTotal());

        return baseListInfo;
    }

    @Override
    public MallIssue insertIssue(MallIssue mallIssue) {
        Date time = new Date();
        mallIssue.setAddTime(time);
        mallIssue.setUpdateTime(time);
        mallIssueMapper.insert(mallIssue);
        mallIssue.setId(mallIssueMapper.lastInsertId());
        return mallIssue;
    }

    @Override
    public void deleteIssue(MallIssue mallIssue) {
        mallIssueMapper.deleteByPrimaryKey(mallIssue.getId());
    }

    @Override
    public MallIssue updateIssue(MallIssue mallIssue) {
        mallIssue.setUpdateTime(new Date());
        mallIssueMapper.updateByPrimaryKey(mallIssue);
        return mallIssue;
    }

    @Override
    public BaseListInfo<MallKeyword> checkKeywordList(Integer page, Integer limit, String sort, String order, String keyword, String url) {
        PageHelper.startPage(page, limit);
        BaseListInfo<MallKeyword> baseListInfo = new BaseListInfo<>();
        MallKeywordExample example = new MallKeywordExample();
        example.setOrderByClause(sort + " " + order);
        MallKeywordExample.Criteria criteria = example.createCriteria();

        if(keyword!=null&&!keyword.trim().equals("")){
            String newkeyword = "%"+keyword+"%";
            criteria.andKeywordLike(newkeyword);
        }
        if(url!=null&&!url.trim().equals("")){
            String newurl = "%"+url+"%";
            criteria.andUrlLike(newurl);
        }

        List<MallKeyword> mallKeywords = mallKeywordMapper.selectByExample(example);
        baseListInfo.setItems(mallKeywords);
        PageInfo<MallKeyword> pageInfo = new PageInfo<>(mallKeywords);
        baseListInfo.setTotal((int) pageInfo.getTotal());

        return baseListInfo;
    }

    @Override
    public MallKeyword insertKeyword(MallKeyword mallKeyword) {
        Date time = new Date();
        mallKeyword.setAddTime(time);
        mallKeyword.setUpdateTime(time);
        mallKeyword.setSortOrder((int) (Math.random()*100));
        mallKeywordMapper.insert(mallKeyword);
        mallKeyword.setId(mallKeywordMapper.lastInsertId());
        return  mallKeyword;
    }

    @Override
    public void deleteKeyword(MallKeyword mallKeyword) {
        mallKeywordMapper.deleteByPrimaryKey(mallKeyword.getId());
    }

    @Override
    public MallKeyword updateKeyword(MallKeyword mallKeyword) {
        mallKeyword.setUpdateTime(new Date());
        mallKeywordMapper.updateByPrimaryKey(mallKeyword);
        return mallKeyword;
    }
}

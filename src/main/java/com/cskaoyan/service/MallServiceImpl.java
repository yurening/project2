package com.cskaoyan.service;

import java.util.Date;

import com.cskaoyan.bean.goods.CategoryExample;
import com.cskaoyan.bean.mall.BaseListInfo;
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
import com.cskaoyan.bean.wx_index.CatalogIndex;
import com.cskaoyan.bean.wx_index.HomeIndex;
import com.cskaoyan.mapper.*;
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
    public List<MallRegion> getAllRegion() {
        MallRegionExample mallRegionExample = new MallRegionExample();
        mallRegionExample.createCriteria().andTypeEqualTo((byte) 1);
        List<MallRegion> mallRegions = mallRegionMapper.selectByExample(mallRegionExample);
        for (MallRegion x:mallRegions) {
            mallRegionExample.clear();
            mallRegionExample.createCriteria().andTypeEqualTo((byte) 2).andPidEqualTo(x.getId());
            List<MallRegion> children2 = mallRegionMapper.selectByExample(mallRegionExample);
            for(MallRegion y:children2){
                mallRegionExample.clear();
                mallRegionExample.createCriteria().andTypeEqualTo((byte) 3).andPidEqualTo(y.getId());
                List<MallRegion> children3 = mallRegionMapper.selectByExample(mallRegionExample);
                y.setChildren(children3);
            }
            x.setChildren(children2);
        }
        return mallRegions;
    }

    @Override
    public BaseListInfo<MallBrand> getAllBrandByplso(Integer page, Integer limit, String sort, String order, String name, Integer id) {
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

        BaseListInfo<MallBrand> allBrandsInfo = new BaseListInfo<>();
        allBrandsInfo.setItems(mallBrands);
        allBrandsInfo.setTotal((int) total);

        return allBrandsInfo;
    }

    @Override
    public MallBrand creatBrand(MallBrand mallBrand) {
        MallBrandExample example = new MallBrandExample();
        mallBrand.setAddTime(new Date());
        mallBrand.setUpdateTime(new Date());
        mallBrand.setDeleted(false);
        mallBrandMapper.insert(mallBrand);
        mallBrand.setId(mallBrandMapper.getlastInsert());
        return mallBrand;
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

    @Override
    public List<HomeIndex.BrandListBean> getBrandList() {
        MallBrandExample brandExample = new MallBrandExample();
        brandExample.createCriteria().andDeletedEqualTo(false);
        List<HomeIndex.BrandListBean> brandList = new ArrayList<>();
        List<MallBrand> mallBrands = mallBrandMapper.selectByExample(brandExample);
        for (MallBrand mallBrand : mallBrands) {
            HomeIndex.BrandListBean brandListBean = new HomeIndex.BrandListBean();
            brandListBean.setId(mallBrand.getId());
            brandListBean.setName(mallBrand.getName());
            brandListBean.setDesc(mallBrand.getDesc());
            brandListBean.setPicUrl(mallBrand.getPicUrl());
            brandListBean.setFloorPrice(mallBrand.getFloorPrice());
            brandList.add(brandListBean);
        }
        return brandList;
    }

    @Override
    public MallCategory getCategoryById(Integer id) {
        MallCategoryExample categoryExample1 = new MallCategoryExample();
        categoryExample1.createCriteria().andIdEqualTo(id).andDeletedEqualTo(false);
        List<MallCategory> categoryList1 = mallCategoryMapper.selectByExample(categoryExample1);
        if (categoryList1 == null) {
            return null;
        }
        MallCategory category = categoryList1.get(0);
        MallCategoryExample categoryExample2 = new MallCategoryExample();
        categoryExample2.createCriteria().andPidEqualTo(category.getId()).andDeletedEqualTo(false);
        List<MallCategory> categoryList2 = mallCategoryMapper.selectByExample(categoryExample2);
        category.setChildren(categoryList2);
        return category;
    }

    @Override
    public List<MallRegion> regionListByPid(Integer pid) {
        MallRegionExample mallRegionExample = new MallRegionExample();
        MallRegionExample.Criteria criteria = mallRegionExample.createCriteria();
        criteria.andPidEqualTo(pid);
        List<MallRegion> mallRegionList = mallRegionMapper.selectByExample(mallRegionExample);
        return mallRegionList;
    }
}

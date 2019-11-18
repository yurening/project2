package com.cskaoyan.service;

import com.cskaoyan.bean.generalize.Coupon;
import com.cskaoyan.bean.generalize.CouponExample;
import com.cskaoyan.bean.goods.*;
import com.cskaoyan.bean.wx_index.IndexBean;
import com.cskaoyan.mapper.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    BrandMapper brandMapper;
    @Autowired
    AttributeMapper attributeMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    SpecificationMapper specificationMapper;

    @Override
    public ResponseType getAllGoods(Integer page,Integer limit,
                                   String order,String sort,
                                   String goodsSn,String name) {
        //查找
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria().andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(goodsSn)){
            criteria.andGoodsSnEqualTo(goodsSn);
        }
        if (!StringUtils.isEmpty(name)){
            criteria.andNameLike("%"+name+"%");
        }
        //获取条目数
        long goodsSize = goodsMapper.countByExample(goodsExample);
        //分页
        PageHelper.startPage(page,limit);
        String str = sort + " " + order;
        goodsExample.setOrderByClause(str);
        List<Goods> goods = goodsMapper.selectByExampleWithBLOBs(goodsExample);
        //封装data
        long total = goodsSize;
        GoodsData goodsData = new GoodsData();
        goodsData.setItems(goods);
        goodsData.setTotal(total);
        //封装ResponseType
        ResponseType responseType = new ResponseType();
        responseType.setErrmsg("成功");
        responseType.setErrno(0);
        responseType.setData(goodsData);
        return responseType;
    }

    @Override
    public List<CategoryResp> getCategory() {
        CategoryExample categoryExample1 = new CategoryExample();
        categoryExample1.createCriteria().andLevelEqualTo("L1");
        List<Category> categories = categoryMapper.selectByExample(categoryExample1);
        List<CategoryResp> list = new ArrayList<>();
        for (Category category : categories) {
            Integer value = category.getId();
            String label = category.getName();
            CategoryExample categoryExample2 = new CategoryExample();
            categoryExample2.createCriteria().andPidEqualTo(value);
            List<Category> categories1 = categoryMapper.selectByExample(categoryExample2);
            CategoryResp categoryResp = new CategoryResp();
            categoryResp.setValue(value);
            categoryResp.setLabel(label);
            List<CategoryResp> children = new ArrayList<>();
            for (Category child : categories1) {
                CategoryResp c = new CategoryResp();
                c.setValue(child.getId());
                c.setLabel(child.getName());
                children.add(c);
            }
            categoryResp.setChildren(children);
            list.add(categoryResp);
        }
        return list;
    }

    @Override
    public List<CategoryResp> getBrand() {
        BrandExample brandExample = new BrandExample();
        List<Brand> brands = brandMapper.selectByExample(brandExample);
        List<CategoryResp> list = new ArrayList<>();
        for (Brand brand : brands) {
            CategoryResp categoryResp = new CategoryResp();
            categoryResp.setLabel(brand.getName());
            categoryResp.setValue(brand.getId());
            list.add(categoryResp);
        }
        return list;
    }

    @Override
    public ResponseType createGoods(CreateGoods createGoods) {
        ResponseType responseType = new ResponseType();
        Goods goods = createGoods.getGoods();
        //判断必填写项是否为空，空则返回错误信息
        String goodsSn = goods.getGoodsSn();
        if (StringUtils.isEmpty(goodsSn)){
            responseType.setErrno(507);
            responseType.setErrmsg("必填项没有填写");
            return responseType;
        }
        //判断填写的内容是不是数字且位数在6到10位之间
        if (!goodsSn.matches("^[0-9]{6,10}")){
            responseType.setErrno(500);
            return responseType;
        }
        //判断商品名是否为空，空则返回错误信息
        String name = goods.getName();
        if (StringUtils.isEmpty(name)){
            responseType.setErrno(500);
            return responseType;
        }

        goods.setAddTime(new Date());
        goodsMapper.insertSelective(goods);
        //获取goodsID
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andNameEqualTo(name);
        List<Goods> goods1 = goodsMapper.selectByExample(goodsExample);
        Integer goodsId = null ;
        for (Goods goods2 : goods1) {
            goodsId = goods2.getId();
        }
        //获取各个配置的集合
        List<Attribute> attributes = createGoods.getAttributes();
        List<Product> products = createGoods.getProducts();
        List<Specification> specifications = createGoods.getSpecifications();
        //插入商品基本信息部分
        for (Attribute attribute : attributes) {
            attribute.setGoodsId(goodsId);
            attribute.setAddTime(new Date());
            attributeMapper.insertSelective(attribute);
        }
        for (Product product : products) {
            product.setGoodsId(goodsId);
            product.setAddTime(new Date());
            productMapper.insertSelective(product);
        }
        for (Specification specification : specifications) {
            specification.setGoodsId(goodsId);
            specification.setAddTime(new Date());
            specificationMapper.insertSelective(specification);
        }
        return responseType;
    }

    @Override
    public CreateGoods getGoodsDetail(Integer id) {
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andIdEqualTo(id);
        List<CreateGoods> list = new ArrayList<>();
        List<Goods> goods = goodsMapper.selectByExampleWithBLOBs(goodsExample);
        CreateGoods createGoods = new CreateGoods();
        for (Goods good : goods) {
            //Integer brandId = good.getBrandId();
            Integer categoryId = good.getCategoryId();
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            Integer pid = category.getPid();
            //Integer[] integers = new Integer[]{brandId,categoryId};
            Integer[] integers = new Integer[]{pid,categoryId};
            createGoods.setCategoryIds(integers);
            //attributes
            AttributeExample attributeExample = new AttributeExample();
            attributeExample.createCriteria().andGoodsIdEqualTo(id);
            List<Attribute> attributes = attributeMapper.selectByExample(attributeExample);
            //products
            ProductExample productExample = new ProductExample();
            productExample.createCriteria().andGoodsIdEqualTo(id);
            List<Product> products = productMapper.selectByExample(productExample);
            //specification
            SpecificationExample specificationExample = new SpecificationExample();
            specificationExample.createCriteria().andGoodsIdEqualTo(id);
            List<Specification> specifications = specificationMapper.selectByExample(specificationExample);
            createGoods.setGoods(good);
            createGoods.setAttributes(attributes);
            createGoods.setProducts(products);
            createGoods.setSpecifications(specifications);
            list.add(createGoods);
        }
        return createGoods;
    }

    @Override
    public int updateGoods(CreateGoods createGoods) {
        Goods goods = createGoods.getGoods();
        goods.setUpdateTime(new Date());

        //获取goodsID
        Integer goodsId = goods.getId();
        List<Attribute> attributes = createGoods.getAttributes();
        List<Product> products = createGoods.getProducts();
        List<Specification> specifications = createGoods.getSpecifications();

        //删除原来的数据
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andIdEqualTo(goodsId);
        goodsMapper.deleteByExample(goodsExample);
        AttributeExample attributeExample = new AttributeExample();
        attributeExample.createCriteria().andGoodsIdEqualTo(goodsId);
        attributeMapper.deleteByExample(attributeExample);
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andGoodsIdEqualTo(goodsId);
        productMapper.deleteByExample(productExample);
        SpecificationExample specificationExample = new SpecificationExample();
        specificationExample.createCriteria().andGoodsIdEqualTo(goodsId);
        specificationMapper.deleteByExample(specificationExample);

        //插入商品基本信息部分
        goodsMapper.insertSelective(goods);
        for (Attribute attribute : attributes) {
            attribute.setGoodsId(goodsId);
            attribute.setUpdateTime(new Date());
            attributeMapper.insertSelective(attribute);
        }
        for (Product product : products) {
            product.setGoodsId(goodsId);
            product.setUpdateTime(new Date());
            productMapper.insertSelective(product);
        }
        for (Specification specification : specifications) {
            specification.setGoodsId(goodsId);
            specification.setUpdateTime(new Date());
            specificationMapper.insertSelective(specification);
        }
        return 0;
    }

    @Override
    public int deleteGoods(Goods goods) {
        Integer goodsId = goods.getId();
        Goods goods1 = goodsMapper.selectByPrimaryKey(goodsId);
        goods1.setDeleted(true);
        goods1.setUpdateTime(new Date());
        goodsMapper.updateByPrimaryKey(goods1);

        AttributeExample attributeExample = new AttributeExample();
        attributeExample.createCriteria().andGoodsIdEqualTo(goodsId);
        List<Attribute> attributes = attributeMapper.selectByExample(attributeExample);
        for (Attribute attribute : attributes) {
            attribute.setUpdateTime(new Date());
            attribute.setDeleted(true);
            attributeMapper.updateByPrimaryKey(attribute);
        }

        ProductExample ProductExample = new ProductExample();
        ProductExample.createCriteria().andGoodsIdEqualTo(goodsId);
        List<Product> products = productMapper.selectByExample(ProductExample);
        for (Product product : products) {
            product.setUpdateTime(new Date());
            product.setDeleted(true);
            productMapper.updateByPrimaryKey(product);
        }

        SpecificationExample specificationExample = new SpecificationExample();
        specificationExample.createCriteria().andGoodsIdEqualTo(goodsId);
        List<Specification> specifications = specificationMapper.selectByExample(specificationExample);
        for (Specification specification : specifications) {
            specification.setUpdateTime(new Date());
            specification.setDeleted(true);
            specificationMapper.updateByPrimaryKey(specification);
        }
       /* ProductExample productExample = new ProductExample();
        productExample.createCriteria().andGoodsIdEqualTo(goodsId);
        productMapper.deleteByExample(productExample);
        SpecificationExample specificationExample = new SpecificationExample();
        specificationExample.createCriteria().andGoodsIdEqualTo(goodsId);
        specificationMapper.deleteByExample(specificationExample);*/
        return 0;
    }

    @Override
    public List<IndexBean.NewGoodsListBean> getNewGoodsList() {
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andIsNewEqualTo(true).andDeletedEqualTo(false);
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        List<IndexBean.NewGoodsListBean> newGoodsList = new ArrayList<>();
        for (Goods goods : goodsList) {
            IndexBean.NewGoodsListBean newGoodsListBean = new IndexBean.NewGoodsListBean();
            newGoodsListBean.setBrief(goods.getBrief());
            newGoodsListBean.setCounterPrice(goods.getCounterPrice());
            newGoodsListBean.setId(goods.getId());
            newGoodsListBean.setIsHot(goods.getIsHot());
            newGoodsListBean.setName(goods.getName());
            newGoodsListBean.setPicUrl(goods.getPicUrl());
            newGoodsListBean.setRetailPrice(goods.getRetailPrice());
            newGoodsListBean.setIsNew(true);
            newGoodsList.add(newGoodsListBean);
        }
        return newGoodsList;
    }

    @Override
    public List<IndexBean.ChannelBean> getChannel() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andLevelEqualTo("L1").andDeletedEqualTo(false);
        List<Category> categorieList = categoryMapper.selectByExample(categoryExample);
        List<IndexBean.ChannelBean> channel = new ArrayList<>();
        for (Category category : categorieList) {
            IndexBean.ChannelBean channelBean = new IndexBean.ChannelBean();
            channelBean.setId(category.getId());
            channelBean.setIconUrl(category.getIconUrl());
            channelBean.setName(category.getName());
            channel.add(channelBean);
        }
        return channel;
    }

    @Override
    public List<IndexBean.HotGoodsListBean> getHotGoodsList() {
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andIsHotEqualTo(true).andDeletedEqualTo(false);
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        List<IndexBean.HotGoodsListBean> hotGoodsList = new ArrayList<>();
        for (Goods goods : goodsList) {
            IndexBean.HotGoodsListBean hotGoodsListBean = new IndexBean.HotGoodsListBean();
            hotGoodsListBean.setBrief(goods.getBrief());
            hotGoodsListBean.setCounterPrice(goods.getCounterPrice());
            hotGoodsListBean.setId(goods.getId());
            hotGoodsListBean.setName(goods.getName());
            hotGoodsListBean.setPicUrl(goods.getPicUrl());
            hotGoodsListBean.setRetailPrice(goods.getRetailPrice());
            hotGoodsListBean.setIsNew(goods.getIsNew());
            hotGoodsListBean.setIsHot(true);
            hotGoodsList.add(hotGoodsListBean);
        }
        return hotGoodsList;
    }

    @Override
    public List<IndexBean.FloorGoodsListBean> getFloorGoodsList() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andLevelEqualTo("L1").andDeletedEqualTo(false);
        List<Category> categories = categoryMapper.selectByExample(categoryExample);
        List<IndexBean.FloorGoodsListBean> floorGoodsList = new ArrayList<>();
        for (Category category : categories) {
            categoryExample = new CategoryExample();
            categoryExample.createCriteria().andPidEqualTo(category.getId());
            List<Category> categoriesSec = categoryMapper.selectByExample(categoryExample);
            List<Goods> goods = new ArrayList<>();
            outer: for (Category categorySec : categoriesSec) {
                GoodsExample goodsExample = new GoodsExample();
                goodsExample.createCriteria().andCategoryIdEqualTo(categorySec.getId());
                for (Goods goodsSec : goodsMapper.selectByExample(goodsExample)) {
                    if (goods.size() >= 6) {
                        break outer;
                    }
                    goods.add(goodsSec);
                }
            }
            IndexBean.FloorGoodsListBean floorGoodsListBean = new IndexBean.FloorGoodsListBean();
            floorGoodsListBean.setId(category.getId());
            floorGoodsListBean.setName(category.getName());
            floorGoodsListBean.setGoodsList(goods);
            floorGoodsList.add(floorGoodsListBean);
        }
        return floorGoodsList;
    }

}

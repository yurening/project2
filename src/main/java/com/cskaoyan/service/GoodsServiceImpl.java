package com.cskaoyan.service;

import com.cskaoyan.bean.goods.*;
import com.cskaoyan.mapper.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        List<Goods> goods = goodsMapper.selectByExample(goodsExample);
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
    public int createGoods(CreateGoods createGoods) {
        Goods goods = createGoods.getGoods();
        String name = goods.getName();
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
        return 0;
    }

    @Override
    public CreateGoods getGoodsDetail(Integer id) {
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andIdEqualTo(id);
        List<CreateGoods> list = new ArrayList<>();
        List<Goods> goods = goodsMapper.selectByExample(goodsExample);
        CreateGoods createGoods = new CreateGoods();
        for (Goods good : goods) {
            Integer brandId = good.getBrandId();
            Integer categoryId = good.getCategoryId();
            Integer[] integers = new Integer[]{brandId,categoryId};
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


}

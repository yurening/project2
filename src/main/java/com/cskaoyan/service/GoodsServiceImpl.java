package com.cskaoyan.service;

import com.cskaoyan.bean.generalize.GrouponRules;
import com.cskaoyan.bean.generalize.GrouponRulesExample;
import com.cskaoyan.bean.goods.*;
import com.cskaoyan.bean.wx_index.HomeIndex;
import com.cskaoyan.mapper.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

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
    @Autowired
    GrouponRulesMapper grouponRulesMapper;
    @Autowired
    IssueMapper issueMapper;
    @Autowired
    CommentMapper commentMapper;

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

            //插入歷史記錄表

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

    @Transactional
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
            product.setId(null);
            product.setGoodsId(goodsId);
            product.setUpdateTime(new Date());
            productMapper.insert(product);
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
    public Long getGoodsCount() {
        GoodsExample goodsExample = new GoodsExample();
        long l = goodsMapper.countByExample(goodsExample);
        return l;
    }

    @Override
    public ResponseType getGoodsByBrandId(Integer brandId, Integer page, Integer size) {
        //获取满足条件的数据条目数
        GoodsExample goodsExample1 = new GoodsExample();
        goodsExample1.createCriteria().andBrandIdEqualTo(brandId);
        List<Goods> goods1 = goodsMapper.selectByExample(goodsExample1);
        int size1 = goods1.size();
        //分页处理
        PageHelper.startPage(page,size);
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria().andBrandIdEqualTo(brandId);
        List<Goods> goods = goodsMapper.selectByExample(goodsExample);
        //获取filterCategroy
        List<Category> list = new ArrayList<>();
        for (Goods good : goods) {
            Integer categoryId = good.getCategoryId();
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            list.add(category);
        }
        //拼接返回的data
        Map map = new HashMap();
        map.put("goodsList",goods);
        map.put("count",size1);
        map.put("filterCategoryList",list);
        //新建返回的bean
        ResponseType responseType = new ResponseType();
        responseType.setErrno(0);
        responseType.setData(map);
        responseType.setErrmsg("成功");
        return responseType;
    }

    @Override
    public ResponseType getGoodsAndAllById(Integer id) {
        SpecificationExample specificationExample = new SpecificationExample();
        specificationExample.createCriteria().andGoodsIdEqualTo(id);
        //该商品下的所有规格
        List<Specification> allSpec = specificationMapper.selectByExample(specificationExample);
        Set<String> specNams = new HashSet<>();
        List<Map> returnList = new ArrayList<>();
        for (Specification specification : allSpec) {
            String specName = specification.getSpecification();
            specNams.add(specName);//先获取规格名字
        }
        for (String specNam : specNams) {
            Map map = new HashMap();
            List<Specification> specifications = new ArrayList<>();
            for (Specification specification : allSpec) {
                if(specification.getSpecification().equals(specNam)){
                    specifications.add(specification);
                }
            }
            map.put("name",specNam);
            map.put("valueList",specifications);
            returnList.add(map);
        }
        //获取团购信息
        GrouponRulesExample grouponRulesExample = new GrouponRulesExample();
        grouponRulesExample.createCriteria().andGoodsIdEqualTo(id);
        List<GrouponRules> grouponRules = grouponRulesMapper.selectByExample(grouponRulesExample);
        //获取常见问题
        IssueExample issueExample = new IssueExample();
        List<Issue> issues = issueMapper.selectByExample(issueExample);
        //获取商品评论评论
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andTypeEqualTo( 0).andValueIdEqualTo(id);
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        List<Comment> showList = new ArrayList<>();
        int commentSize = comments.size();
        int size = 0 ;
        for (Comment comment : comments) {
            if (size < 6){
                showList.add(comment);
                size++;
            }
        }
        Map comment = new HashMap();
        comment.put("data",showList);
        comment.put("count",commentSize);
        //attribute
        AttributeExample attributeExample = new AttributeExample();
        attributeExample.createCriteria().andGoodsIdEqualTo(id);
        List<Attribute> attributes = attributeMapper.selectByExample(attributeExample);
        //brand
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        Integer brandId = goods.getBrandId();
        Brand brand = brandMapper.selectByPrimaryKey(brandId);
        //product
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andGoodsIdEqualTo(id);
        List<Product> products = productMapper.selectByExample(productExample);
        //goods
        Goods good = goodsMapper.selectByPrimaryKey(id);


        Map returnMap = new HashMap();
        returnMap.put("specificationList",returnList);
        returnMap.put("groupon",grouponRules);
        returnMap.put("issue",issues);
        returnMap.put("userHasCollect",0);
        returnMap.put("shareImage","");
        returnMap.put("comment",comment);
        returnMap.put("attribute",attributes);
        returnMap.put("brand",brand);
        returnMap.put("productList",products);
        returnMap.put("info",good);
        //拼返回的类
        ResponseType responseType = new ResponseType();
        responseType.setData(returnMap);
        responseType.setErrmsg("成功");
        responseType.setErrno(0);
        return responseType;
    }

    @Override
    public ResponseType getCategoryByParent(Integer id) {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andPidEqualTo(id);
        List<Category> categories = categoryMapper.selectByExample(categoryExample);
        Map map = new HashMap();
        /**
         *  判断传过来的是一级还是二级的目录id
         *  如果是二级目录的id，集合是空
         */
        if (categories.size()==0){
            //是二级目录，去找他的一级目录
            Category category = categoryMapper.selectByPrimaryKey(id);//获得currentCategory
            Integer pid = category.getPid();
            Category category1 = categoryMapper.selectByPrimaryKey(pid);
            Integer id1 = category1.getId();//获得parentCategory
            CategoryExample categoryExample1 = new CategoryExample();
            categoryExample1.createCriteria().andPidEqualTo(id1);
            List<Category> categories1 = categoryMapper.selectByExample(categoryExample1);//获得brotherCategory
            map.put("brotherCategory",categories1);
            map.put("parentCategory",category1);
            map.put("currentCategory",category);
        }else{
            //获取当前一级目录
            Category category = categoryMapper.selectByPrimaryKey(id);
            map.put("brotherCategory",categories);
            map.put("parentCategory",category);
            map.put("currentCategory",categories.get(0));
        }

        ResponseType responseType = new ResponseType();
        responseType.setErrno(0);
        responseType.setErrmsg("成功");
        responseType.setData(map);
        return responseType;
    }

    @Override
    public ResponseType getGoodsByCategory(Integer id) {
        //获取goodsList
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andCategoryIdEqualTo(id);
        List<Goods> goods = goodsMapper.selectByExample(goodsExample);
        //获取个数
        int count = goods.size();
        //获取filterCategoryList
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andLevelEqualTo("l2");
        List<Category> categories = categoryMapper.selectByExample(categoryExample);
        Map map = new HashMap();
        map.put("goodsList",goods);
        map.put("count",count);
        map.put("filterCategoryList",categories);

        ResponseType responseType = new ResponseType();
        responseType.setData(map);
        responseType.setErrmsg("成功");
        responseType.setErrno(0);
        return responseType;
    }

    @Override
    public ResponseType getRelativeGoods(Integer id) {
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        Integer categoryId = goods.getCategoryId();
        PageHelper.startPage(0,6);
        GoodsExample goodsExample = new GoodsExample();
        String clause = "sort_order desc";
        goodsExample.setOrderByClause(clause);
        goodsExample.createCriteria().andCategoryIdEqualTo(categoryId);
        List<Goods> goods1 = goodsMapper.selectByExample(goodsExample);
        Map map = new HashMap();
        map.put("goodsList",goods1);

        ResponseType responseType = new ResponseType();
        responseType.setErrno(0);
        responseType.setErrmsg("成功");
        responseType.setData(map);
        return responseType;
    }

    @Override
    public ResponseType getGoodsByKeyword(String keyword, String sort, String order, Integer page, Integer size, Integer categoryId) {
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.setOrderByClause(sort+" "+order);
        String trim = keyword.trim();
        GoodsExample.Criteria criteria = goodsExample.createCriteria().andNameLike("%" + trim + "%");
        List<Goods> goods = goodsMapper.selectByExample(goodsExample);
        if (categoryId!=0){
            criteria.andCategoryIdEqualTo(categoryId);
        }
        List<Category> list = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        for (Goods good : goods) {
            Integer categoryId1 = good.getCategoryId();
            CategoryExample categoryExample = new CategoryExample();
            categoryExample.createCriteria().andIdEqualTo(categoryId1);
            List<Category> categories = categoryMapper.selectByExample(categoryExample);
            Category category = categories.get(0);
            String name = category.getName();
            if(!nameList.contains(name)){
                nameList.add(name);
                list.add(category);
            }
        }
        int count = goods.size();

        Map map = new HashMap();
        map.put("goodsList",goods);
        map.put("count",count);
        map.put("filterCategoryList",list);
        /*List<Goods> goods = goodsMapper.selectByExample(goodsExample);*/
        ResponseType responseType = new ResponseType();
        responseType.setErrno(0);
        responseType.setErrmsg("成功");
        responseType.setData(map);
        return responseType;
    }

    @Override
    public ResponseType getGoodsByIsHot(boolean isHot, Integer page, Integer size, String order, String sort,Integer categoryId) {
        PageHelper.startPage(page,size);
        String cluse = sort+" "+order;
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.setOrderByClause(cluse);
        GoodsExample.Criteria criteria = goodsExample.createCriteria().andIsHotEqualTo(true);
        List<Goods> goods = goodsMapper.selectByExample(goodsExample);
        if (categoryId!=0){
            criteria.andCategoryIdEqualTo(categoryId);
        }
        List<Category> list = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        for (Goods good : goods) {
            Integer categoryId1 = good.getCategoryId();
            CategoryExample categoryExample = new CategoryExample();
            categoryExample.createCriteria().andIdEqualTo(categoryId1);
            List<Category> categories = categoryMapper.selectByExample(categoryExample);
            Category category = categories.get(0);
            String name = category.getName();
            if(!nameList.contains(name)){
                nameList.add(name);
                list.add(category);
            }
        }
        int count = goods.size();

        Map map = new HashMap();
        map.put("goodsList",goods);
        map.put("count",count);
        map.put("filterCategoryList",list);
        /*List<Goods> goods = goodsMapper.selectByExample(goodsExample);*/
        ResponseType responseType = new ResponseType();
        responseType.setErrno(0);
        responseType.setErrmsg("成功");
        responseType.setData(map);
        return responseType;
    }

    @Override
    public ResponseType getGoodsByIsNew(boolean isNew, Integer page, Integer size, String order, String sort, Integer categoryId) {
        PageHelper.startPage(page,size);
        String cluse = sort+" "+order;
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.setOrderByClause(cluse);
        GoodsExample.Criteria criteria = goodsExample.createCriteria().andIsNewEqualTo(true);
        List<Goods> goods = goodsMapper.selectByExample(goodsExample);
        if (categoryId!=0){
            criteria.andCategoryIdEqualTo(categoryId);
        }
        List<Category> list = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        for (Goods good : goods) {
            Integer categoryId1 = good.getCategoryId();
            CategoryExample categoryExample = new CategoryExample();
            categoryExample.createCriteria().andIdEqualTo(categoryId1);
            List<Category> categories = categoryMapper.selectByExample(categoryExample);
            Category category = categories.get(0);
            String name = category.getName();
            if(!nameList.contains(name)){
                nameList.add(name);
                list.add(category);
            }
        }
        int count = goods.size();

        Map map = new HashMap();
        map.put("goodsList",goods);
        map.put("count",count);
        map.put("filterCategoryList",list);
        /*List<Goods> goods = goodsMapper.selectByExample(goodsExample);*/
        ResponseType responseType = new ResponseType();
        responseType.setErrno(0);
        responseType.setErrmsg("成功");
        responseType.setData(map);
        return responseType;
    }


    public List<HomeIndex.NewGoodsListBean> getNewGoodsList() {
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andIsNewEqualTo(true).andDeletedEqualTo(false);
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        List<HomeIndex.NewGoodsListBean> newGoodsList = new ArrayList<>();
        for (Goods goods : goodsList) {
            HomeIndex.NewGoodsListBean newGoodsListBean = new HomeIndex.NewGoodsListBean();
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
    public List<HomeIndex.ChannelBean> getChannel() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andLevelEqualTo("L1").andDeletedEqualTo(false);
        List<Category> categorieList = categoryMapper.selectByExample(categoryExample);
        List<HomeIndex.ChannelBean> channel = new ArrayList<>();
        for (Category category : categorieList) {
            HomeIndex.ChannelBean channelBean = new HomeIndex.ChannelBean();
            channelBean.setId(category.getId());
            channelBean.setIconUrl(category.getIconUrl());
            channelBean.setName(category.getName());
            channel.add(channelBean);
        }
        return channel;
    }

    @Override
    public List<HomeIndex.HotGoodsListBean> getHotGoodsList() {
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andIsHotEqualTo(true).andDeletedEqualTo(false);
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        List<HomeIndex.HotGoodsListBean> hotGoodsList = new ArrayList<>();
        for (Goods goods : goodsList) {
            HomeIndex.HotGoodsListBean hotGoodsListBean = new HomeIndex.HotGoodsListBean();
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
    public List<HomeIndex.FloorGoodsListBean> getFloorGoodsList() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andLevelEqualTo("L1").andDeletedEqualTo(false);
        List<Category> categories = categoryMapper.selectByExample(categoryExample);
        List<HomeIndex.FloorGoodsListBean> floorGoodsList = new ArrayList<>();
        for (Category category : categories) {
            categoryExample = new CategoryExample();
            categoryExample.createCriteria().andPidEqualTo(category.getId()).andDeletedEqualTo(false);
            List<Category> categoriesSec = categoryMapper.selectByExample(categoryExample);
            List<Goods> goods = new ArrayList<>();
            outer: for (Category categorySec : categoriesSec) {
                GoodsExample goodsExample = new GoodsExample();
                goodsExample.createCriteria().andCategoryIdEqualTo(categorySec.getId()).andDeletedEqualTo(false);
                for (Goods goodsSec : goodsMapper.selectByExample(goodsExample)) {
                    if (goods.size() >= 6) {
                        break outer;
                    }
                    goods.add(goodsSec);
                }
            }
            HomeIndex.FloorGoodsListBean floorGoodsListBean = new HomeIndex.FloorGoodsListBean();
            floorGoodsListBean.setId(category.getId());
            floorGoodsListBean.setName(category.getName());
            floorGoodsListBean.setGoodsList(goods);
            floorGoodsList.add(floorGoodsListBean);
        }
        return floorGoodsList;
    }

    public static class DataBean {
        /**
         * currentCategory : {"id":1036098,"name":"男装","keywords":"男装关键字","desc":"男装简介","pid":0,"iconUrl":"http://192.168.2.100:8081/wx/storage/fetch/eqnkzk7aw2ihjhpj5nfg.jpg","picUrl":"http://192.168.2.100:8081/wx/storage/fetch/m6nspdmn9il21qgueo6r.jpg","level":"L1","sortOrder":50,"addTime":"2019-11-18 05:58:00","updateTime":"2019-11-18 09:57:22","deleted":false}
         * categoryList : [{"id":1036098,"name":"男装","keywords":"男装关键字","desc":"男装简介","pid":0,"iconUrl":"http://192.168.2.100:8081/wx/storage/fetch/eqnkzk7aw2ihjhpj5nfg.jpg","picUrl":"http://192.168.2.100:8081/wx/storage/fetch/m6nspdmn9il21qgueo6r.jpg","level":"L1","sortOrder":50,"addTime":"2019-11-18 05:58:00","updateTime":"2019-11-18 09:57:22","deleted":false},{"id":1036103,"name":"女装","keywords":"女装关键字","desc":"女装简介","pid":0,"iconUrl":"http://192.168.2.100:8081/wx/storage/fetch/zoius8gnlnz3sfv6qec6.jpg","picUrl":"http://192.168.2.100:8081/wx/storage/fetch/5ed97l7zcm59e8mo4ied.jpg","level":"L1","sortOrder":50,"addTime":"2019-11-18 10:49:07","updateTime":"2019-11-18 10:49:07","deleted":false}]
         * currentSubCategory : [{"id":1036099,"name":"男休闲裤","keywords":"男休闲裤关键字","desc":"男休闲裤简介","pid":1036098,"iconUrl":"http://192.168.2.100:8081/wx/storage/fetch/5gou6qjt7wqgrbnv4e54.jpg","picUrl":"http://192.168.2.100:8081/wx/storage/fetch/wxs1gr99y78j1d0c23a0.jpg","level":"L2","sortOrder":50,"addTime":"2019-11-18 05:58:35","updateTime":"2019-11-18 09:58:44","deleted":false},{"id":1036102,"name":"男牛仔裤","keywords":"男牛仔裤关键字","desc":"男牛仔裤简介","pid":1036098,"iconUrl":"http://192.168.2.100:8081/wx/storage/fetch/hdvcii8ksghcqg316p3r.jpeg","picUrl":"http://192.168.2.100:8081/wx/storage/fetch/4xjbk4fnzljoepjmxh4v.jpg","level":"L2","sortOrder":50,"addTime":"2019-11-18 09:58:06","updateTime":"2019-11-18 09:58:06","deleted":false}]
         */

        private CurrentCategoryBean currentCategory;
        private List<CategoryListBean> categoryList;
        private List<CurrentSubCategoryBean> currentSubCategory;

        public CurrentCategoryBean getCurrentCategory() {
            return currentCategory;
        }

        public void setCurrentCategory(CurrentCategoryBean currentCategory) {
            this.currentCategory = currentCategory;
        }

        public List<CategoryListBean> getCategoryList() {
            return categoryList;
        }

        public void setCategoryList(List<CategoryListBean> categoryList) {
            this.categoryList = categoryList;
        }

        public List<CurrentSubCategoryBean> getCurrentSubCategory() {
            return currentSubCategory;
        }

        public void setCurrentSubCategory(List<CurrentSubCategoryBean> currentSubCategory) {
            this.currentSubCategory = currentSubCategory;
        }

        public static class CurrentCategoryBean {
            /**
             * id : 1036098
             * name : 男装
             * keywords : 男装关键字
             * desc : 男装简介
             * pid : 0
             * iconUrl : http://192.168.2.100:8081/wx/storage/fetch/eqnkzk7aw2ihjhpj5nfg.jpg
             * picUrl : http://192.168.2.100:8081/wx/storage/fetch/m6nspdmn9il21qgueo6r.jpg
             * level : L1
             * sortOrder : 50
             * addTime : 2019-11-18 05:58:00
             * updateTime : 2019-11-18 09:57:22
             * deleted : false
             */

            private int id;
            private String name;
            private String keywords;
            private String desc;
            private int pid;
            private String iconUrl;
            private String picUrl;
            private String level;
            private int sortOrder;
            private String addTime;
            private String updateTime;
            private boolean deleted;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getKeywords() {
                return keywords;
            }

            public void setKeywords(String keywords) {
                this.keywords = keywords;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public int getSortOrder() {
                return sortOrder;
            }

            public void setSortOrder(int sortOrder) {
                this.sortOrder = sortOrder;
            }

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public boolean isDeleted() {
                return deleted;
            }

            public void setDeleted(boolean deleted) {
                this.deleted = deleted;
            }
        }

        public static class CategoryListBean {
            /**
             * id : 1036098
             * name : 男装
             * keywords : 男装关键字
             * desc : 男装简介
             * pid : 0
             * iconUrl : http://192.168.2.100:8081/wx/storage/fetch/eqnkzk7aw2ihjhpj5nfg.jpg
             * picUrl : http://192.168.2.100:8081/wx/storage/fetch/m6nspdmn9il21qgueo6r.jpg
             * level : L1
             * sortOrder : 50
             * addTime : 2019-11-18 05:58:00
             * updateTime : 2019-11-18 09:57:22
             * deleted : false
             */

            private int id;
            private String name;
            private String keywords;
            private String desc;
            private int pid;
            private String iconUrl;
            private String picUrl;
            private String level;
            private int sortOrder;
            private String addTime;
            private String updateTime;
            private boolean deleted;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getKeywords() {
                return keywords;
            }

            public void setKeywords(String keywords) {
                this.keywords = keywords;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public int getSortOrder() {
                return sortOrder;
            }

            public void setSortOrder(int sortOrder) {
                this.sortOrder = sortOrder;
            }

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public boolean isDeleted() {
                return deleted;
            }

            public void setDeleted(boolean deleted) {
                this.deleted = deleted;
            }
        }

        public static class CurrentSubCategoryBean {
            /**
             * id : 1036099
             * name : 男休闲裤
             * keywords : 男休闲裤关键字
             * desc : 男休闲裤简介
             * pid : 1036098
             * iconUrl : http://192.168.2.100:8081/wx/storage/fetch/5gou6qjt7wqgrbnv4e54.jpg
             * picUrl : http://192.168.2.100:8081/wx/storage/fetch/wxs1gr99y78j1d0c23a0.jpg
             * level : L2
             * sortOrder : 50
             * addTime : 2019-11-18 05:58:35
             * updateTime : 2019-11-18 09:58:44
             * deleted : false
             */

            private int id;
            private String name;
            private String keywords;
            private String desc;
            private int pid;
            private String iconUrl;
            private String picUrl;
            private String level;
            private int sortOrder;
            private String addTime;
            private String updateTime;
            private boolean deleted;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getKeywords() {
                return keywords;
            }

            public void setKeywords(String keywords) {
                this.keywords = keywords;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public int getSortOrder() {
                return sortOrder;
            }

            public void setSortOrder(int sortOrder) {
                this.sortOrder = sortOrder;
            }

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public boolean isDeleted() {
                return deleted;
            }

            public void setDeleted(boolean deleted) {
                this.deleted = deleted;
            }
        }
    }
}

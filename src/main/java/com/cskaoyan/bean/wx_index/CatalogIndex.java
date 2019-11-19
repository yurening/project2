package com.cskaoyan.bean.wx_index;

import com.cskaoyan.bean.mall.category.MallCategory;

import java.util.List;


public class CatalogIndex {
    /**
     * currentCategory : {"id":1036098,"name":"男装","keywords":"男装关键字","desc":"男装简介","pid":0,"iconUrl":"http://192.168.2.100:8081/wx/storage/fetch/eqnkzk7aw2ihjhpj5nfg.jpg","picUrl":"http://192.168.2.100:8081/wx/storage/fetch/m6nspdmn9il21qgueo6r.jpg","level":"L1","sortOrder":50,"addTime":"2019-11-18 05:58:00","updateTime":"2019-11-18 09:57:22","deleted":false}
     * categoryList : [{"id":1036098,"name":"男装","keywords":"男装关键字","desc":"男装简介","pid":0,"iconUrl":"http://192.168.2.100:8081/wx/storage/fetch/eqnkzk7aw2ihjhpj5nfg.jpg","picUrl":"http://192.168.2.100:8081/wx/storage/fetch/m6nspdmn9il21qgueo6r.jpg","level":"L1","sortOrder":50,"addTime":"2019-11-18 05:58:00","updateTime":"2019-11-18 09:57:22","deleted":false},{"id":1036103,"name":"女装","keywords":"女装关键字","desc":"女装简介","pid":0,"iconUrl":"http://192.168.2.100:8081/wx/storage/fetch/zoius8gnlnz3sfv6qec6.jpg","picUrl":"http://192.168.2.100:8081/wx/storage/fetch/5ed97l7zcm59e8mo4ied.jpg","level":"L1","sortOrder":50,"addTime":"2019-11-18 10:49:07","updateTime":"2019-11-18 10:49:07","deleted":false}]
     * currentSubCategory : [{"id":1036099,"name":"男休闲裤","keywords":"男休闲裤关键字","desc":"男休闲裤简介","pid":1036098,"iconUrl":"http://192.168.2.100:8081/wx/storage/fetch/5gou6qjt7wqgrbnv4e54.jpg","picUrl":"http://192.168.2.100:8081/wx/storage/fetch/wxs1gr99y78j1d0c23a0.jpg","level":"L2","sortOrder":50,"addTime":"2019-11-18 05:58:35","updateTime":"2019-11-18 09:58:44","deleted":false},{"id":1036102,"name":"男牛仔裤","keywords":"男牛仔裤关键字","desc":"男牛仔裤简介","pid":1036098,"iconUrl":"http://192.168.2.100:8081/wx/storage/fetch/hdvcii8ksghcqg316p3r.jpeg","picUrl":"http://192.168.2.100:8081/wx/storage/fetch/4xjbk4fnzljoepjmxh4v.jpg","level":"L2","sortOrder":50,"addTime":"2019-11-18 09:58:06","updateTime":"2019-11-18 09:58:06","deleted":false}]
     */

    private MallCategory currentCategory;
    private List<MallCategory> categoryList;
    private List<MallCategory> currentSubCategory;

    public MallCategory getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(MallCategory currentCategory) {
        this.currentCategory = currentCategory;
    }

    public List<MallCategory> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<MallCategory> categoryList) {
        this.categoryList = categoryList;
    }

    public List<MallCategory> getCurrentSubCategory() {
        return currentSubCategory;
    }

    public void setCurrentSubCategory(List<MallCategory> currentSubCategory) {
        this.currentSubCategory = currentSubCategory;
    }
}


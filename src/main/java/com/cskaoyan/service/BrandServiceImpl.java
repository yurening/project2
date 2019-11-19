package com.cskaoyan.service;

import com.cskaoyan.bean.goods.Brand;
import com.cskaoyan.bean.goods.BrandExample;
import com.cskaoyan.bean.goods.ResponseType;
import com.cskaoyan.mapper.BrandMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandMapper brandMapper;

    @Override
    public ResponseType getBrandList(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        BrandExample brandExample = new BrandExample();
        List<Brand> brands = brandMapper.selectByExample(brandExample);
        int brandsSize = brands.size();
        Map map = new HashMap();
        map.put("totalPages",brandsSize);
        map.put("brandList",brands);
        ResponseType responseType = new ResponseType();
        responseType.setErrno(0);
        responseType.setErrmsg("成功");
        responseType.setData(map);
        return responseType;
    }
}

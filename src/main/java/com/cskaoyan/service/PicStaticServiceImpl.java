package com.cskaoyan.service;

import com.cskaoyan.bean.goods.StaticPhoto;
import com.cskaoyan.mapper.StaticPhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PicStaticServiceImpl implements PicStaticService {
    @Autowired
    StaticPhotoMapper staticPhotoMapper;

    @Override
    public StaticPhoto addFile(StaticPhoto staticPhoto) {
        staticPhoto.setAddTime(new Date());
        staticPhotoMapper.insert(staticPhoto);
        /*System.out.println(staticPhoto);*/
        StaticPhoto staticPhoto1 = staticPhotoMapper.selectByPrimaryKey(staticPhoto.getId());
        return staticPhoto1;
    }
}

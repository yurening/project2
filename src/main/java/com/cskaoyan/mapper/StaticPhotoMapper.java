package com.cskaoyan.mapper;

import com.cskaoyan.bean.goods.StaticPhoto;
import com.cskaoyan.bean.goods.StaticPhotoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StaticPhotoMapper {
    long countByExample(StaticPhotoExample example);

    int deleteByExample(StaticPhotoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StaticPhoto record);

    int insertSelective(StaticPhoto record);

    List<StaticPhoto> selectByExample(StaticPhotoExample example);

    StaticPhoto selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StaticPhoto record, @Param("example") StaticPhotoExample example);

    int updateByExample(@Param("record") StaticPhoto record, @Param("example") StaticPhotoExample example);

    int updateByPrimaryKeySelective(StaticPhoto record);

    int updateByPrimaryKey(StaticPhoto record);
}

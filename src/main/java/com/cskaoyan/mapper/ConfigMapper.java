package com.cskaoyan.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ConfigMapper {

    String getSystemValueByID(int id);


    void updateSystemValueByKey(@Param("key") String key, @Param("value") String value);
}

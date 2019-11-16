package com.cskaoyan.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface TestMapper {

    @Insert("insert into cskaoyan_mall_admin (username) value (#{username})")
    int insertUser(@Param("username") String username);
}

package com.cskaoyan.mapper;

import com.cskaoyan.bean.mall.system.MallSystem;
import com.cskaoyan.bean.mall.system.MallSystemExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MallSystemMapper {
    long countByExample(MallSystemExample example);

    int deleteByExample(MallSystemExample example);

    @Delete({
        "delete from cskaoyan_mall_system",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into cskaoyan_mall_system (id, key_name, ",
        "key_value, add_time, ",
        "update_time, deleted)",
        "values (#{id,jdbcType=INTEGER}, #{keyName,jdbcType=VARCHAR}, ",
        "#{keyValue,jdbcType=VARCHAR}, #{addTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{deleted,jdbcType=BIT})"
    })
    int insert(MallSystem record);

    int insertSelective(MallSystem record);

    List<MallSystem> selectByExample(MallSystemExample example);

    @Select({
        "select",
        "id, key_name, key_value, add_time, update_time, deleted",
        "from cskaoyan_mall_system",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.cskaoyan.mapper.MallSystemMapper.BaseResultMap")
    MallSystem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallSystem record, @Param("example") MallSystemExample example);

    int updateByExample(@Param("record") MallSystem record, @Param("example") MallSystemExample example);

    int updateByPrimaryKeySelective(MallSystem record);

    @Update({
        "update cskaoyan_mall_system",
        "set key_name = #{keyName,jdbcType=VARCHAR},",
          "key_value = #{keyValue,jdbcType=VARCHAR},",
          "add_time = #{addTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "deleted = #{deleted,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(MallSystem record);
}

package com.cskaoyan.mapper;

import com.cskaoyan.bean.mall.keyword.MallKeyword;
import com.cskaoyan.bean.mall.keyword.MallKeywordExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MallKeywordMapper {
    long countByExample(MallKeywordExample example);

    int deleteByExample(MallKeywordExample example);

    @Delete({
        "delete from cskaoyan_mall_keyword",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into cskaoyan_mall_keyword (id, keyword, ",
        "url, is_hot, is_default, ",
        "sort_order, add_time, ",
        "update_time, deleted)",
        "values (#{id,jdbcType=INTEGER}, #{keyword,jdbcType=VARCHAR}, ",
        "#{url,jdbcType=VARCHAR}, #{isHot,jdbcType=BIT}, #{isDefault,jdbcType=BIT}, ",
        "#{sortOrder,jdbcType=INTEGER}, #{addTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{deleted,jdbcType=BIT})"
    })
    int insert(MallKeyword record);

    int insertSelective(MallKeyword record);

    List<MallKeyword> selectByExample(MallKeywordExample example);

    @Select({
        "select",
        "id, keyword, url, is_hot, is_default, sort_order, add_time, update_time, deleted",
        "from cskaoyan_mall_keyword",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.cskaoyan.mapper.MallKeywordMapper.BaseResultMap")
    MallKeyword selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallKeyword record, @Param("example") MallKeywordExample example);

    int updateByExample(@Param("record") MallKeyword record, @Param("example") MallKeywordExample example);

    int updateByPrimaryKeySelective(MallKeyword record);

    @Update({
        "update cskaoyan_mall_keyword",
        "set keyword = #{keyword,jdbcType=VARCHAR},",
          "url = #{url,jdbcType=VARCHAR},",
          "is_hot = #{isHot,jdbcType=BIT},",
          "is_default = #{isDefault,jdbcType=BIT},",
          "sort_order = #{sortOrder,jdbcType=INTEGER},",
          "add_time = #{addTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "deleted = #{deleted,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(MallKeyword record);

    int lastInsertId();
}

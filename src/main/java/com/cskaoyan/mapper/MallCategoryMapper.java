package com.cskaoyan.mapper;

import com.cskaoyan.bean.mall.category.MallCategory;
import com.cskaoyan.bean.mall.category.MallCategoryExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MallCategoryMapper {
    long countByExample(MallCategoryExample example);

    int deleteByExample(MallCategoryExample example);

    @Delete({
        "delete from cskaoyan_mall_category",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into cskaoyan_mall_category (id, name, ",
        "keywords, `desc`, ",
        "pid, icon_url, pic_url, ",
        "level, sort_order, ",
        "add_time, update_time, ",
        "deleted)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{keywords,jdbcType=VARCHAR}, #{desc,jdbcType=VARCHAR}, ",
        "#{pid,jdbcType=INTEGER}, #{iconUrl,jdbcType=VARCHAR}, #{picUrl,jdbcType=VARCHAR}, ",
        "#{level,jdbcType=VARCHAR}, #{sortOrder,jdbcType=TINYINT}, ",
        "#{addTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP}, ",
        "#{deleted,jdbcType=BIT})"
    })
    int insert(MallCategory record);

    int insertSelective(MallCategory record);

    List<MallCategory> selectByExample(MallCategoryExample example);

    @Select({
        "select",
        "id, name, keywords, `desc`, pid, icon_url, pic_url, level, sort_order, add_time, ",
        "update_time, deleted",
        "from cskaoyan_mall_category",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.cskaoyan.mapper.MallCategoryMapper.BaseResultMap")
    MallCategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallCategory record, @Param("example") MallCategoryExample example);

    int updateByExample(@Param("record") MallCategory record, @Param("example") MallCategoryExample example);

    int updateByPrimaryKeySelective(MallCategory record);

    @Update({
        "update cskaoyan_mall_category",
        "set name = #{name,jdbcType=VARCHAR},",
          "keywords = #{keywords,jdbcType=VARCHAR},",
          "`desc` = #{desc,jdbcType=VARCHAR},",
          "pid = #{pid,jdbcType=INTEGER},",
          "icon_url = #{iconUrl,jdbcType=VARCHAR},",
          "pic_url = #{picUrl,jdbcType=VARCHAR},",
          "level = #{level,jdbcType=VARCHAR},",
          "sort_order = #{sortOrder,jdbcType=TINYINT},",
          "add_time = #{addTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "deleted = #{deleted,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(MallCategory record);
}

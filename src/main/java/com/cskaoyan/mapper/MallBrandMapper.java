package com.cskaoyan.mapper;


import com.cskaoyan.bean.mall.brand.MallBrand;
import com.cskaoyan.bean.mall.brand.MallBrandExample;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface MallBrandMapper {
    long countByExample(MallBrandExample example);

    int deleteByExample(MallBrandExample example);

    @Delete({
        "delete from cskaoyan_mall_brand",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into cskaoyan_mall_brand (id, name, ",
        "`desc`, pic_url, sort_order, ",
        "floor_price, add_time, ",
        "update_time, deleted)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{desc,jdbcType=VARCHAR}, #{picUrl,jdbcType=VARCHAR}, #{sortOrder,jdbcType=TINYINT}, ",
        "#{floorPrice,jdbcType=DECIMAL}, #{addTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{deleted,jdbcType=BIT})"
    })
    int insert(MallBrand record);

    int insertSelective(MallBrand record);

    List<MallBrand> selectByExample(MallBrandExample example);

    @Select({
        "select",
        "id, name, `desc`, pic_url, sort_order, floor_price, add_time, update_time, deleted",
        "from cskaoyan_mall_brand",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.cskaoyan.mapper.MallBrandMapper.BaseResultMap")
    MallBrand selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallBrand record, @Param("example") MallBrandExample example);

    int updateByExample(@Param("record") MallBrand record, @Param("example") MallBrandExample example);

    int updateByPrimaryKeySelective(MallBrand record);

    @Update({
        "update cskaoyan_mall_brand",
        "set name = #{name,jdbcType=VARCHAR},",
          "`desc` = #{desc,jdbcType=VARCHAR},",
          "pic_url = #{picUrl,jdbcType=VARCHAR},",
          "sort_order = #{sortOrder,jdbcType=TINYINT},",
          "floor_price = #{floorPrice,jdbcType=DECIMAL},",
          "add_time = #{addTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "deleted = #{deleted,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(MallBrand record);

    int getlastInsert();
}

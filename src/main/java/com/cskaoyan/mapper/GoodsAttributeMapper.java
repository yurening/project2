package com.cskaoyan.mapper;

import com.cskaoyan.bean.user.GoodsAttribute;
import com.cskaoyan.bean.user.GoodsAttributeExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface GoodsAttributeMapper {
    long countByExample(GoodsAttributeExample example);

    int deleteByExample(GoodsAttributeExample example);

    @Delete({
        "delete from cskaoyan_mall_goods_attribute",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into cskaoyan_mall_goods_attribute (id, goods_id, ",
        "attribute, value, ",
        "add_time, update_time, ",
        "deleted)",
        "values (#{id,jdbcType=INTEGER}, #{goodsId,jdbcType=INTEGER}, ",
        "#{attribute,jdbcType=VARCHAR}, #{value,jdbcType=VARCHAR}, ",
        "#{addTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP}, ",
        "#{deleted,jdbcType=BIT})"
    })
    int insert(GoodsAttribute record);

    int insertSelective(GoodsAttribute record);

    List<GoodsAttribute> selectByExample(GoodsAttributeExample example);

    @Select({
        "select",
        "id, goods_id, attribute, value, add_time, update_time, deleted",
        "from cskaoyan_mall_goods_attribute",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.cskaoyan.mapper.GoodsAttributeMapper.BaseResultMap")
    GoodsAttribute selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodsAttribute record, @Param("example") GoodsAttributeExample example);

    int updateByExample(@Param("record") GoodsAttribute record, @Param("example") GoodsAttributeExample example);

    int updateByPrimaryKeySelective(GoodsAttribute record);

    @Update({
        "update cskaoyan_mall_goods_attribute",
        "set goods_id = #{goodsId,jdbcType=INTEGER},",
          "attribute = #{attribute,jdbcType=VARCHAR},",
          "value = #{value,jdbcType=VARCHAR},",
          "add_time = #{addTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "deleted = #{deleted,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(GoodsAttribute record);
}
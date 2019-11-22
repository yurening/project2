package com.cskaoyan.mapper;


import com.cskaoyan.bean.mall.cart.MallCart;
import com.cskaoyan.bean.mall.cart.MallCartExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MallCartMapper {
    long countByExample(MallCartExample example);

    int deleteByExample(MallCartExample example);

    @Delete({
        "delete from cskaoyan_mall_cart",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into cskaoyan_mall_cart (id, user_id, ",
        "goods_id, goods_sn, ",
        "goods_name, product_id, ",
        "price, number, ",
        "specifications, checked, ",
        "pic_url, add_time, ",
        "update_time, deleted)",
        "values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, ",
        "#{goodsId,jdbcType=INTEGER}, #{goodsSn,jdbcType=VARCHAR}, ",
        "#{goodsName,jdbcType=VARCHAR}, #{productId,jdbcType=INTEGER}, ",
        "#{price,jdbcType=DECIMAL}, #{number,jdbcType=SMALLINT}, ",
        "#{specifications,jdbcType=VARCHAR}, #{checked,jdbcType=BIT}, ",
        "#{picUrl,jdbcType=VARCHAR}, #{addTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{deleted,jdbcType=BIT})"
    })
    int insert(MallCart record);

    int insertSelective(MallCart record);

    List<MallCart> selectByExample(MallCartExample example);

    @Select({
        "select",
        "id, user_id, goods_id, goods_sn, goods_name, product_id, price, number, specifications, ",
        "checked, pic_url, add_time, update_time, deleted",
        "from cskaoyan_mall_cart",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.cskaoyan.mapper.MallCartMapper.BaseResultMap")
    MallCart selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallCart record, @Param("example") MallCartExample example);

    int updateByExample(@Param("record") MallCart record, @Param("example") MallCartExample example);

    int updateByPrimaryKeySelective(MallCart record);

    @Update({
        "update cskaoyan_mall_cart",
        "set user_id = #{userId,jdbcType=INTEGER},",
          "goods_id = #{goodsId,jdbcType=INTEGER},",
          "goods_sn = #{goodsSn,jdbcType=VARCHAR},",
          "goods_name = #{goodsName,jdbcType=VARCHAR},",
          "product_id = #{productId,jdbcType=INTEGER},",
          "price = #{price,jdbcType=DECIMAL},",
          "number = #{number,jdbcType=SMALLINT},",
          "specifications = #{specifications,jdbcType=VARCHAR},",
          "checked = #{checked,jdbcType=BIT},",
          "pic_url = #{picUrl,jdbcType=VARCHAR},",
          "add_time = #{addTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "deleted = #{deleted,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(MallCart record);
}

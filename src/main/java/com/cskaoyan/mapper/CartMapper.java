package com.cskaoyan.mapper;

import com.cskaoyan.bean.user.Cart;
import com.cskaoyan.bean.user.CartExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CartMapper {
    long countByExample(CartExample example);

    int deleteByExample(CartExample example);

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
    int insert(Cart record);

    int insertSelective(Cart record);

    List<Cart> selectByExample(CartExample example);

    @Select({
        "select",
        "id, user_id, goods_id, goods_sn, goods_name, product_id, price, number, specifications, ",
        "checked, pic_url, add_time, update_time, deleted",
        "from cskaoyan_mall_cart",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.cskaoyan.mapper.CartMapper.BaseResultMap")
    Cart selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Cart record, @Param("example") CartExample example);

    int updateByExample(@Param("record") Cart record, @Param("example") CartExample example);

    int updateByPrimaryKeySelective(Cart record);

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
    int updateByPrimaryKey(Cart record);

    Integer getGoodsCountByUserId(@Param("checked")Boolean checked, @Param("userId")Integer userId);
    Double getGoodsAmountByUserId(@Param("checked")Boolean checked, @Param("userId")Integer userId);
}
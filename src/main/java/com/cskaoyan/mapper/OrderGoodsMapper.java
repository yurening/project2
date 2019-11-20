package com.cskaoyan.mapper;

import com.cskaoyan.bean.user.OrderGoods;
import com.cskaoyan.bean.user.OrderGoodsExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OrderGoodsMapper {
    long countByExample(OrderGoodsExample example);

    int deleteByExample(OrderGoodsExample example);

    @Delete({
        "delete from cskaoyan_mall_order_goods",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into cskaoyan_mall_order_goods (id, order_id, ",
        "goods_id, goods_name, ",
        "goods_sn, product_id, ",
        "number, price, ",
        "specifications, pic_url, ",
        "comment, add_time, ",
        "update_time, deleted)",
        "values (#{id,jdbcType=INTEGER}, #{orderId,jdbcType=INTEGER}, ",
        "#{goodsId,jdbcType=INTEGER}, #{goodsName,jdbcType=VARCHAR}, ",
        "#{goodsSn,jdbcType=VARCHAR}, #{productId,jdbcType=INTEGER}, ",
        "#{number,jdbcType=SMALLINT}, #{price,jdbcType=DECIMAL}, ",
        "#{specifications,jdbcType=VARCHAR}, #{picUrl,jdbcType=VARCHAR}, ",
        "#{comment,jdbcType=INTEGER}, #{addTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{deleted,jdbcType=BIT})"
    })
    int insert(OrderGoods record);

    int insertSelective(OrderGoods record);

    List<OrderGoods> selectByExample(OrderGoodsExample example);

    @Select({
        "select",
        "id, order_id, goods_id, goods_name, goods_sn, product_id, number, price, specifications, ",
        "pic_url, comment, add_time, update_time, deleted",
        "from cskaoyan_mall_order_goods",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.cskaoyan.mapper.OrderGoodsMapper.BaseResultMap")
    OrderGoods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderGoods record, @Param("example") OrderGoodsExample example);

    int updateByExample(@Param("record") OrderGoods record, @Param("example") OrderGoodsExample example);

    int updateByPrimaryKeySelective(OrderGoods record);

    @Update({
        "update cskaoyan_mall_order_goods",
        "set order_id = #{orderId,jdbcType=INTEGER},",
          "goods_id = #{goodsId,jdbcType=INTEGER},",
          "goods_name = #{goodsName,jdbcType=VARCHAR},",
          "goods_sn = #{goodsSn,jdbcType=VARCHAR},",
          "product_id = #{productId,jdbcType=INTEGER},",
          "number = #{number,jdbcType=SMALLINT},",
          "price = #{price,jdbcType=DECIMAL},",
          "specifications = #{specifications,jdbcType=VARCHAR},",
          "pic_url = #{picUrl,jdbcType=VARCHAR},",
          "comment = #{comment,jdbcType=INTEGER},",
          "add_time = #{addTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "deleted = #{deleted,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(OrderGoods record);
}
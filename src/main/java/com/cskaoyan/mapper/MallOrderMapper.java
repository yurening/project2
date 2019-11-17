package com.cskaoyan.mapper;

import com.cskaoyan.bean.mall.order.MallOrder;
import com.cskaoyan.bean.mall.order.MallOrderExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MallOrderMapper {
    long countByExample(MallOrderExample example);

    int deleteByExample(MallOrderExample example);

    @Delete({
        "delete from cskaoyan_mall_order",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into cskaoyan_mall_order (id, user_id, ",
        "order_sn, order_status, ",
        "consignee, mobile, ",
        "address, message, ",
        "goods_price, freight_price, ",
        "coupon_price, integral_price, ",
        "groupon_price, order_price, ",
        "actual_price, pay_id, ",
        "pay_time, ship_sn, ",
        "ship_channel, ship_time, ",
        "confirm_time, comments, ",
        "end_time, add_time, ",
        "update_time, deleted)",
        "values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, ",
        "#{orderSn,jdbcType=VARCHAR}, #{orderStatus,jdbcType=SMALLINT}, ",
        "#{consignee,jdbcType=VARCHAR}, #{mobile,jdbcType=VARCHAR}, ",
        "#{address,jdbcType=VARCHAR}, #{message,jdbcType=VARCHAR}, ",
        "#{goodsPrice,jdbcType=DECIMAL}, #{freightPrice,jdbcType=DECIMAL}, ",
        "#{couponPrice,jdbcType=DECIMAL}, #{integralPrice,jdbcType=DECIMAL}, ",
        "#{grouponPrice,jdbcType=DECIMAL}, #{orderPrice,jdbcType=DECIMAL}, ",
        "#{actualPrice,jdbcType=DECIMAL}, #{payId,jdbcType=VARCHAR}, ",
        "#{payTime,jdbcType=TIMESTAMP}, #{shipSn,jdbcType=VARCHAR}, ",
        "#{shipChannel,jdbcType=VARCHAR}, #{shipTime,jdbcType=TIMESTAMP}, ",
        "#{confirmTime,jdbcType=TIMESTAMP}, #{comments,jdbcType=SMALLINT}, ",
        "#{endTime,jdbcType=TIMESTAMP}, #{addTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{deleted,jdbcType=BIT})"
    })
    int insert(MallOrder record);

    int insertSelective(MallOrder record);

    List<MallOrder> selectByExample(MallOrderExample example);

    @Select({
        "select",
        "id, user_id, order_sn, order_status, consignee, mobile, address, message, goods_price, ",
        "freight_price, coupon_price, integral_price, groupon_price, order_price, actual_price, ",
        "pay_id, pay_time, ship_sn, ship_channel, ship_time, confirm_time, comments, ",
        "end_time, add_time, update_time, deleted",
        "from cskaoyan_mall_order",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.cskaoyan.mapper.MallOrderMapper.BaseResultMap")
    MallOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallOrder record, @Param("example") MallOrderExample example);

    int updateByExample(@Param("record") MallOrder record, @Param("example") MallOrderExample example);

    int updateByPrimaryKeySelective(MallOrder record);

    @Update({
        "update cskaoyan_mall_order",
        "set user_id = #{userId,jdbcType=INTEGER},",
          "order_sn = #{orderSn,jdbcType=VARCHAR},",
          "order_status = #{orderStatus,jdbcType=SMALLINT},",
          "consignee = #{consignee,jdbcType=VARCHAR},",
          "mobile = #{mobile,jdbcType=VARCHAR},",
          "address = #{address,jdbcType=VARCHAR},",
          "message = #{message,jdbcType=VARCHAR},",
          "goods_price = #{goodsPrice,jdbcType=DECIMAL},",
          "freight_price = #{freightPrice,jdbcType=DECIMAL},",
          "coupon_price = #{couponPrice,jdbcType=DECIMAL},",
          "integral_price = #{integralPrice,jdbcType=DECIMAL},",
          "groupon_price = #{grouponPrice,jdbcType=DECIMAL},",
          "order_price = #{orderPrice,jdbcType=DECIMAL},",
          "actual_price = #{actualPrice,jdbcType=DECIMAL},",
          "pay_id = #{payId,jdbcType=VARCHAR},",
          "pay_time = #{payTime,jdbcType=TIMESTAMP},",
          "ship_sn = #{shipSn,jdbcType=VARCHAR},",
          "ship_channel = #{shipChannel,jdbcType=VARCHAR},",
          "ship_time = #{shipTime,jdbcType=TIMESTAMP},",
          "confirm_time = #{confirmTime,jdbcType=TIMESTAMP},",
          "comments = #{comments,jdbcType=SMALLINT},",
          "end_time = #{endTime,jdbcType=TIMESTAMP},",
          "add_time = #{addTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "deleted = #{deleted,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(MallOrder record);
}

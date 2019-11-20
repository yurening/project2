package com.cskaoyan.mapper;

import com.cskaoyan.bean.mall.coupon.MallCoupon;
import com.cskaoyan.bean.mall.coupon.MallCouponExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MallCouponMapper {
    long countByExample(MallCouponExample example);

    int deleteByExample(MallCouponExample example);

    @Delete({
        "delete from cskaoyan_mall_coupon",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into cskaoyan_mall_coupon (id, name, ",
        "desc, tag, total, ",
        "discount, min, limit, ",
        "type, status, ",
        "goods_type, goods_value, ",
        "code, time_type, ",
        "days, start_time, ",
        "end_time, add_time, ",
        "update_time, deleted)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{desc,jdbcType=VARCHAR}, #{tag,jdbcType=VARCHAR}, #{total,jdbcType=INTEGER}, ",
        "#{discount,jdbcType=DECIMAL}, #{min,jdbcType=DECIMAL}, #{limit,jdbcType=SMALLINT}, ",
        "#{type,jdbcType=SMALLINT}, #{status,jdbcType=SMALLINT}, ",
        "#{goodsType,jdbcType=SMALLINT}, #{goodsValue,jdbcType=VARCHAR}, ",
        "#{code,jdbcType=VARCHAR}, #{timeType,jdbcType=SMALLINT}, ",
        "#{days,jdbcType=SMALLINT}, #{startTime,jdbcType=TIMESTAMP}, ",
        "#{endTime,jdbcType=TIMESTAMP}, #{addTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{deleted,jdbcType=BIT})"
    })
    int insert(MallCoupon record);

    int insertSelective(MallCoupon record);

    List<MallCoupon> selectByExample(MallCouponExample example);

    @Select({
        "select",
        "id, name, desc, tag, total, discount, min, limit, type, status, goods_type, ",
        "goods_value, code, time_type, days, start_time, end_time, add_time, update_time, ",
        "deleted",
        "from cskaoyan_mall_coupon",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.cskaoyan.mapper.MallCouponMapper.BaseResultMap")
    MallCoupon selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallCoupon record, @Param("example") MallCouponExample example);

    int updateByExample(@Param("record") MallCoupon record, @Param("example") MallCouponExample example);

    int updateByPrimaryKeySelective(MallCoupon record);

    @Update({
        "update cskaoyan_mall_coupon",
        "set name = #{name,jdbcType=VARCHAR},",
          "desc = #{desc,jdbcType=VARCHAR},",
          "tag = #{tag,jdbcType=VARCHAR},",
          "total = #{total,jdbcType=INTEGER},",
          "discount = #{discount,jdbcType=DECIMAL},",
          "min = #{min,jdbcType=DECIMAL},",
          "limit = #{limit,jdbcType=SMALLINT},",
          "type = #{type,jdbcType=SMALLINT},",
          "status = #{status,jdbcType=SMALLINT},",
          "goods_type = #{goodsType,jdbcType=SMALLINT},",
          "goods_value = #{goodsValue,jdbcType=VARCHAR},",
          "code = #{code,jdbcType=VARCHAR},",
          "time_type = #{timeType,jdbcType=SMALLINT},",
          "days = #{days,jdbcType=SMALLINT},",
          "start_time = #{startTime,jdbcType=TIMESTAMP},",
          "end_time = #{endTime,jdbcType=TIMESTAMP},",
          "add_time = #{addTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "deleted = #{deleted,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(MallCoupon record);
}

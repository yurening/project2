package com.cskaoyan.mapper;

import com.cskaoyan.bean.mall.address.MallAddress;
import com.cskaoyan.bean.mall.address.MallAddressExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MallAddressMapper {
    long countByExample(MallAddressExample example);

    int deleteByExample(MallAddressExample example);

    @Delete({
        "delete from cskaoyan_mall_address",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into cskaoyan_mall_address (id, name, ",
        "user_id, province_id, ",
        "city_id, area_id, ",
        "address, mobile, ",
        "is_default, add_time, ",
        "update_time, deleted)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{userId,jdbcType=INTEGER}, #{provinceId,jdbcType=INTEGER}, ",
        "#{cityId,jdbcType=INTEGER}, #{areaId,jdbcType=INTEGER}, ",
        "#{address,jdbcType=VARCHAR}, #{mobile,jdbcType=VARCHAR}, ",
        "#{isDefault,jdbcType=BIT}, #{addTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{deleted,jdbcType=BIT})"
    })
    int insert(MallAddress record);

    int insertSelective(MallAddress record);

    List<MallAddress> selectByExample(MallAddressExample example);

    @Select({
        "select",
        "id, name, user_id, province_id, city_id, area_id, address, mobile, is_default, ",
        "add_time, update_time, deleted",
        "from cskaoyan_mall_address",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.cskaoyan.mapper.MallAddressMapper.BaseResultMap")
    MallAddress selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallAddress record, @Param("example") MallAddressExample example);

    int updateByExample(@Param("record") MallAddress record, @Param("example") MallAddressExample example);

    int updateByPrimaryKeySelective(MallAddress record);

    @Update({
        "update cskaoyan_mall_address",
        "set name = #{name,jdbcType=VARCHAR},",
          "user_id = #{userId,jdbcType=INTEGER},",
          "province_id = #{provinceId,jdbcType=INTEGER},",
          "city_id = #{cityId,jdbcType=INTEGER},",
          "area_id = #{areaId,jdbcType=INTEGER},",
          "address = #{address,jdbcType=VARCHAR},",
          "mobile = #{mobile,jdbcType=VARCHAR},",
          "is_default = #{isDefault,jdbcType=BIT},",
          "add_time = #{addTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "deleted = #{deleted,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(MallAddress record);
}

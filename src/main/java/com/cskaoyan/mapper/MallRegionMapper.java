package com.cskaoyan.mapper;

import com.cskaoyan.bean.mall.region.MallRegion;
import com.cskaoyan.bean.mall.region.MallRegionExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MallRegionMapper {
    long countByExample(MallRegionExample example);

    int deleteByExample(MallRegionExample example);

    @Delete({
        "delete from cskaoyan_mall_region",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into cskaoyan_mall_region (id, pid, ",
        "name, type, code)",
        "values (#{id,jdbcType=INTEGER}, #{pid,jdbcType=INTEGER}, ",
        "#{name,jdbcType=VARCHAR}, #{type,jdbcType=TINYINT}, #{code,jdbcType=INTEGER})"
    })
    int insert(MallRegion record);

    int insertSelective(MallRegion record);

    List<MallRegion> selectByExample(MallRegionExample example);

    @Select({
        "select",
        "id, pid, name, type, code",
        "from cskaoyan_mall_region",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.cskaoyan.mapper.MallRegionMapper.BaseResultMap")
    MallRegion selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallRegion record, @Param("example") MallRegionExample example);

    int updateByExample(@Param("record") MallRegion record, @Param("example") MallRegionExample example);

    int updateByPrimaryKeySelective(MallRegion record);

    @Update({
        "update cskaoyan_mall_region",
        "set pid = #{pid,jdbcType=INTEGER},",
          "name = #{name,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=TINYINT},",
          "code = #{code,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(MallRegion record);
}

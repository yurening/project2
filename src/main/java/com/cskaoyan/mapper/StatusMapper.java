package com.cskaoyan.mapper;

import com.cskaoyan.bean.user.Status;
import com.cskaoyan.bean.user.StatusExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StatusMapper {
    long countByExample(StatusExample example);

    int deleteByExample(StatusExample example);

    @Delete({
        "delete from cskaoyan_mall_order_status",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into cskaoyan_mall_order_status (id, status_id, ",
        "status_text)",
        "values (#{id,jdbcType=INTEGER}, #{statusId,jdbcType=INTEGER}, ",
        "#{statusText,jdbcType=VARCHAR})"
    })
    int insert(Status record);

    int insertSelective(Status record);

    List<Status> selectByExample(StatusExample example);

    @Select({
        "select",
        "id, status_id, status_text",
        "from cskaoyan_mall_order_status",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.cskaoyan.mapper.StatusMapper.BaseResultMap")
    Status selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Status record, @Param("example") StatusExample example);

    int updateByExample(@Param("record") Status record, @Param("example") StatusExample example);

    int updateByPrimaryKeySelective(Status record);

    @Update({
        "update cskaoyan_mall_order_status",
        "set status_id = #{statusId,jdbcType=INTEGER},",
          "status_text = #{statusText,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Status record);
}
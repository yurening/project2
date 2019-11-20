package com.cskaoyan.mapper;

import com.cskaoyan.bean.user.SearchHistory;
import com.cskaoyan.bean.user.SearchHistoryExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SearchHistoryMapper {
    long countByExample(SearchHistoryExample example);

    int deleteByExample(SearchHistoryExample example);

    @Delete({
        "delete from cskaoyan_mall_search_history",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into cskaoyan_mall_search_history (id, user_id, ",
        "keyword, from, add_time, ",
        "update_time, deleted)",
        "values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, ",
        "#{keyword,jdbcType=VARCHAR}, #{from,jdbcType=VARCHAR}, #{addTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{deleted,jdbcType=BIT})"
    })
    int insert(SearchHistory record);

    int insertSelective(SearchHistory record);

    List<SearchHistory> selectByExample(SearchHistoryExample example);

    @Select({
        "select",
        "id, user_id, keyword, from, add_time, update_time, deleted",
        "from cskaoyan_mall_search_history",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.cskaoyan.mapper.SearchHistoryMapper.BaseResultMap")
    SearchHistory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SearchHistory record, @Param("example") SearchHistoryExample example);

    int updateByExample(@Param("record") SearchHistory record, @Param("example") SearchHistoryExample example);

    int updateByPrimaryKeySelective(SearchHistory record);

    @Update({
        "update cskaoyan_mall_search_history",
        "set user_id = #{userId,jdbcType=INTEGER},",
          "keyword = #{keyword,jdbcType=VARCHAR},",
          "from = #{from,jdbcType=VARCHAR},",
          "add_time = #{addTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "deleted = #{deleted,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(SearchHistory record);
}
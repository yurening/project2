package com.cskaoyan.mapper;

import com.cskaoyan.bean.mall.issue.MallIssue;
import com.cskaoyan.bean.mall.issue.MallIssueExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MallIssueMapper {
    long countByExample(MallIssueExample example);

    int deleteByExample(MallIssueExample example);

    @Delete({
        "delete from cskaoyan_mall_issue",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into cskaoyan_mall_issue (id, question, ",
        "answer, add_time, ",
        "update_time, deleted)",
        "values (#{id,jdbcType=INTEGER}, #{question,jdbcType=VARCHAR}, ",
        "#{answer,jdbcType=VARCHAR}, #{addTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{deleted,jdbcType=BIT})"
    })
    int insert(MallIssue record);

    int insertSelective(MallIssue record);

    List<MallIssue> selectByExample(MallIssueExample example);

    @Select({
        "select",
        "id, question, answer, add_time, update_time, deleted",
        "from cskaoyan_mall_issue",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.cskaoyan.mapper.MallIssueMapper.BaseResultMap")
    MallIssue selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallIssue record, @Param("example") MallIssueExample example);

    int updateByExample(@Param("record") MallIssue record, @Param("example") MallIssueExample example);

    int updateByPrimaryKeySelective(MallIssue record);

    @Update({
        "update cskaoyan_mall_issue",
        "set question = #{question,jdbcType=VARCHAR},",
          "answer = #{answer,jdbcType=VARCHAR},",
          "add_time = #{addTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "deleted = #{deleted,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(MallIssue record);

    int lastInsertId();
}

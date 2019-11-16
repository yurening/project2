package com.cskaoyan.mapper;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.bean.system.AdminExample;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface AdminMapper {
    long countByExample(AdminExample example);

    int deleteByExample(AdminExample example);

    @Delete({
        "delete from cskaoyan_mall_admin",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into cskaoyan_mall_admin (id, username, ",
        "password, last_login_ip, ",
        "last_login_time, avatar, ",
        "add_time, update_time, ",
        "deleted, role_ids)",
        "values (#{id,jdbcType=INTEGER}, #{username,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{lastLoginIp,jdbcType=VARCHAR}, ",
        "#{lastLoginTime,jdbcType=TIMESTAMP}, #{avatar,jdbcType=VARCHAR}, ",
        "#{addTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP}, ",
        "#{deleted,jdbcType=BIT}, #{roleIds,jdbcType=VARCHAR})"
    })
    int insert(Admin record);

    int insertSelective(Admin record);

    List<Admin> selectByExample(AdminExample example);

    @Select({
        "select",
        "id, username, password, last_login_ip, last_login_time, avatar, add_time, update_time, ",
        "deleted, role_ids",
        "from cskaoyan_mall_admin",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.cskaoyan.mapper.AdminMapper.BaseResultMap")
    Admin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByExample(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByPrimaryKeySelective(Admin record);

    @Update({
        "update cskaoyan_mall_admin",
        "set username = #{username,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "last_login_ip = #{lastLoginIp,jdbcType=VARCHAR},",
          "last_login_time = #{lastLoginTime,jdbcType=TIMESTAMP},",
          "avatar = #{avatar,jdbcType=VARCHAR},",
          "add_time = #{addTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "deleted = #{deleted,jdbcType=BIT},",
          "role_ids = #{roleIds,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Admin record);

    @Select({
            "select",
            "id, username, password, last_login_ip, last_login_time, avatar, add_time, update_time, ",
            "deleted, role_ids",
            "from cskaoyan_mall_admin",
            "where username = #{username}"
    })
    Admin selectByUsername(String username);

    String getRoleNameById(String roleId);

    List<String> getPermsNameByRoleId(String roleId);

    @Select({"select count(id) from cskaoyan_mall_goods"})
    int getGoodsTotal();

    @Select({"select count(id) from cskaoyan_mall_user"})
    int getUserTotal();

    @Select({"select count(id) from cskaoyan_mall_goods_product"})
    int getProductTotal();

    @Select({"select count(id) from cskaoyan_mall_order"})
    int getOrderTotal();
}
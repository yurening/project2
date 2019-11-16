package com.cskaoyan.mapper;

import com.cskaoyan.bean.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface AuthMapper {
/*    long countByExample(AdminExample example);

    int deleteByExample(AdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    List<Admin> selectByExample(AdminExample example);*/

    @Select({
        "select",
        "id, username, password, last_login_ip, last_login_time, avatar, add_time, update_time, ",
        "deleted, role_ids",
        "from cskaoyan_mall_admin",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.cskaoyan.mapper.AuthMapper.BaseResultMap")

    Admin selectByPrimaryKey(Integer id);

/*    int updateByExampleSelective(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByExample(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);*/

/*    @Select({
            "select",
            "id, username, password, last_login_ip, last_login_time, avatar, add_time, update_time, ",
            "deleted, role_ids",
            "from cskaoyan_mall_admin",
            "where username = #{username}"
    })*/
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

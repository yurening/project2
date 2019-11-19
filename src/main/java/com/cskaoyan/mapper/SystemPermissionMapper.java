package com.cskaoyan.mapper;

import com.cskaoyan.bean.systemBean.SystemPermission;
import com.cskaoyan.bean.systemBean.SystemPermissionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemPermissionMapper {
    long countByExample(SystemPermissionExample example);

    int deleteByExample(SystemPermissionExample example);

    int deleteByPrimaryKey(Integer sId);

    int insert(SystemPermission record);

    int insertSelective(SystemPermission record);

    List<SystemPermission> selectByExample(SystemPermissionExample example);

    SystemPermission selectByPrimaryKey(Integer sId);

    int updateByExampleSelective(@Param("record") SystemPermission record, @Param("example") SystemPermissionExample example);

    int updateByExample(@Param("record") SystemPermission record, @Param("example") SystemPermissionExample example);

    int updateByPrimaryKeySelective(SystemPermission record);

    int updateByPrimaryKey(SystemPermission record);
}
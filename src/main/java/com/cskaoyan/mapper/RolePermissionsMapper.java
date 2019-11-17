package com.cskaoyan.mapper;

import com.cskaoyan.bean.systemBean.SystemPermissions;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RolePermissionsMapper {

    List<SystemPermissions> rolePermissionsMapperList();

}

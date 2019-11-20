package com.cskaoyan.bean.systemBean;

import lombok.Data;

import java.util.List;

@Data
public class Permissions {

    List<String> permissions;

    Integer roleId;
}

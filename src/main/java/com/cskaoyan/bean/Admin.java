package com.cskaoyan.bean;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Admin {
    private Integer id;

    private String username;

    private String password;

    private String last_login_ip;

    private Date last_login_time;

    private String avatar;

    private Date add_time;

    private Date update_time;

    private Boolean deleted;

    private String[] role_ids;
}
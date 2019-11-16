package com.cskaoyan.service;

import com.cskaoyan.bean.config.SystemBean;

public interface ConfigService {
    SystemBean getSystemMall();

    SystemBean getSystemExpress();

    SystemBean getSystemOrder();

    SystemBean getSystemWx();

    int updateSystemMall(SystemBean systemBean);

    int updateSystemExpress(SystemBean systemBean);

    int updateSystemOrder(SystemBean systemBean);

    int updateSystemWx(SystemBean systemBean);
}

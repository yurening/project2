package com.cskaoyan;

import com.cskaoyan.bean.systemBean.SystemPermission;
import com.cskaoyan.bean.systemBean.SystemPermissionExample;
import com.cskaoyan.mapper.SystemPermissionMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MyTest {

    @Autowired
    SystemPermissionMapper systemPermissionMapper;


    @Test
    public void mytest(){
        SystemPermissionExample systemPermissionExample = new SystemPermissionExample();
        List<SystemPermission> systemPermissions = new ArrayList<>();
        SystemPermission systemPermission1 = new SystemPermission(125,"3",37,"admin:comment:delete","删除","POST /admin/comment/delete");
        SystemPermission systemPermission2 = new SystemPermission(126,"3",37,"admin:comment:list","查询","GET /admin/comment/list");
        //SystemPermission systemPermission3 = new SystemPermission(122,"3",36,"admin:goods:delete","删除","POST /admin/goods/delete");
        //SystemPermission systemPermission4 = new SystemPermission(123,"3",36,"admin:goods:create","上架","POST /admin/goods/create");
        //SystemPermission systemPermission5 = new SystemPermission(124,"3",36,"admin:goods:list","查询","GET /admin/goods/list");
        //SystemPermission systemPermission6 = new SystemPermission(88,"3",29,"admin:role:permission:get","权限详情","GET /admin/role/permissions");
        //SystemPermission systemPermission7 = new SystemPermission(89,"3",29,"admin:role:create","角色添加","POST /admin/role/create");
        //SystemPermission systemPermission8 = new SystemPermission(90,"3",29,"admin:role:list","角色查询","GET /admin/role/list");
        systemPermissions.add(systemPermission1);
        systemPermissions.add(systemPermission2);
        //systemPermissions.add(systemPermission3);
        //systemPermissions.add(systemPermission4);
        //systemPermissions.add(systemPermission5);
        //systemPermissions.add(systemPermission6);
        //systemPermissions.add(systemPermission7);
        //systemPermissions.add(systemPermission8);
        //Integer sId, String level, Integer pId, String id, String label, String api
        for (SystemPermission systemPermission : systemPermissions) {
            systemPermissionMapper.insert(systemPermission);
        }
    }

    @Test
    public void mytest2(){
        List<String> strings = new ArrayList<>();
        for (String string : strings) {
            System.out.println(string);
        }
    }
}

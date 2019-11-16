/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/16
 * Time: 11:37
 */
package com.cskaoyan.service;

import com.cskaoyan.bean.config.SystemBean;
import com.cskaoyan.mapper.ConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ConfigServiceImpl implements ConfigService{

    @Autowired
    ConfigMapper configMapper;

    @Override
    public SystemBean getSystemMall() {
        SystemBean systemBean = new SystemBean();
        systemBean.setLitemall_mall_phone(configMapper.getSystemValueByID(12)); //phone对应的id为12
        systemBean.setLitemall_mall_address(configMapper.getSystemValueByID(14)); //address对应的id为14
        systemBean.setLitemall_mall_name(configMapper.getSystemValueByID(6)); //name对应的id为6
        systemBean.setLitemall_mall_qq(configMapper.getSystemValueByID(8)); //qq对应的id为8
        return systemBean;
    }

    @Override
    public SystemBean getSystemExpress() {
        SystemBean systemBean = new SystemBean();
        systemBean.setLitemall_express_freight_min(configMapper.getSystemValueByID(5)); //min对应的id为5
        systemBean.setLitemall_express_freight_value(configMapper.getSystemValueByID(7)); //value对应的id为7
        return systemBean;
    }

    @Override
    public SystemBean getSystemOrder() {
        SystemBean systemBean = new SystemBean();
        systemBean.setLitemall_order_comment(configMapper.getSystemValueByID(10)); //comment对应的id为10
        systemBean.setLitemall_order_unconfirm(configMapper.getSystemValueByID(3)); //unconfirm对应的id为3
        systemBean.setLitemall_order_unpaid(configMapper.getSystemValueByID(1)); //unpaid对应的id为1
        return systemBean;
    }

    @Override
    public SystemBean getSystemWx() {
        SystemBean systemBean = new SystemBean();
        systemBean.setLitemall_wx_index_new(configMapper.getSystemValueByID(2)); //new对应的id为2
        systemBean.setLitemall_wx_catlog_goods(configMapper.getSystemValueByID(11)); //goods对应的id为11
        systemBean.setLitemall_wx_catlog_list(configMapper.getSystemValueByID(13)); //unconfirm对应的id为13
        systemBean.setLitemall_wx_share(configMapper.getSystemValueByID(4)); //unpaid对应的id为4
        systemBean.setLitemall_wx_index_brand(configMapper.getSystemValueByID(15)); //unpaid对应的id为15
        systemBean.setLitemall_wx_index_hot(configMapper.getSystemValueByID(9)); //unpaid对应的id为9
        systemBean.setLitemall_wx_index_topic(configMapper.getSystemValueByID(16)); //unpaid对应的id为16
        return systemBean;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int updateSystemMall(SystemBean systemBean) {
        String phone = systemBean.getLitemall_mall_phone();//phone对应的id为12
        String address = systemBean.getLitemall_mall_address();//address对应的id为14
        String name = systemBean.getLitemall_mall_name();//name对应的id为6
        String qq = systemBean.getLitemall_mall_qq();//qq对应的id为8
        if ("".equals(name)){
            return 2;
        }else if ("".equals(address)){
            return 3;
        }else if ("".equals(phone)){
            return 4;
        }else if ("".equals(qq)){
            return 5;
        }


        HashMap<String, String> map = new HashMap<>();
        map.put("%"+"phone",phone);
        map.put("%"+"address",address);
        map.put("%"+"name",name);
        map.put("%"+"qq",qq);
        try {
            updateSystemTool(map);
        }catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int updateSystemExpress(SystemBean systemBean) {
        String min = systemBean.getLitemall_express_freight_min();
        String value = systemBean.getLitemall_express_freight_value();
        String regex = "^(([0])||([-+]?([1-9]([0-9])*)+(\\.[0-9]{1,2})?))$";
//        String regex = "^[-+]?(([0-9]+)(\\.)?|(\\.)?)$";
//        String regex = "^-?[0-9]+$";
        Pattern pat = Pattern.compile(regex);
        Matcher mat1 = pat.matcher(min);
        Matcher mat2 = pat.matcher(value);
        if (!( mat1.find() && mat2.find())) { return 2; }

        double mini = Double.parseDouble(min);
        double valuei = Double.parseDouble(min);
        if (mini < 0 || valuei < 0){
            return 3;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("%"+"min",min);
        map.put("%"+"value",value);
        try {
            updateSystemTool(map);
        }catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int updateSystemOrder(SystemBean systemBean) {
        String unpaid = systemBean.getLitemall_order_unpaid();
        String unconfirm = systemBean.getLitemall_order_unconfirm();
        String comment = systemBean.getLitemall_order_comment();
        if ("".equals(unpaid)){ return 2; }
        if ("".equals(unconfirm)){ return 3; }
        if ("".equals(comment)){ return 4; }

        String regex = "^(([0])||([-+]?([1-9]([0-9])*)+(\\.[0-9]{1,2})?))$";
        Pattern pat = Pattern.compile(regex);
        Matcher mat1 = pat.matcher(unpaid);
        Matcher mat2 = pat.matcher(unconfirm);
        Matcher mat3 = pat.matcher(comment);
        if (! mat1.find()) { return 5; }
        if (!(mat2.find() && mat3.find())) { return 6; }

        double unpaidd = Double.parseDouble(unpaid);
        double unconfirmd = Double.parseDouble(unconfirm);
        double commentd = Double.parseDouble(comment);
        if (unpaidd < 0) {return 7;}
        if (unconfirmd < 0 || commentd < 0){ return 8; }

        HashMap<String, String> map = new HashMap<>();
        map.put("%"+"unpaid",unpaid);
        map.put("%"+"unconfirm",unconfirm);
        map.put("%"+"comment",comment);
        try {
            updateSystemTool(map);
        }catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int updateSystemWx(SystemBean systemBean) {
        String aNew = systemBean.getLitemall_wx_index_new();
        String hot = systemBean.getLitemall_wx_index_hot();
        String brand = systemBean.getLitemall_wx_index_brand();
        String topic = systemBean.getLitemall_wx_index_topic();
        String list = systemBean.getLitemall_wx_catlog_list();
        String goods = systemBean.getLitemall_wx_catlog_goods();
        String share = systemBean.getLitemall_wx_share();
        if ("".equals(aNew)){ return 2; }   //判断是否为空串,即没输入
        if ("".equals(hot)){ return 3; }
        if ("".equals(brand)){ return 4; }
        if ("".equals(topic)){ return 5; }
        if ("".equals(list)){ return 6; }
        if ("".equals(goods)){ return 7; }
        if ("".equals(share)){ return 8; }
//        String regex = "^[-+]?([1-9]([0-9])*)+$";
        String regex = "^(([0])||([1-9]([0-9])*)+)$";
        Pattern pat = Pattern.compile(regex);
        Matcher mat1 = pat.matcher(aNew);
        Matcher mat2 = pat.matcher(hot);
        Matcher mat3 = pat.matcher(brand);
        Matcher mat4 = pat.matcher(topic);
        Matcher mat5 = pat.matcher(list);
        Matcher mat6 = pat.matcher(goods);
        if (!(mat1.find() && mat2.find() && mat3.find() && mat4.find() && mat5.find() && mat6.find())) { return 9; }

        double aNewd = Double.parseDouble(aNew);
        double hotd = Double.parseDouble(hot);
        double brandd = Double.parseDouble(brand);
        double topicd = Double.parseDouble(topic);
        double listd = Double.parseDouble(list);
        double goodsd = Double.parseDouble(goods);
        if (aNewd < 0 || hotd < 0 || brandd < 0 || topicd < 0 || listd < 0 || goodsd < 0){ return 10; }

        HashMap<String, String> map = new HashMap<>();
        map.put("%"+"new",aNew);
        map.put("%"+"hot",hot);
        map.put("%"+"brand",brand);
        map.put("%"+"topic",topic);
        map.put("%"+"list",list);
        map.put("%"+"goods",goods);
        map.put("%"+"share",share);
        try {
            updateSystemTool(map);
        }catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    private void updateSystemTool(HashMap<String, String> map) throws Exception{
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                configMapper.updateSystemValueByKey(entry.getKey(), entry.getValue());
            }
    }
}

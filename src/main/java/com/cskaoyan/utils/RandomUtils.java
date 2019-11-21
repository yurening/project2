/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/21
 * Time: 20:37
 */
package com.cskaoyan.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomUtils {

    public static String getRandomString(Integer num) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String getRandomNum(Integer num) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int number = random.nextInt(base.length()-1);
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String getRandomAvater() {
        List<String> list = new ArrayList<>();
        list.add("http://imgsrc.baidu.com/forum/w%3D580/sign=12f67e1a584e9258a63486e6ac83d1d1/48260a55b319ebc4ac90e0e78c26cffc1f1716fe.jpg");
        list.add("http://imgsrc.baidu.com/forum/w%3D580/sign=fce283ea482309f7e76fad1a420f0c39/c2c9b48f8c5494ee43b7996723f5e0fe98257eca.jpg");
        list.add("https://img3.doubanio.com/view/group_topic/l/public/p135989295.jpg");
        list.add("https://img1.doubanio.com/view/group_topic/l/public/p135989298.jpg");
        list.add("https://img3.doubanio.com/view/group_topic/l/public/p135989292.jpg");
        list.add("https://img9.doubanio.com/view/group_topic/l/public/p135989276.jpg");
        list.add("https://img9.doubanio.com/view/group_topic/l/public/p135989296.jpg");
        list.add("https://img1.doubanio.com/view/group_topic/l/public/p135989287.jpg");
        list.add("https://img3.doubanio.com/view/group_topic/l/public/p135989282.jpg");
        String base = "012345678";
        Random random = new Random();
        int number = random.nextInt(base.length()-1);
        return list.get(number);
    }

    public static String getRandomNickName() {
        List<String> list = new ArrayList<>();
        String base = "012345678";
        Random random = new Random();
        int number = random.nextInt(base.length());
        return list.get(number);
    }
}

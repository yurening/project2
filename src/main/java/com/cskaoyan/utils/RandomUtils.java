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
        StringBuffer nickName = new StringBuffer();
        String[] ss = new String[]{"一样的", "喜欢的", "美丽的", "一定的", "原来的", "美好的", "开心的", "可能的", "可爱的", "明白的", "所有的", "后来的", "重要的", "经常的", "自然的", "真正的", "害怕的", "空中的", "红色的", "痛苦的", "干净的", "辛苦的", "精彩的", "欢乐的", "进步的", "影响的", "黄色的", "亲爱的", "根本的", "完美的", "金黄的", "聪明的", "清新的", "迷人的", "光明的", "共同的", "直接的", "真实的", "听说的", "可怕的"};
        String[] name = new String[]{"赵","钱","孙","李","周","吴","郑","王","冯","陈","楮","卫","蒋","沈","韩","杨","朱","秦","尤","许","何","吕","施","张","孔","曹","严","华","金","魏","陶","姜"};
        String[] name2 = new String[]{"万俟","司马","上官","欧阳","夏侯","诸葛","闻人","东方","赫连","皇甫","尉迟","公羊","澹台","公冶","宗政","濮阳","淳于","单于","太叔","申屠","公孙","仲孙","轩辕","令狐","锺离","宇文","长孙","慕容","鲜于","闾丘","司徒","司空","梁丘","左丘","东门","西门","南宫"};
        Random random = new Random();
        int number1 = random.nextInt(ss.length-1);
        int number2 = random.nextInt(name.length-1);
        int number3 = random.nextInt(name2.length-1);
        nickName.append(ss[number1]);
        nickName.append(name[number2]);
        nickName.append(name2[number3]);
        return nickName.toString();
    }
}

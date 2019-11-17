/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/17
 * Time: 15:42
 */
package com.cskaoyan.bean.stat;

import lombok.Data;

import java.util.Date;

@Data
public class GoodsCount {
    private double amount;
    private Integer orders;
    private String day;
    private Integer products;
}

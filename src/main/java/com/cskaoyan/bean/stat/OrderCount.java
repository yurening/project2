/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/17
 * Time: 15:41
 */
package com.cskaoyan.bean.stat;


import lombok.Data;

@Data
public class OrderCount {
    private double amount;
    private Integer orders;
    private Integer customers;
    private String day;
    private double pcr;
}

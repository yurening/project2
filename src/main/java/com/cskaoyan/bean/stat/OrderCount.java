/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/17
 * Time: 15:41
 */
package com.cskaoyan.bean.stat;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;

@Data
public class OrderCount {
    private double amount;
    private Integer orders;
    private Integer customers;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private String day;
    private double pcr;

}

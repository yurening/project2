package com.cskaoyan.bean.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class FootprintForList {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date addTime;

    String brief;

    Integer goodsId;

    Integer id;

    String name;

    String picUrl;

    String retailPrice;
}

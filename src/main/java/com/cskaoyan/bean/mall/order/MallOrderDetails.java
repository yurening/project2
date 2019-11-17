package com.cskaoyan.bean.mall.order;

import com.cskaoyan.bean.user.User;
import lombok.Data;

@Data
public class MallOrderDetails {
    MallOrder order;
    MallOrderGoods mallOrderGoods;
    User user;
}

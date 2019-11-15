/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/15
 * Time: 16:18
 */
package com.cskaoyan.bean;

import lombok.Data;

@Data
public class BaseReqVo<T> {
        T data;
        String errmsg;
        int errno;
}

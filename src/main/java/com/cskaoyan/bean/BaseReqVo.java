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


        public static BaseReqVo ok(Object data){
                BaseReqVo BaseReqVo = new BaseReqVo();
                BaseReqVo.setErrmsg("成功");
                BaseReqVo.setData(data);
                BaseReqVo.setErrno(0);
                return BaseReqVo;
        }

        public static BaseReqVo fail(int errno,String errmsg){
                BaseReqVo BaseReqVo = new BaseReqVo();
                BaseReqVo.setErrno(errno);
                BaseReqVo.setErrmsg(errmsg);
                return BaseReqVo;
        }
}

package com.cskaoyan.controller;

import com.aliyun.oss.OSSClient;
import com.cskaoyan.bean.goods.PicStatic;
import com.cskaoyan.bean.goods.ResponseType;
import com.cskaoyan.bean.goods.StaticPhoto;
import com.cskaoyan.service.PicStaticService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@ResponseBody
@RequestMapping("admin")
public class PicStaticController {
    @Autowired
    PicStaticService picStaticService;
    @RequestMapping("storage/create")
    @RequiresPermissions(value = {"admin:storage:create","admin:groupon:create","admin:ad:create",
    "admin:topic:create","admin:coupon:create","admin:admin:create","admin:role:create",
    "admin:brand:create","admin:keyword:create","admin:category:create","admin:issue:create",
    "admin:goods:create","admin:storage:update","admin:groupon:update","admin:ad:update",
    "admin:topic:update","admin:coupon:update","admin:admin:update","admin:role:update",
    "admin:brand:update","admin:keyword:update","admin:category:update","admin:issue:update",
    "admin:goods:update"},logical = Logical.OR)
    public ResponseType picStatic(MultipartFile file) throws IOException {
        //阿里云
        String accessKeyId="LTAI4FkEFjiCLX5NWuaCreE7";
        String accessSecret="thisSIrvhvbgp0AE0joz5ZwEe4m0Q2";
        String bucket = "mall2";
        String endPoint = "oss-cn-beijing.aliyuncs.com";
        //完成文件key部分的命名
        String str = UUID.randomUUID().toString();
        String[] split = str.split("-");
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : split) {
            stringBuffer.append(s);
        }
        //System.out.println(stringBuffer);
        String originalFilename = file.getOriginalFilename();
        String[] split1 = originalFilename.split("\\.");
        String suffix = split1[split1.length-1];
        String fileName = stringBuffer+"."+suffix;

        OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessSecret);
        //bucket、上传的文件名、file对象或者是file流（multipart直接getStream）
        ossClient.putObject(bucket,fileName,file.getInputStream());

        StaticPhoto staticPhoto = new StaticPhoto();
        staticPhoto.setKey(fileName);
        staticPhoto.setName(originalFilename);
        staticPhoto.setSize((int) file.getSize());
        staticPhoto.setType(file.getContentType());
        staticPhoto.setUrl("http://192.168.4.17:8080/wx/storage/fetch/"+fileName);

        StaticPhoto staticPhoto1 = picStaticService.addFile(staticPhoto);

        ResponseType responseType = new ResponseType();
        responseType.setErrmsg("成功");
        responseType.setErrno(0);
        responseType.setData(staticPhoto1);
        return responseType;
    }
}

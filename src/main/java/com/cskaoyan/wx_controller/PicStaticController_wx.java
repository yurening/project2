package com.cskaoyan.wx_controller;

import com.aliyun.oss.OSSClient;
import com.cskaoyan.bean.goods.ResponseType;
import com.cskaoyan.bean.goods.StaticPhoto;
import com.cskaoyan.service.PicStaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Controller
@ResponseBody
@RequestMapping("wx")
public class PicStaticController_wx {
    @Autowired
    PicStaticService picStaticService;
    @RequestMapping("storage/upload")
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
        staticPhoto.setUrl("http://localhost:8080/wx/storage/fetch/"+fileName);

        StaticPhoto staticPhoto1 = picStaticService.addFile(staticPhoto);

        ResponseType responseType = new ResponseType();
        responseType.setErrmsg("成功");
        responseType.setErrno(0);
        responseType.setData(staticPhoto1);
        return responseType;
    }
}

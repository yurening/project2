package com.cskaoyan.controller;

import com.cskaoyan.bean.goods.PicStatic;
import com.cskaoyan.bean.goods.ResponseType;
import com.cskaoyan.bean.goods.StaticPhoto;
import com.cskaoyan.service.PicStaticService;
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
    public ResponseType picStatic(MultipartFile file) throws IOException {
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

        file.transferTo(new File("d:/pic/"+fileName));

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

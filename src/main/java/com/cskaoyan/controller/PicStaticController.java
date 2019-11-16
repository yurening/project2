package com.cskaoyan.controller;

import com.cskaoyan.bean.goods.PicStatic;
import com.cskaoyan.bean.goods.ResponseType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("admin")
public class PicStaticController {
    @RequestMapping("storage/create")
    public ResponseType picStatic(MultipartFile file) throws IOException {
        //String originalFilename = file.getOriginalFilename()+".jpg";
        String originalFilename = file.getOriginalFilename();
        file.transferTo(new File("D:/"+originalFilename));
        /*PicStatic picStatic = new PicStatic();
        picStatic.setId(12);
        picStatic.setKey(originalFilename);
        picStatic.setName("1.jpg");
        picStatic.setSize((int) file.getSize());
        picStatic.setType("image/jpeg");
        picStatic.setUrl("http://localhost:8080/wx/storage/fetch/"+originalFilename);*/
        ResponseType responseType = new ResponseType();
        /*responseType.setErrmsg("成功");
        responseType.setErrno(0);
        responseType.setData(picStatic);*/
        return responseType;
    }
}

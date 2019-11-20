package com.cskaoyan.wx_controller;

import com.aliyun.oss.OSSClient;
import com.cskaoyan.bean.BaseReqVo;
import com.cskaoyan.bean.goods.ResponseType;
import com.cskaoyan.bean.goods.StaticPhoto;
import com.cskaoyan.bean.user.Feedback;
import com.cskaoyan.needdelete.BaseRespVo;
import com.cskaoyan.needdelete.UserTokenManager;
import com.cskaoyan.service.OtherService;
import com.cskaoyan.service.PicStaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("wx")
public class OtherController_wx {

    @Autowired
    PicStaticService picStaticService;

    @Autowired
    OtherService otherService;

    @RequestMapping("storage/create")
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

    @RequestMapping("feedback/submit")
    public Object regionList(@RequestBody Feedback feedback, HttpServletRequest request){
        //前端写了一个token放在请求头中
        //*************************
        //获得请求头
        String tokenKey = request.getHeader("5cn9hnzh0lgki9n69bxjegsafqzocpq2");
        Integer userId = UserTokenManager.getUserId(tokenKey);

        //通过请求头获得userId，进而可以获得一切关于user的信息
        //**************************
        if (userId == null) {
            return BaseRespVo.fail();
        }
        otherService.feedbackSubmit(feedback,userId);
        return BaseRespVo.ok(null);
    }

    @RequestMapping("footprint/list")
    public Object footprintList(Integer page,Integer size,HttpServletRequest request){
        //前端写了一个token放在请求头中
        //*************************
        //获得请求头
        String tokenKey = request.getHeader("5cn9hnzh0lgki9n69bxjegsafqzocpq2");
        Integer userId = UserTokenManager.getUserId(tokenKey);

        //通过请求头获得userId，进而可以获得一切关于user的信息
        //**************************
        if (userId == null) {
            return BaseRespVo.fail();
        }
        HashMap<String,Object> footprintLists = otherService.footprintList(page,size,userId);
        return BaseRespVo.ok(footprintLists);
    }
}

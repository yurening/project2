/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/21
 * Time: 17:36
 */
package com.cskaoyan.utils;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.stereotype.Component;

@Component
public class SmsUtils {

    public static String regCaptchaTool(String PhoneNumbers) {
        String accessKeyId = "LTAI4Fr5gfYhcVjLMqeRGbuT";
        String accessSecret = "IrkcHu6dZyrjPZRushgO76P5392HJ1";
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        String code = RandomUtils.getRandomNum(6);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", PhoneNumbers);
        request.putQueryParameter("SignName", "王道训练营");
        request.putQueryParameter("TemplateCode", "SMS_173765187");
        request.putQueryParameter("TemplateParam", "{\"code\": "+code+"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return code;

    }
}
